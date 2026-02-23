package mDimension.type;

import mDimension.entity.EntityShield;
import mindustry.world.blocks.production.GenericCrafter;

public class TestCrafter extends GenericCrafter {
    public TestCrafter(String name){
        super(name);
    }
    public class TestCrafterBuild extends GenericCrafterBuild{
        @Override
        public void craft(){
            super.craft();
            EntityShield e = new EntityShield(this.team);
            e.create(x,y,0);
        }
    }
}
