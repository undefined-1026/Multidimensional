package mDimension.type;

import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;

public class tool {
    public static Block getBlock(int x,int y){
        Tile tile = Vars.world.tile(x,y);
        return tile ==null?null:tile.block();
    }
    public static Building getBuilding(int x, int y){
        Tile tile = Vars.world.tile(x,y);
        return tile ==null?null:tile.build;
    }
}
