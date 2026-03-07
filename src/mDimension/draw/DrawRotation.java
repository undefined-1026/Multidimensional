package mDimension.draw;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;

public class DrawRotation extends DrawBlock {
    public String suffix = "-arrow";
    public boolean independent = false;
    public boolean spinIndependent = false;
    public boolean drawPlan = true;
    public float layer = -1;
    public Color color;
    public TextureRegion[] regions;

    public DrawRotation(){
    }
    public DrawRotation(String suffix){
        this.suffix = suffix;
    }
    public DrawRotation(String suffix,boolean independent){
        this.suffix = suffix;
        this.independent = independent;
    }
    public DrawRotation(String suffix,boolean independent,boolean spinIndependent){
        this.suffix = suffix;
        this.independent = independent;
        this.spinIndependent = spinIndependent;
    }
    public DrawRotation(String suffix,boolean independent,float layer){
        this.suffix = suffix;
        this.independent = independent;
        this.layer = layer;
    }
    public DrawRotation(String suffix,boolean independent,boolean spinIndependent,float layer){
        this.suffix = suffix;
        this.independent = independent;
        this.spinIndependent = spinIndependent;
        this.layer = layer;
    }

    @Override
    public void draw(Building build) {
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        if(color != null) Draw.color(color);
        if(!independent){
            Draw.rect(regions[0],build.x,build.y,build.rotdeg());
        }else if(!spinIndependent){
            Draw.rect(regions[build.rotation],build.x,build.y);
        }else{
            Draw.rect(regions[build.rotation],build.x,build.y,build.rotdeg());
        }
        Draw.z(z);
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
        if(!drawPlan) return;
        if(!independent){
            Draw.rect(regions[0],plan.drawx(),plan.drawy(),plan.rotation*90);
        }else if(!spinIndependent){
            Draw.rect(regions[plan.rotation],plan.drawx(),plan.drawy());
        }else{
            Draw.rect(regions[plan.rotation],plan.drawx(),plan.drawy(),plan.rotation*90);
        }
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{regions[0]};
    }

    @Override
    public void load(Block b){
        if(!independent){
            regions = new TextureRegion[]{Core.atlas.find(b.name+suffix+1)};
        }else{
            regions = new TextureRegion[]{
                    Core.atlas.find(b.name+suffix+1),
                    Core.atlas.find(b.name+suffix+2),
                    Core.atlas.find(b.name+suffix+3),
                    Core.atlas.find(b.name+suffix+4)
            };
        }
    }
}
