package mDimension.content;

import arc.graphics.Color;
import mDimension.type.md_Fx;
import mindustry.type.StatusEffect;

public class md_status {
    public static StatusEffect
            dimension_slip;
    public static void load(){
        dimension_slip = new StatusEffect("dimension-slip"){{
            color = new Color(0xFFFFFFFF);
            healthMultiplier = 0.65f;
            damageMultiplier = 0.8f;
            speedMultiplier = 1.5f;
            damage = 4f;
            effect = md_Fx.dimension_vapor;
            effectChance = 0.03f;
        }};
    }
}
