package mDimension.plante;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.util.noise.Simplex;
import mDimension.content.md_Planets;
import mDimension.content.md_items;
import mindustry.content.Planets;
import mindustry.maps.generators.BaseGenerator;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.Sector;

public class DepicilonPlanetGenerator extends PlanetGenerator {
    //alternate, less direct generation
    public static boolean indirectPaths = false;
    //random water patches
    public static boolean genLakes = false;

    BaseGenerator basegen = new BaseGenerator();
    float heightYOffset = 42.7f;
    float scl = 5f;
    float waterOffset = 0.04f;
    float heightScl = 1.01f;
    static float[] count = {0,0};

    @Override
    protected void generate(){
        Object[] sectors = Planets.serpulo.sectors.items;
        for(int i = 0; i < md_Planets.depicilon.sectors.size; i ++){
            var sector = (Sector)sectors[i];

            Vec3 g = sector.tile.v;

        }

    }

    @Override
    public float getHeight(Vec3 position){
        float height = rawHeight(position);
        if (height>0.72f){
            height += 0.3f;
        }else if (height > 0.68f) {
            height += 0.15f;
        }else if (height > 0.58f) {
            height -= 0.1f;
        }else if (height <0.42f){
            height -=0.2f;
        }
        return height * 0.7f;
    }

    @Override
    public void getColor(Vec3 position, Color out){
//            Block block = getBlock(position);
//            //replace salt with sand color
//            if(block == Blocks.salt) block = Blocks.sand;
//            out.set(block.mapColor).a(1f - block.albedo);
        float height = rawHeight(position);
        float latit = getAbsLatitude(position);
        latit = (float)Math.pow(latit,0.7f);
        Color toC = Color.valueOf("FFFFE8");
        Color c = Color.valueOf("524727");
        if(height>0.72f){
            c.lerp(toC,0.8f*latit);
        }else if(height>0.68f){
            c.lerp(toC,0.55f*latit);
        }else if(height>0.58f){
            c.lerp(toC,0.35f*latit);
        }else if(height<0.42f){
            c.lerp(toC,0.2f*latit);
        }
        out.set(c);
    }
    // -1~~0~~1  ->  -90~~0~~90
    float getLatitude(Vec3 v){
        return (float) (Math.asin(v.y)/(Math.PI/2));
    }
    float getAbsLatitude(Vec3 v){
        return (float) (Math.abs(Math.asin(v.y)/(Math.PI/2)));
    }

    float rawHeight(Vec3 position) {
        return Simplex.noise3d(seed, 7, 0.5f, 1f / 3f, position.x * scl, position.y * scl + heightYOffset, position.z * scl) * heightScl;
    }

}