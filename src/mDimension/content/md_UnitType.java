package mDimension.content;

import annotations.Annotations.*;
import arc.graphics.Color;
import mDimension.type.DepicilonUnitType;
import mDimension.type.md_Fx;
import mindustry.content.Fx;
import mindustry.entities.bullet.ContinuousFlameBulletType;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.weapons.RepairBeamWeapon;

public class md_UnitType {
    public static @EntityDef({Unitc.class, ElevationMovec.class}) UnitType shimmer;

    public static void load(){
        shimmer = new DepicilonUnitType("shimmer"){{
            constructor = ElevationMoveUnit::create;

            hovering = true;
            canDrown = false;
            shadowElevation = 0.1f;
            softShadowScl = 0.7f;

            drag = 0.08f;
            speed = 1.8f;
            rotateSpeed = 6f;

            accel = 0.07f;

            health = 850f;
            armor = 2f;
            hitSize = 11f;

            engineSize = 0;
            itemCapacity = 15;
            setEnginesMirror(
                    new UnitEngine(17f/4f,-14f/4f,2.5f,-45f)
            );

            useEngineElevation = false;
            researchCostMultiplier = 0f;
            moveSound = Sounds.loopExtract;
            moveSoundVolume = 0.25f;
            moveSoundPitchMin = 0.7f;
            moveSoundPitchMax = 1.5f;
            weapons.add(new Weapon("shimmer-weapon"){{
                range = 15.5f;
                alwaysContinuous = true;
                mirror = false;
                top = false;
                shootY = 4f;
                x = y = 0f;
                shootSound = Sounds.none;
                activeSound = Sounds.shootSublimate;
                activeSoundVolume = 1.5f;
                bullet = new ContinuousFlameBulletType(){{
                    damage = 40f;
                    length = 9f;
                    width = 4.5f;
                    ammoMultiplier = 1.2f;
                    knockback = 1f;
                    pierceCap = 2;
                    buildingDamageMultiplier = 0.3f;

                    hitColor = flareColor = Color.valueOf("FFDB78");
                    flareLength = 7f;
                    flareWidth = 3f;
                    colors = new Color[]{
                            Color.valueOf("DB7D42").a(0.45f),
                            Color.valueOf("E8AC58").a(0.65f),
                            Color.valueOf("FFDB78").a(0.85f),
                            Color.white};
                }};
            }});
        }};
    }

}
