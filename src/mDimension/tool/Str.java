package mDimension.tool;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Str {
    public static String goStr(float f,int acc){
        BigDecimal b = new BigDecimal(Float.toString(f));
        b = b.setScale(acc, RoundingMode.HALF_UP);
        return b.toString();
    };
}
