package mDimension.content;
import arc.graphics.Color;
import mDimension.entity.ability.SprintAbility;
import mDimension.type.TestCrafter;
import mDimension.type.md_Fx;
import mindustry.content.*;
import mindustry.entities.bullet.LiquidBulletType;
import mindustry.entities.bullet.PointLaserBulletType;
import mindustry.gen.Hitboxc;
import mindustry.gen.UnitEntity;
import mindustry.type.*;
import mindustry.world.blocks.defense.turrets.ContinuousTurret;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.production.GenericCrafter;

import java.lang.reflect.Method;

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
        UnitType horizon = (UnitType) UnitTypes.horizon;
        horizon.abilities.add(new SprintAbility(){{speed = 10f;}});


    }
}
