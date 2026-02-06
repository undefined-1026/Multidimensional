package mDimension.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.math.geom.Position;
import arc.struct.EnumSet;
import arc.struct.ObjectMap;
import arc.util.Time;
import mDimension.tool.Str;
import mDimension.tool.continuousRGB;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;
import mDimension.type.md_LaserCrafter.md_LaserCrafterBuild;
import mDimension.type.md_LaserProducer.md_LaserProducerBuild;
import java.awt.geom.Line2D;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;

import static mindustry.Vars.world;

public class md_LaserProcesser extends GenericCrafter{

    public boolean DrawRotation = true;
    public boolean du_li_Arrow = true;
    public boolean DrawHigherTop = true;


    public md_LaserProcesser(String name){
        super(name);
        rotate = true;
        rotateDraw = false;
        canOverdrive = false;
        drawArrow = true;
        category = Category.crafting;
        flags = EnumSet.of();

    }

    @Override
    public void setBars(){
        super.setBars();//Core.bundle.format("bar.efficiency", (int)(entity.amass + 0.01f), (int)(entity.efficiencyScale() * 100 + 0.01f)

        addBar("bunch power", (md_LaserProcesserBuild entity) ->
                new Bar(String.format("power|%s", Str.goStr(entity.mergeLaser.laserPower,1)),
                        new Color(0.8f,0.85f,1f),
                        ()->(entity.warmup)));
        addBar("wavelength", (md_LaserProcesserBuild entity) ->
                new Bar(String.format("wavelength|%dnm",(int)(entity.mergeLaser.waveLength)),
                        continuousRGB.contWhiteRGB(entity.mergeLaser.waveLength/1000,0.4f),
                        ()->(!entity.CacheLasers.isEmpty()?1f:1f)));
    }

    public class md_LaserProcesserBuild extends GenericCrafterBuild {
        public float lsx = this.x;
        public float lsy = this.y;
        public float lex = this.x;
        public float ley = this.y;
        public boolean DrawEndCircle = false;
        public boolean DrawEndGradient = false;
        public float laserWarmup = 0f;

        public ArrayList<md_LaserData> CacheLasers = new ArrayList<>();
        public md_LaserData mergeLaser = new md_LaserData(15,0,0,true);

