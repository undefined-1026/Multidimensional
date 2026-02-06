package mDimension.type;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mDimension.entity.VisibleEffect;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;

import arc.graphics.Color;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Shaders;
import mindustry.type.UnitType;
import mindustry.world.Block;

import javax.swing.plaf.synth.Region;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.world;

public class md_Fx {
    public static final Effect


    dimension_vapor =
    new Effect(150f, e -> {
        color(new Color(0xffffb0ff));
        alpha(e.fout());

        randLenVectors(e.id, 3, 3f + e.finpow() * 11f, (x, y) -> {
            Fill.poly(e.x + x, e.y + y,4, 0.6f + e.fin() * 5f);
        });
    }),
    dimension_vapor_small = new Effect(90f, e -> {
        color(new Color(0xffffb0ff));
        alpha(e.fout());

        randLenVectors(e.id, 2, 1.8f + e.finpow() * 7f, (x, y) -> {
            Fill.poly(e.x + x, e.y + y,4, 0.6f + e.fin() * 5f);
        });
    }),
    dimension_vapor_big =
            new Effect(120, e -> {
                color(new Color(0xffffb0ff));
                alpha(e.fout());

                randLenVectors(e.id, 3, 4f + e.finpow() * 35f, (x, y) -> {
                    Fill.poly(e.x + x, e.y + y,4, 2f + e.fin() * 8f);
                });
            }),

    polyWave = new Effect(100, e -> {
        color(new Color(0xffc0ffff), 0.7f);
        stroke(e.fout() * 5f);
        Lines.poly(e.x, e.y, 4,2f + e.finpow()*8.31f);
    }),

    catFly = new Effect(500,9999f,e ->{
        z(111);
        TextureRegion region = Core.atlas.find("cat");
        float dy = e.fin()*8f*30f;
        float dr = (float)Math.pow(e.fin(),2f)*360f*2.5f;
        rect(region,e.x,e.y+dy,16,16,dr);
    }),

    plan = new Effect(120f,80f,e->{
        TextureRegion region = Core.atlas.find("cat");
        Draw.draw(Layer.blockBuilding,()->{
            Shaders.blockbuild.region = region;
            Shaders.blockbuild.time = Time.time;
            Shaders.blockbuild.progress = e.fin();

            Draw.rect(region, e.x, e.y);
            Draw.flush();
        });
        Draw.reset();
    }),

    regionFlash = new Effect(70, e -> {
        if(!(e.data instanceof TextureRegion region)) return;
        alpha(e.fout());
        mixcol(Color.valueOf("ffe3bf"), e.fout());
        rect(region, e.x , e.y, e.rotation);
    }).layer(Layer.flyingUnit+5f),


