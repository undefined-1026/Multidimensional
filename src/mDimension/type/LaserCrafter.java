package mDimension.type;

import arc.graphics.Color;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mDimension.content.md_beams;
import mDimension.entity.LaserEntity;
import mDimension.meta.md_StatValues;
import mDimension.tool.md_Edge;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Stat;

import java.util.Arrays;

import static mindustry.world.meta.StatValues.stack;


public class LaserCrafter extends GenericCrafter {
    public float beamPower = 10f;
    public Beam beam = md_beams.near_infrared_ligth;
    public LaserCrafter(String name){
        super(name);
        rotate = true;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.output,t->{
            t.add(md_StatValues.BeamStack(beam,beamPower));
        });
    }

    public class TestCrafterBuild extends GenericCrafterBuild{

        public LaserEntity[] crafterLasers = new LaserEntity[size];

        public int lastRotation;

        {
            Arrays.fill(crafterLasers,null);
            lastRotation = rotation;
        }

        @Override
        public void update(){
            super.update();
            if(lastRotation!=rotation){
                Arrays.fill(crafterLasers,null);
                lastRotation = rotation;
            }
            Vec2[] neardys = md_Edge.getFacingNearby(this);
            for(int i = 0;i<size;i++) {
                if (crafterLasers[i] == null) {
                    Vec2 p = neardys[i];
                    LaserEntity laserEntity = new LaserEntity(beam);
                    laserEntity.create(p.x, p.y,rotation, i);
                    md_Fx.waveColor(5f, 3f, 1f).at(p.x * 8, p.y * 8, Color.valueOf("FFFFFF"));
                } else {
                    crafterLasers[i].setPower(efficiency*warmup*beamPower/size);
                }
            }

        }

        @Override
        public void read(Reads read) {
            super.read(read);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
        }
    }
}
