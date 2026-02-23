package mDimension.tool;

import java.awt.*;

public class COLOR {
    public static Color mix(Color c1,Color c2){
        float qw = 2;
        int R = (int)(c1.getRed()/qw+c2.getRed()/qw);
        int G = (int)(c1.getRed()/qw+c2.getRed()/qw);
        int B = (int)(c1.getRed()/qw+c2.getRed()/qw);
        int A = (int)(c1.getRed()/qw+c2.getRed()/qw);
        return new Color(R,G,B,A);
    }
    /*proportion 为c1所占百分比**/
    public static Color mix(Color c1,Color c2,float proportion){
        float q2 = 1-proportion;
        float qw = 1;
        int R = (int)(c1.getRed()* proportion /qw+c2.getRed()*q2/qw);
        int G = (int)(c1.getRed()* proportion /qw+c2.getRed()*q2/qw);
        int B = (int)(c1.getRed()* proportion /qw+c2.getRed()*q2/qw);
        int A = (int)(c1.getRed()* proportion /qw+c2.getRed()*q2/qw);
        return new Color(R,G,B,A);
    }
    public static Color turnBlack(Color c1,float proportion){
        float q2 = 1-proportion;
        Color c2 = new Color(0,0,0,1);
        float qw = proportion +q2;
        int R = (int)(c1.getRed()* proportion /qw+c2.getRed()*q2/qw);
        int G = (int)(c1.getRed()* proportion /qw+c2.getRed()*q2/qw);
        int B = (int)(c1.getRed()* proportion /qw+c2.getRed()*q2/qw);
        int A = (int)(c1.getRed()* proportion /qw+c2.getRed()*q2/qw);
        return new Color(R,G,B,A);
    }
    public static Color turnWhite(Color c1,float proportion){
        float q2 = 1-proportion;
        Color c2 = new Color(1,1,1,1);
        float qw = proportion +q2;
        int R = (int)(c1.getRed()* proportion /qw+c2.getRed()*q2/qw);
        int G = (int)(c1.getRed()* proportion /qw+c2.getRed()*q2/qw);
        int B = (int)(c1.getRed()* proportion /qw+c2.getRed()*q2/qw);
        int A = (int)(c1.getRed()* proportion /qw+c2.getRed()*q2/qw);
        return new Color(R,G,B,A);
    }
}
