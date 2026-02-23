package mDimension.meta;

import mindustry.world.meta.Stat;

public class md_Stat{
    public static Stat
            percentageDamage,percentageReply,armorAdditional,armorMultiplier,percentageShieldDamage;
    public static void load(){
        percentageDamage = new Stat("percentageDamage");
        percentageReply = new Stat("percentageReply");
        armorAdditional = new Stat("armorAdditional");
        armorMultiplier = new Stat("armorMultiplier");
        percentageShieldDamage = new Stat("percentageShieldDamage");
    }
}
