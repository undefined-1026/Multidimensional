package mDimension.tool;

public class Vector2D {
    public float angle;
    public float len;
    public Vector2D(Vector2D v){
        this.angle = v.angle;
        this.len = v.len;
    }
    public Vector2D(float angle){
        this.angle = angle;
        this.len = 1f;
    }
    public Vector2D(float angle,float len){
        this.angle = angle;
        this.len = len;
    }

    public static float innerProduct(Vector2D v1, Vector2D v2){
        return (float) Math.cos(v1.angle-v2.angle)*v1.len;
    }

    public Vector2D mix(Vector2D v1 ,Vector2D v2){
        float absAngle = Math.abs(v1.angle-v2.angle);
        return new Vector2D(
        Math.min(absAngle,360f-absAngle),
        ((v1.len+v2.len)/2)
        );

    }

}
