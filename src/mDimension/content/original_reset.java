package mDimension.content;
import arc.graphics.Color;
import mDimension.type.md_Fx;
import mindustry.content.*;
import mindustry.entities.bullet.LiquidBulletType;
import mindustry.type.*;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.production.GenericCrafter;

public class original_reset {
    public static void load() {
        LiquidTurret tsunami = (LiquidTurret) Blocks.tsunami;
        tsunami.ammoTypes.put(
                md_liquids.dimension_fluid,new LiquidBulletType(md_liquids.dimension_fluid){{
                    lifetime = 50f;
                    speed = 6f;
                    knockback = 1.3f;
                    puddleSize = 8f;
                    orbSize = 4f;
                    drag = 0.001f;
                    ammoMultiplier = 0.4f;
                    statusDuration = 60f * 4f;
                    damage = 10f;
                    rangeChange = 95f;
                    statusDuration = 600f;
                    trailEffect = Fx.trailFade;
                    }
                }
        );

        GenericCrafter o = (GenericCrafter) Blocks.graphitePress;
        o.craftEffect = md_Fx.plan;
        o.craftTime = 240f;
        o = (GenericCrafter) Blocks.siliconSmelter;
        o.craftEffect = md_Fx.dimension_vapor_small;

    }
}