        public void merge(){
            float wavelength = 0f;
            float power = 0f;

            for(md_LaserData l:CacheLasers){
                power+=l.laserPower;
                wavelength+=l.laserPower*l.waveLength;
            }
            wavelength/=power;

            power = Math.min(power,1024);
            wavelength = Math.min(wavelength,32768);
            wavelength = Math.max(wavelength,50f);
            mergeLaser.waveLength = wavelength;
            mergeLaser.laserPower = power;
            mergeLaser.maxLength = 15;

        }
        @Override
        public void updateTile() {

            if(!CacheLasers.isEmpty()&&efficiency>0&&mergeLaser != null) {
                md_LaserData l1 = new md_LaserData(mergeLaser);
                merge();
                laserWarmup = Mathf.approachDelta(laserWarmup, efficiency*Math.min(1f,mergeLaser.laserPower/25), 0.15f * delta());

                super.updateTile();
                int dx = getdx_dy()[0];
                int dy = getdx_dy()[1];
                int ox = 0;
                int oy = 0;
                float dl = 0.5F;
                boolean hit = false;
                DrawEndGradient = true;
                DrawEndCircle = false;
                for (int i = 0; i < mergeLaser.maxLength; i++) {
                    ox += dx;
                    oy += dy;
                    Building other = world.build(tile.x + ox, tile.y + oy);
                    if (other != null) {
                        if (other instanceof md_LaserProcesser2.md_LaserProcesser2Build other2) {
                            // 检查是否会形成循环：如果目标是激光处理器2，且我们的输出方向与它的输入方向相反
                            int oppositeDirection = (rotation + 2) % 4;
                            if (other2.CacheLasers[oppositeDirection] == null) {
                                other2.CacheLasers[rotation] = (l1);
                                dl = 0f;
                                DrawEndGradient = false;
                            }
                            break;
                        }
                        if (other instanceof md_LaserProcesserBuild other2) {
                            // 检查是否会形成循环：如果目标是激光处理器，且我们的输出方向与它的输入方向相反
                            int oppositeDirection = (rotation + 2) % 4;
                            int otherRotation = other2.rotation;
                            if (otherRotation != oppositeDirection) {
                                other2.CacheLasers.add(l1);
                                dl = 0f;
                                DrawEndGradient = false;
                            }
                            break;
                        }
                        if (other instanceof md_LaserCrafterBuild other2) {
                            other2.CacheLasers.add(l1);
                            DrawEndCircle = true;
                            break;
                        }
                        if (other.block.solid && l1.blocked) {
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
                CacheLasers.clear();
                hit(tile.x , tile.y , tile.x - dx * dl + ox, tile.y - dy * dl + oy);
            }else {
                laserWarmup = Mathf.approachDelta(laserWarmup, 0, 0.15f * delta());
            }


        }

        @Override
        public void draw() {

            super.draw();
            if(DrawHigherTop){
                Draw.z(Layer.blockProp+0.1f);
                Draw.rect(name+"-top",this.x(),this.y());
            }
            if(DrawRotation){
                if(du_li_Arrow){
                    Draw.z(Layer.blockProp+0.1001f);
                    Draw.rect(name+String.format("-arrow%d",rotation+1),this.x(),this.y());
                }else{
                    Draw.z(Layer.blockProp+0.1001f);
                    Draw.rect(name+"-arrow",this.x(),this.y(),rotation*90);
                }
            }
            if(laserWarmup>0.001f) {


                float stroke = (4f + Mathf.absin(Time.time+ (id % 9)*9, 3f, 0.50f)) * (float) Math.sqrt(laserWarmup);

                int dx = getdx_dy()[0];
                int dy = getdx_dy()[1];
                Lines.setCirclePrecision(0.9f);

                Color col1 = continuousRGB.contWhiteRGB(mergeLaser.waveLength/1000f,0.65f,0.95f);
                Color col2 = Color.white;
                Color endCol = Color.clear;

                Draw.z(Layer.blockProp);
                Draw.color(col1);
                Lines.stroke(stroke);
                Lines.line(lsx,lsy,lex,ley,false);

                Draw.z(Layer.blockProp+0.001f);
                Draw.color(col2);
                Lines.stroke(stroke*0.4f);
                Lines.line(lsx,lsy,lex,ley,false);

                if(DrawEndCircle){
                    Draw.z(Layer.blockProp);
                    Draw.color(col1);
                    Fill.circle(lex, ley, stroke * 0.7f);
                    Draw.z(Layer.blockProp + 0.001f);
                    Draw.color(col2);
                    Fill.circle(lex, ley, stroke * 0.7f * 0.4f);
                }else if (DrawEndGradient){
                    Draw.z(Layer.blockProp-0.01f);
                    Draw.color(col1);
                    Lines.stroke(stroke);
                    Lines.line(lex+0.5f*stroke*dx, ley+0.5f*stroke*dy, col1, lex+dx*6f, ley+dy*6f, endCol);

                    Draw.z(Layer.blockProp+0.001f);
                    Draw.color(col2);
                    Lines.stroke(stroke*0.4f);
                    Lines.line(lex+0.5f*stroke*0.4f*dx, ley+0.5f*stroke*0.4f*dy, col2, lex+dx*5f, ley+dy*5f, endCol);
                }
                Draw.reset();
            }
        }

        public void hit(float sx, float sy, float ex, float ey) {
            this.lsx = sx * 8;
            this.lsy = sy * 8;
            this.lex = ex * 8;
            this.ley = ey * 8;

        }
        public int[] getdx_dy(){
            int r = this.rotation;
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
    }


}
