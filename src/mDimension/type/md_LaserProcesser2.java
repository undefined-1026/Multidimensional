package mDimension.type;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.struct.EnumSet;
import arc.util.Time;
import mDimension.tool.continuousRGB;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.world.blocks.production.GenericCrafter;
import mDimension.type.md_LaserCrafter.md_LaserCrafterBuild;

import java.util.Arrays;

import mDimension.type.md_LaserProcesser.md_LaserProcesserBuild;

import static mindustry.Vars.world;

public class md_LaserProcesser2 extends GenericCrafter{

    public boolean DrawRotation = false;
    public boolean du_li_Arrow = false;
    public boolean DrawHigherTop = true;
    public float wavelengthAdd = 0f;
    public float powerAdd = 0f;
    /// 这个值决定了每点LaserPower会额外产生多少的物品消耗
    public float AddConsum = 0.1f;



    public md_LaserProcesser2(String name){
        super(name);
        rotate = false;
        rotateDraw = false;
        canOverdrive = false;
        drawArrow = false;
        category = Category.crafting;
        flags = EnumSet.of();
        consumePower(1f);
    }


    public class md_LaserProcesser2Build extends GenericCrafterBuild {
        public float[][] CachePoint= new float[][]{
                {-1,-1,-1,-1},
                {-1,-1,-1,-1},
                {-1,-1,-1,-1},
                {-1,-1,-1,-1}

        };
        public boolean DrawEndCircle = false;
        public boolean DrawEndGradient = false;



        public md_LaserData[] CacheLasers = new md_LaserData[]{null,null,null,null};
        public md_LaserData[] CacheprocessLasers = new md_LaserData[]{null,null,null,null};
        public float[] CacheLaserPower = new float[]{0f,0f,0f,0f};
        public float[] CacheWanelength = new float[]{0f,0f,0f,0f};


        public void process(md_LaserData l) {
            ;

            float CpowerAdd = Math.max(0f, l.laserPower + powerAdd) - l.laserPower;
            float CwavelengthAdd = Math.max(50f, l.waveLength + wavelengthAdd) - l.waveLength;
            CpowerAdd = powerAdd * Math.min((CwavelengthAdd / wavelengthAdd), (CpowerAdd / powerAdd));
            CwavelengthAdd = wavelengthAdd * Math.min((CwavelengthAdd / wavelengthAdd), (CpowerAdd / powerAdd));


            l.laserPower = Math.min(l.laserPower + CpowerAdd, 1024);
            l.laserPower = Math.max(l.laserPower, 0f);
            l.waveLength = Math.min(l.waveLength + CwavelengthAdd, 32768);
            l.waveLength = Math.max(l.waveLength, 50f);
        }
        public float[] getprocess(int r) {
            float laserPower = CacheWanelength[r];
            float waveLength = CacheLaserPower[r];

            float CpowerAdd = Math.max(0f, laserPower + powerAdd) - laserPower;
            float CwavelengthAdd = Math.max(50f, waveLength + wavelengthAdd) - waveLength;
            CpowerAdd = powerAdd * Math.min((CwavelengthAdd / wavelengthAdd), (CpowerAdd / powerAdd));
            CwavelengthAdd = wavelengthAdd * Math.min((CwavelengthAdd / wavelengthAdd), (CpowerAdd / powerAdd));

            return new float[]{CpowerAdd,CwavelengthAdd};
        }
        public float getpowerSum(){
            float sum = 0;
            for(float i:CacheLaserPower){
                sum+=i;
            }
            return sum;
        }
        @Override
        public void updateTile() {
            super.updateTile();
            Arrays.fill(CachePoint, new float[]{-1, -1, -1, -1});
            Arrays.fill(CacheLaserPower, 0);
            Arrays.fill(CacheWanelength, 0);
            Arrays.fill(CacheprocessLasers, null);
            if(warmup>0.01f) {
                for (int k = 0; k < 4; k++) {

                    md_LaserData l = CacheLasers[k];

                    if (l != null) {
                        int r = k;
                        CacheLaserPower[r] = l.laserPower;
                        CacheWanelength[r] = l.waveLength;
                        DrawEndGradient = true;
                        DrawEndCircle = false;
                        int dx = getdx_dy(r)[0];
                        int dy = getdx_dy(r)[1];
                        int ox = 0;
                        int oy = 0;
                        float dl = 0.5F;
                        md_LaserData l1 = new md_LaserData(l);
                        process(l1);
                        for (int i = 0; i < l.maxLength; i++) {
                            ox += dx;
                            oy += dy;
                            Building other = world.build(tile.x + ox, tile.y + oy);
                            if (other != null) {
                                if (other instanceof md_LaserProcesser2Build other2) {
                                    other2.CacheLasers[r] = l1;
                                    dl = 0f;
                                    DrawEndGradient = false;
                                    break;
                                }
                                if (other instanceof md_LaserProcesserBuild other2) {
                                    other2.CacheLasers.add(l1);
                                    dl = 0f;
                                    DrawEndGradient = false;
                                    break;
                                }
                                if (other instanceof md_LaserCrafterBuild other2) {
                                    other2.CacheLasers.add(l1);
                                    DrawEndCircle = true;
                                    break;
                                }
                                if (other.block.solid && l.blocked) {

                                    if (!(other instanceof md_LaserProducer.md_LaserProducerBuild)) {
                                        DrawEndCircle = true;
                                    } else {
                                        dl = 0f;
                                        DrawEndGradient = false;
                                    }
                                    break;
                                }
                            }
                        }
                        CacheprocessLasers[r] = l1;
                        hit(tile.x, tile.y, tile.x - dx * dl + ox, tile.y - dy * dl + oy, r);
                    }
                }
            }
            Arrays.fill(CacheLasers,null);
        }