    awa = Fx.none;
    public static Effect  polyWave(int side,float radius,float rotate,float stroke,float life,Color color,float alpha){
        return new Effect(life, e -> {
            color(new Color(color), alpha);
            stroke(e.fout() * stroke);
            Lines.poly(e.x, e.y, side, e.finpow() * (radius),rotate);
        });
    }
    public static VisibleEffect loadLaunch(float life,String regionName,float trip,float tripXM,float tripYM,float flameSize){
        return  new VisibleEffect(life,9999f,e->{
            float stallRatio = 0.22f;
            z(Layer.effect+1);
            TextureRegion loadRegion = Core.atlas.find(regionName);
            float fi = (float)Math.pow(Math.max(0,(e.fin()-stallRatio)/(1-stallRatio)),5);
            float rotation = 0;
            float dx = fi*(tripXM);
            float dy = fi*(tripYM);
            float cx = e.x+dx*trip;
            float cy = e.y+dy*trip;
            float size = 0.25f*(fi+1f);
            float alpha = e.fout(Interp.pow5Out);
            float scale = (1.0F - alpha) * 1.3F + 1.0F;
            float rad = 0.2F + e.fslope();
            alpha(alpha);
            z(Layer.effect+1f);
            rect(loadRegion,cx,cy,loadRegion.width*size,loadRegion.height*size);
            if(Time.time%(fi<0.01f?10f:5f)<Time.delta&&!Vars.state.isPaused()) {
                if (fi<0.01f) {
                    dimension_vapor(50f,1f,4f).at(cx, cy);
                } else {
                    dimension_vapor(100f,1f-fi,1.3f).at(cx, cy);
                }
            }
            z(Layer.effect+0.01f);
            Draw.color(Pal.engine);
            alpha(alpha*0.75f);
            for(int i = 0; i < 4; ++i) {
                Drawf.tri(cx, cy, 12F, 60f*flameSize*(rad + scale - 1.0F), (float)i * 90f+rotation);
            }
            for(int i = 0; i < 4; ++i) {
                Drawf.tri(cx, cy, 18f, 35f*flameSize*(rad + scale - 1.0F), (float)i * 90f+rotation+45F);
            }
            Fill.light(cx, cy, 16, 35.0F * (rad + scale - 1.0F), Tmp.c2.set(Pal.engine).a(alpha), Tmp.c1.set(Pal.engine).a(0.0F));
            z(Layer.effect+1.1f);
            Fill.light(cx, cy, 10, 20.0F, Tmp.c2.set(new Color(1f,0.7f,0.65f,1f)).a(Math.max(0,fi*alpha*2f)), Tmp.c1.set(Pal.engine).a(0.0F));

        });
    }
    public static VisibleEffect loadLand(float life,String regionName,float trip,float tripXM,float tripYM,float flameSize,float stayTime){
        return new VisibleEffect(life,9999f,e->{
            float fob = (float)Math.pow((e.fout()-stayTime/life)/(1-stayTime/life),5f);
            float fo = Math.max(0f,fob);
            float alpha = e.fin(Interp.pow5Out);

            float dx = fo*tripXM;
            float dy = fo*tripYM;

            float cx = e.x +dx*trip;
            float cy = e.y +dy*trip;

            float size = (fo+1f)*0.25f;
            float rad = 0.2F + (float)Math.sin(3.1415f*e.fin()/(1-stayTime/life));
            float scale = (1.0F - alpha) * 1.3F + 1.0F;


            TextureRegion loadRegion = Core.atlas.find(regionName);
            z(Layer.effect+1f);

            if(fob>0f) {
                alpha(alpha);
                rect(loadRegion,cx,cy,loadRegion.width*size,loadRegion.height*size,0f);
                Draw.color(Pal.engine);
                z(Layer.effect);
                alpha(alpha*0.9f);
                for (int i = 0; i < 4; ++i) {
                    Drawf.tri(cx, cy, 10f*flameSize, 30f * flameSize * (rad + scale - 1.0F), (float) i * 90f);
                }
            }else {
                float landalpha = e.fout()/(stayTime/life);
                alpha(landalpha);
                rect(loadRegion,e.x,e.y);
            }



;        });
    }
    public static Effect dimension_vapor(float life,float alpha,float size){
        return new Effect(life, e -> {
            color(new Color(1f,1f,0.647f,1f));
            alpha(e.fout()*alpha);

            randLenVectors(e.id, 3, (3f + e.finpow() * 11f)*size, (x, y) -> {
                Fill.poly(e.x + x, e.y + y,4, (0.6f + e.fin() * 5f)*((size-1)*0.7f+1));
            });
        });
    }
    /**precision是绘制渐变的边数*/
    public static Effect gradientWave(float life,float rad,Color formColor,float FCalpha,Color toColor,float TCalpha,int precision){
        return new Effect(life,rad*2,e->{

            float alpha = e.fout();
            color(toColor);
            Lines.stroke(3f*e.fout());
            float radd = rad*e.finpow();
            z(Layer.effect-11f);
            Fill.light(e.x,e.y,precision,radd,formColor.a(FCalpha*alpha*0.8f),toColor.a(TCalpha*alpha*0.8f));
            z(Layer.effect);
            alpha(1f);
            Lines.circle(e.x,e.y,radd);
        });
    }
    public static Effect Mulitpleslash(float life,int number,Color color,float length,float width,float eccentricity){
        return new Effect(life,length*2,e->{
            float len = length * (e.fout()*0.2f+0.8f);
            float wid = width * e.fout();
            color(color);
            randLenVectors(e.id,number,eccentricity,(x,y)->{
                float rotat = Mathf.randomSeed((long)(e.id+Mathf.len(x,y)),0f,360f);
                for(int i = 0;i<2;i++){

                    Drawf.tri(e.x+x,e.y+y,wid,len/2,i*180+rotat);
                }
            });
        });
    }
    public static Effect polygonalStar(float life,int number,Color color,float length,float width,float rotation){
        return new Effect(life,length*2,e->{
            float len = length * (e.fout()*0.2f+0.8f);
            float wid = width * e.fout();
            color(color);
            for(int i=0;i<number;i++){
                Drawf.tri(e.x,e.y,wid,len,i*(360f/number)+rotation);
            }
        });
    }
    public static Effect polygonalStar(float life,int number,Color color,float length,float width,float rotation,float deltaRotation){
        return new Effect(life,length*2,e->{
            float len = length * (e.fout()*0.2f+0.8f);
            float wid = width * e.fout();
            color(color);
            for(int i=0;i<number;i++){
                Drawf.tri(e.x,e.y,wid,len,i*deltaRotation+rotation);
            }
        });
    }
}
