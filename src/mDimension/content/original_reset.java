package mDimension.content;
import mDimension.entity.ability.SprintAbility;
import mindustry.content.*;
import mindustry.entities.bullet.LiquidBulletType;
import mindustry.type.*;
import mindustry.world.blocks.defense.turrets.LiquidTurret;

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
