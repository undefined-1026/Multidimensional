package mDimension.tool;

import arc.graphics.Color;

public class continuousRGB {
    public static Color contRGB(float v){
        v = v>1?1:v;v = v<0?0:v;
        v = 1-v;
        v*=6;
        float R = Math.abs(v-3f)-1f;
        float G = Math.abs(-1*Math.abs(v-2f)+3)-1;
        float B = Math.abs(-1*Math.abs(v-4f)+3)-1;

        R = R>1?1:R;G = G>1?1:G;B = B>1?1:B;
        R = R<0?0:R;G = G<0?0:G;B = B<0?0:B;
        return new Color(R,G,B);
    }
    public static Color contRGB(float v,float a){
        v = v>1?1:v;v = v<0?0:v;
        v = 1-v;
        v*=6;
        float R = Math.abs(v-3f)-1f;
        float G = Math.abs(-1*Math.abs(v-2f)+3)-1;
        float B = Math.abs(-1*Math.abs(v-4f)+3)-1;

        R = R>1?1:R;G = G>1?1:G;B = B>1?1:B;
        R = R<0?0:R;G = G<0?0:G;B = B<0?0:B;
        return new Color(R,G,B,a);
    }
    public static Color contWhiteRGB(float v,float p){
        v = v>1?1:v;v = v<0?0:v;
        v = 1-v;
        v*=6;
        float R = Math.abs(v-3f)-1f;
        float G = Math.abs(-1*Math.abs(v-2f)+3)-1;
        float B = Math.abs(-1*Math.abs(v-4f)+3)-1;

        R = R>1?1:R;G = G>1?1:G;B = B>1?1:B;
        R = R<0?0:R;G = G<0?0:G;B = B<0?0:B;
        R = (1-R)*p+R;G = (1-G)*p+G;B = (1-B)*p+B;
        return new Color(R,G,B);
    }
    public static Color contWhiteRGB(float v,float p,float a){
        v = v>1?1:v;v = v<0?0:v;
        v = 1-v;
        v*=6;
        float R = Math.abs(v-3f)-1f;
        float G = Math.abs(-1*Math.abs(v-2f)+3)-1;
        float B = Math.abs(-1*Math.abs(v-4f)+3)-1;

        R = R>1?1:R;G = G>1?1:G;B = B>1?1:B;
        R = R<0?0:R;G = G<0?0:G;B = B<0?0:B;
        R = (1-R)*p+R;G = (1-G)*p+G;B = (1-B)*p+B;
        return new Color(R,G,B,a);
    }
}
