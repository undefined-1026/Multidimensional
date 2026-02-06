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
import mDimension.type.md_LaserProcesser.md_LaserProcesserBuild;
import java.awt.geom.Line2D;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;

import static mindustry.Vars.world;

public class md_LaserProducer extends GenericCrafter{

    public md_LaserData Baselaser;
    public boolean DrawRotation = true;
    public boolean du_li_Arrow = true;
    public boolean DrawHigherTop = true;


    public md_LaserProducer(String name){
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

        addBar("bunch power", (md_LaserProducerBuild entity) ->
                new Bar(String.format("power|%s", Str.goStr(entity.laser.laserPower,1)),
                        new Color(0.8f,0.85f,1f),
                        ()->(entity.laserWarmup)));
        addBar("wavelength", (md_LaserProducerBuild entity) ->
                new Bar(String.format("wavelength|%dnm",(int)(entity.laser.waveLength)),
                        continuousRGB.contWhiteRGB(entity.laser.waveLength/1000,0.4f),
                        ()->(entity.efficiency)));
    }

    public class md_LaserProducerBuild extends GenericCrafterBuild {
        public float lsx = this.x;
        public float lsy = this.y;
        public float lex = this.x;
        public float ley = this.y;
        public boolean DrawEndCircle = false;
        public boolean DrawEndGradient = false;
        public float laserWarmup = 0f;

        public md_LaserData laser = new md_LaserData(Baselaser);
        @Override
        public void updateTile() {
            laserWarmup = Mathf.approachDelta(laserWarmup, efficiency*Math.min(1f,(Baselaser.laserPower/25)), 0.05f * delta());
            super.updateTile();
            if(laserWarmup>0.001f) {
                int dx = getdx_dy()[0];
                int dy = getdx_dy()[1];
                int ox = 0;
                int oy = 0;
                float dl = 0.5F;
                boolean hit = false;
                DrawEndGradient = true;
                DrawEndCircle = false;
                for (int i = 0; i < Baselaser.maxLength; i++) {
                    ox += dx;
                    oy += dy;
                    Building other = world.build(tile.x + ox, tile.y + oy);
                    if (other != null) {
                        if (other instanceof md_LaserProcesser2.md_LaserProcesser2Build other2) {
                            laser.laserPower = Baselaser.laserPower * efficiency;
                            other2.CacheLasers[rotation] = (laser);

                            dl = 0f;

                            DrawEndGradient = false;

                            break;
                        }
                        if (other instanceof md_LaserProcesserBuild other2) {
                            laser.laserPower = Baselaser.laserPower * efficiency;
                            other2.CacheLasers.add(laser);
                            dl = 0f;
                            DrawEndGradient = false;
                            break;
                        }
                        if (other instanceof md_LaserCrafterBuild other2) {
                            laser.laserPower = Baselaser.laserPower * efficiency;
                            other2.CacheLasers.add(laser);
                            DrawEndCircle = true;
                            break;
                        }
                        if (other.block.solid && Baselaser.blocked) {

                            if (!(other instanceof md_LaserProducerBuild)) {
                                DrawEndCircle = true;
                            } else {
                                dl = 0f;
                                DrawEndGradient = false;
                            }
                            break;
                        }
                    }
                }

                hit(tile.x + dx * 0.55f, tile.y + dy * 0.55f, tile.x - dx * dl + ox, tile.y - dy * dl + oy);
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
            if (laserWarmup>0.001f) {


                float stroke = (4f + Mathf.absin(Time.time+ (id % 9)*9, 3f, 0.50f))*(float) Math.sqrt(laserWarmup);

                Color col1 = continuousRGB.contWhiteRGB(Baselaser.waveLength/1000f,0.65f,0.95f);
                Color col2 = Color.white;
                Color endCol = Color.clear;

                int dx = getdx_dy()[0];
                int dy = getdx_dy()[1];
                Lines.setCirclePrecision(0.9f);

                Draw.z(Layer.blockProp);
                Draw.color(col1);
                Fill.circle( lsx, lsy, stroke * 0.7f);
                Draw.z(Layer.blockProp + 0.001f);
                Draw.color(col2);
                Fill.circle(lsx, lsy, stroke * 0.7f * 0.4f);



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
