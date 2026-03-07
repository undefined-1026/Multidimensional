package mDimension.type;

import arc.math.geom.Vec2;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;

public class md_BeamDeflector extends GenericCrafter {
    public boolean canDeflectorParticle = false;
    public Vec2 afterRotation = new Vec2(1,0);

    public md_BeamDeflector(String name){
        super(name);
        rotate = true;
        rotateDraw = false;
    }
    public class md_BeamDeflectorBuild extends GenericCrafterBuild {
    }
}
