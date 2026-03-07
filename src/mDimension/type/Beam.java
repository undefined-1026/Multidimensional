package mDimension.type;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.GL20;
import arc.graphics.Gl;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import mDimension.MDLines;
import mDimension.entity.LaserEntity;
import mindustry.content.Items;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.graphics.Drawf;
import mindustry.graphics.Shaders;
import mindustry.logic.LAccess;
import mindustry.logic.Senseable;

public class Beam extends UnlockableContent implements Senseable {
    public static final Blending cover = new Blending(Gl.one,Gl.zero,Gl.one,Gl.zero);
    public LaserDrawer laserDrawer= l->{
        basicDraw(l, Color.valueOf("ffd080"));
    };
    public static void basicDraw(LaserEntity l){basicDraw(l,Color.white);}
    public static void basicDraw(LaserEntity l,Color color){basicDraw( l, color, 2.5f);}
    public static void basicDraw(LaserEntity l,Color color,float rad){basicDraw( l, color, rad, 1f,110f);}
    public static void basicDraw(LaserEntity l,Color color,float rad,float alpha,float Layer){
        rad *= (Mathf.sin(Time.time+Mathf.randomSeed(l.id* 114L)*50,24,0.08f)+1f);
        color = color.a(Math.min(alpha*l.laserData.power/10,1));
        Draw.color(color);
        Draw.z(Layer);
        Lines.stroke(rad*1.4f);
        for(int i = 1;i<l.points.size;i++){
            Vec2 lp = l.points.get(i-1);
            Vec2 np = l.points.get(i);
            Lines.line(lp.x,lp.y  ,  np.x,np.y , false);
            if(l.points.size>2 && i!=l.points.size-1){
                Fill.circle(np.x,np.y,rad*0.7f);
            }
        }
        float sx = l.points.get(0).x,sy = l.points.get(0).y,
                tx = l.points.get(l.points.size-1).x,ty = l.points.get(l.points.size-1).y;
        Fill.circle(sx,sy,rad);
        if(l.isBlocked){
            Fill.circle(tx,ty,rad);
        }else {
            MDLines.line2(Core.atlas.white(), tx, ty, color.toFloatBits(), tx+l.rotation.x*4f, ty+l.rotation.y*4f, color.cpy().a(0).toFloatBits(),false);
        }
        Draw.reset();
    }

    public static void DrawPacket(LaserEntity l,Color color,float rad,float alpha,float Layer,float life) {
        if (l.points.size < 2) return;
        float lastTime = Time.time;
        float fin = (((Time.time + Mathf.randomSeed(l.id)*life) % life) / life)*1.25f;

        Draw.z(Layer);
        if(fin<1f) {
            alpha *= (float) (1 - Math.pow(2 * fin - 1, 6));
            Draw.color(color.a(alpha));
            if(fin<0.3f && !l.isBlocked) {
                Lines.setCirclePrecision(1f);
                Lines.stroke((1.2f-fin/0.3f)*rad);
                Lines.circle(l.points.get(0).x,l.points.get(0).y,rad*2.2f*(fin/0.3f));
            }
            float[] para = new float[l.points.size - 1];
            float totLen = 0;
            for (int i = 1; i < l.points.size; i++) {
                Vec2 lp = l.points.get(i - 1);
                Vec2 np = l.points.get(i);
                float paraLen = Mathf.len(np.x - lp.x, np.y - lp.y);
                para[i - 1] = paraLen;
                totLen += paraLen;
            }
            float lenFin = fin * totLen;
            float max = 0;
            int f = 0;
            for (int i = 0; i < para.length; i++) {
                max += para[i];
                f = i;
                if (max >= lenFin) break;
            }

            float useLen = max - lenFin;
            Vec2 node = l.points.get(f + 1);
            Vec2 rotat = new Vec2(
                    l.points.get(f + 1).x - l.points.get(f).x,
                    l.points.get(f + 1).y - l.points.get(f).y
            ).nor();
            Fill.circle(node.x - useLen * rotat.x, node.y - useLen * rotat.y, rad);
        }else if(!l.isBlocked){
            Draw.color(color.a(alpha));
            float cfin = (fin-1) /0.25f;
            for(int i = 0;i<4;i++){
                Vec2 end = l.points.get(l.points.size-1);
                Drawf.tri(end.x,end.y,rad*(1-cfin),rad*2f*(2-cfin),i*90f);
            }
        }

    }

    public interface LaserDrawer{
        void draw(LaserEntity laserEntity);
    }

    public int wavelengthLeve = 3;

    public int lenght = 15;

    public boolean hasDamage = false;

    public boolean targetAir = false;

    public boolean targetGround = true;

    public Color color;
    //如果是的，会在子分支里显示，并且无法被激光使用的棱镜转向
    public boolean isParticle  = false;

    public Beam(String name){
        super(name);
        this.databaseCategory = "beam";
        this.color = Color.white;
        if(isParticle){
            this.databaseTag = "particle";
        }else {
            this.databaseTag = "laser";
        }
    }
    public Beam(String name,Color color){
        super(name);
        this.databaseCategory = "beam";
        this.color = color;
        if(isParticle){
            this.databaseTag = "particle";
        }else {
            this.databaseTag = "laser";
        }
    }
    @Override
    public ContentType getContentType() {
        return ContentType.error;
    }

    @Override
    public double sense(LAccess sensor) {
        return 0;
    }
}