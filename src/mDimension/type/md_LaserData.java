package mDimension.type;

import arc.math.geom.Point2;
import mDimension.tool.Vector2D;

/**激光数据类*/
public class md_LaserData {
    /// 最大长度
    public int maxLength = 10;
    /**波长*/
    public float waveLength = 500f;
    /**功率*/
    public float laserPower = 10f;
    /**偏振*/
    //public Vector2D basePolarization = new Vector2D(0,0);
   /// 如果为true 那么会被方块阻挡
    public boolean blocked = true;
    public md_LaserData(md_LaserData l ){
        this.maxLength = l.maxLength;
        this.waveLength = l.waveLength;
        this.laserPower = l.laserPower;
        //this.basePolarization = new Vector2D(l.basePolarization);
    }
    public md_LaserData(
            int maxLength,
            float wavelength,
            float laserPower,
            //Vector2D basePolarization,
            boolean blocked)
    {
        this.maxLength = maxLength;
        this.waveLength = wavelength;
        this.laserPower = laserPower;
        //this.basePolarization = basePolarization;
        this.blocked = blocked;
    }
    public md_LaserData(
            float laserPower
    )
    {
        this.laserPower = laserPower;
    }
    public md_LaserData(
            float wavelength,
            float laserPower
    )
    {
        this.waveLength = wavelength;
        this.laserPower = laserPower;
    }
}