        @Override
        public void draw() {

            super.draw();
            if (DrawHigherTop) {
                Draw.z(Layer.blockProp + 0.1f);
                Draw.rect(name + "-top", this.x(), this.y());
            }
            if (DrawRotation) {
                if (du_li_Arrow) {
                    Draw.z(Layer.blockProp + 0.1001f);
                    Draw.rect(name + String.format("-arrow%d", rotation + 1), this.x(), this.y());
                } else {
                    Draw.z(Layer.blockProp + 0.1001f);
                    Draw.rect(name + "-arrow", this.x(), this.y(), rotation * 90);
                }
            }
            for (int i =0;i<4;i++) {
                int r = i;

                md_LaserData l = CacheprocessLasers[r];
                if(l!=null) {
                    float laserWarmup = efficiency * Math.min(1f, l.laserPower / 25f)/efficiencyScale();
                    if (laserWarmup > 0.001f) {

                        float lsx = CachePoint[r][0];
                        float lsy = CachePoint[r][1];
                        float lex = CachePoint[r][2];
                        float ley = CachePoint[r][3];


                        float stroke = (4f + Mathf.absin(Time.time + (id % 9) * 9, 3f, 0.50f)) * (float) Math.sqrt(laserWarmup);
                        int dx = getdx_dy(r)[0];
                        int dy = getdx_dy(r)[1];
                        Lines.setCirclePrecision(0.9f);

                        Color col1 = continuousRGB.contWhiteRGB((l.waveLength / 1000f), 0.65f, 0.95f);
                        Color col2 = Color.white;
                        Color endCol = Color.clear;

                        Draw.z(Layer.blockProp);
                        Draw.color(col1);
                        Lines.stroke(stroke);
                        Lines.line(lsx, lsy, lex, ley, false);

                        Draw.z(Layer.blockProp + 0.001f);
                        Draw.color(col2);
                        Lines.stroke(stroke * 0.4f);
                        Lines.line(lsx, lsy, lex, ley, false);

                        if (DrawEndCircle) {
                            Draw.z(Layer.blockProp);
                            Draw.color(col1);
                            Fill.circle(lex, ley, stroke * 0.7f);
                            Draw.z(Layer.blockProp + 0.001f);
                            Draw.color(col2);
                            Fill.circle(lex, ley, stroke * 0.7f * 0.4f);
                        } else if (DrawEndGradient) {
                            Draw.z(Layer.blockProp - 0.01f);
                            Draw.color(col1);
                            Lines.stroke(stroke);
                            Lines.line(lex + 0.5f * stroke * dx, ley + 0.5f * stroke * dy, col1, lex + dx * 6f, ley + dy * 6f, endCol);

                            Draw.z(Layer.blockProp + 0.001f);
                            Draw.color(col2);
                            Lines.stroke(stroke * 0.4f);
                            Lines.line(lex + 0.5f * stroke * 0.4f * dx, ley + 0.5f * stroke * 0.4f * dy, col2, lex + dx * 5f, ley + dy * 5f, endCol);
                        }
                        Draw.reset();

                    }
                }
            }

        }

        public void hit(float sx, float sy, float ex, float ey,int r) {
            CachePoint[r] = new float[] {sx * 8,sy * 8,ex * 8,ey * 8};

        }
        public int[] getdx_dy(int r){
            int dx = 1, dy = 0;
            if (r == 1) {
                dx = 0;
                dy = 1;
            }
            ;
            if (r == 2) {
                dx = -1;
            }
            ;
            if (r == 3) {
                dx = 0;
                dy = -1;
            }
            ;
            return new int[] {dx,dy};
        }

        @Override
        public float efficiencyScale(){
            return Math.max(1,getpowerSum()*AddConsum+1f);
        }

    }


}
