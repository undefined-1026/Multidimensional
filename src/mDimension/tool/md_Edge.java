package mDimension.tool;

import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import mindustry.gen.Building;

public class md_Edge {
    public static Vec2[] getFacingNearby(Building b){
        if(b.block.size<=1)return new Vec2[]{new Vec2(b.x,b.y)};
        int size = b.block.size;
        Vec2[] points = new Vec2[size];
        for(int i = 0;i<size;i++){
            points[i] = transpose(new Vec2(((size-1)/2f)*8f,((size-1)/2f)*8f-i*8),b.rotation);
        }
        for(int i = 0;i<size;i++){
            points[i].add(b.x,b.y);
        }
        return points;
    }

    public static Vec2 transpose(Vec2 v, int r){
        if(r == 0)return v;
        float x = v.x;
        float y = v.y;
        switch (r){
            case(1)-> v.set(-1*y,x);
            case(2)-> v.set(-1*x,-1*y);
            case(3)-> v.set(y,-1*x);
        }
        return v;
    }

    public static Vec2 direction (int r){
        switch (r){
            case(1)-> {
                return new Vec2(0,1);
            }
            case(2)-> {
                return new Vec2(-1,0);
            }
            case(3)-> {
                return new Vec2(0,-1);
            }
        }
        return new Vec2(1,0);

    }
}
