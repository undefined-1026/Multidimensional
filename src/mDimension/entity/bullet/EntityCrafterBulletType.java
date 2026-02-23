package mDimension.entity.bullet;

import arc.graphics.Color;
import mDimension.type.md_Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;

public class EntityCrafterBulletType extends BasicBulletType {
    //public Entityc entity;
    public craftEntity craft = (b)->{
    };

    public EntityCrafterBulletType(){
        super();
        //this.entity = entity;
        lifetime = 0;
        speed = 0;
        damage = 0;
        hittable = false;
    }

    public interface craftEntity{
        void craft(Bullet b);
    }

    @Override
    public void init(Bullet b){
        super.init(b);
        craft.craft(b);

    }
}
