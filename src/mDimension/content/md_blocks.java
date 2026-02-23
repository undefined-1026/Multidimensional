package mDimension.content;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import mDimension.entity.EntityShield;
import mDimension.entity.bullet.BallLightningBulletType;
import mDimension.entity.bullet.EntityCrafterBulletType;
import mDimension.entity.pattern.ShootSwing;
import mDimension.type.*;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.type.Weapon;
import mindustry.type.unit.MissileUnitType;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ContinuousTurret;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.distribution.Duct;
import mindustry.world.blocks.distribution.DuctBridge;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static mindustry.type.ItemStack.with;

public class md_blocks {
    public static final String modname = "mdimension-";
    public static Block
            aluminium_electrolysis_cell, al_alloy_smelting,
            ti_alloy_smelting, helium_factory, laser_ganerator, test2,
    //distribution
    beam_merging_prism, frequency_doubling_prism, power_exchange_prism,
            multiway_unloader, polymer_compressor, al_alloy_duct_bridge,
            ngm_launch_pad, light_duct, stack_rail_conveyor,
    //turret
    ionize, fracture,break_water, polarization,dawn,
    //core
    coreSteady,
    //power
    internal_energy_pile;


    public static void load() {
        crafter:
        {
            al_alloy_smelting = new GenericCrafter("al-alloy-smelting") {{
                squareSprite = false;
                id = 1145;
                health = 500;
                armor = 3;
                size = 3;
                buildTime = 3f;
                requirements(Category.crafting, ItemStack.with(
                        md_items.aluminium, 100,
                        Items.lead, 120,
                        Items.silicon, 80
                ));
                alwaysUnlocked = true;
                craftEffect = Fx.pulverizeMedium;
                outputItem = new ItemStack(md_items.al_alloy, 3);
                consumeItems(ItemStack.with(
                        md_items.aluminium, 4,
                        Items.silicon, 2,
                        Items.copper, 2
                ));
                consumePower(4f);
                drawer = new DrawMulti(new DrawDefault(), new DrawFlame(new Color(0xb0b0ffff)));
                craftTime = 90f;
                hasItems = true;
                hasPower = true;
                isDuct = true;


            }};
            ti_alloy_smelting = new HeatCrafter("ti-alloy-smelting") {{
                requirements(Category.crafting, ItemStack.with(
                        md_items.al_alloy, 100,
                        Items.phaseFabric, 45,
                        md_items.aluminium, 150,
                        Items.silicon, 120
                ));
                health = 500;
                armor = 3;
                size = 4;
                buildTime = 6f;
                heatRequirement = 12f;
                maxEfficiency = 4f;
                craftTime = 160f;
                itemCapacity = 20;
                consumeItems(ItemStack.with(md_items.aluminium, 2, Items.titanium, 6));
                consumeLiquid(md_liquids.helium, 0.0131f);
                outputItem = new ItemStack(md_items.ti_alloy, 2);
                hasItems = true;
                hasPower = true;
                craftEffect = Fx.pulverizeMedium;
                DrawCrucibleFlame flame = new DrawCrucibleFlame();
                flame.flameColor = new Color(0xf1e8ffff);
                flame.midColor = new Color(0xf5edffff);
                flame.flameRadiusMag = 2f;
                flame.flameRadiusScl = 40f;
                flame.flameRad = 5f;
                flame.particleLife = 110f;
                flame.particleRad = 12f;
                flame.particleSize = 5;
                flame.alpha = 0.7f;
                consumePower(4f);
                drawer = new DrawMulti(
                        new DrawRegion("-bottom"),
                        new DrawLiquidTile(md_liquids.helium),
                        flame,
                        new DrawRegion(),
                        new DrawHeatInput(),
                        new DrawRegion("-top")
                );
                uiIcon = Core.atlas.find(this.name + "-ui");

            }};
            helium_factory = new GenericCrafter("helium-factory") {{
                squareSprite = false;
                requirements(Category.crafting, ItemStack.with(
                        md_items.aluminium, 60,
                        Items.silicon, 40,
                        Items.lead, 70,
                        Items.copper, 40
                ));
                health = 500;
                armor = 3;
                buildTime = 3f;
                consumeLiquid(Liquids.hydrogen, 0.1f);
                consumeItem(Items.phaseFabric, 1);
                craftTime = 240f;
                size = 2;

                outputLiquid = new LiquidStack(md_liquids.helium, 0.025f);
                hasItems = true;
                hasPower = true;
                craftEffect = md_Fx.polyWave(
                        4, 10.31f, 0, 5, 100f, new Color(0xffc0ffff), 0.7f
                );

                drawer = new DrawMulti(
                        new DrawRegion("-bottom"),
                        new DrawLiquidTile(Liquids.hydrogen, 2f),
                        new DrawLiquidTile(md_liquids.helium, 2f),
                        new DrawRegion(),
                        new DrawRegion("-top")

                );

                consumePower(1f);
            }};
            aluminium_electrolysis_cell = new GenericCrafter("aluminium-electrolysis-cell") {{
                squareSprite = false;
                requirements(Category.crafting, ItemStack.with(
                        Items.copper, 60,
                        Items.lead, 50,
                        Items.silicon, 30
                ));
                health = 500;
                armor = 3;
                buildTime = 1f;
                size = 2;
                itemCapacity = 20;
                consumeItem(md_items.bauxite, 4);
                outputItem = new ItemStack(md_items.aluminium, 3);
                consumePower(2f);
                craftEffect = new Effect(90f, e -> {
                    color(new Color(0xfff2e0ff));
                    alpha(e.fout());

                    randLenVectors(e.id, 5, 3f + e.finpow() * 7f, (x, y) -> {
                        Fill.poly(e.x + x, e.y + y, 4, 0.6f + e.fin() * 5f, 45);
                    });
                });
                craftTime = 120f;

                drawer = new DrawMulti(

                        new DrawRegion("-bottom"),
                        new DrawRegion(),
                        new DrawFlame(new Color(0xe8e8e8ff)) {{
                            flameRadius = 1.5f;
                            flameRadiusIn = 1f;
                            flameRadiusMag = 0.5f;
                            flameRadiusInMag = 0.33f;
                        }}
                );
            }};
            laser_ganerator = new md_LaserProducer("laser-generator") {{
                size = 1;
                consumePower(1f);
                hasPower = true;
                hasItems = false;
                alwaysUnlocked = true;
                Baselaser = new md_LaserData(500f, 3f);
                fullOverride = this.name + "-private";
                conductivePower = true;

            }};
            test2 = new md_LaserCrafter("test2") {{
                size = 3;
                consumePower(1f);
                consumeItem(Items.sand, 1);
                outputItem = new ItemStack(Items.silicon, 1);
                craftTime = 15f;
                hasPower = true;
                hasItems = true;
                alwaysUnlocked = true;
            }};
            beam_merging_prism = new md_LaserProcesser("beam-merging-prism") {{
                size = 1;
                fullOverride = this.name + "-private";
                hasItems = false;
                hasPower = false;
                conductivePower = true;

            }};
            frequency_doubling_prism = new md_LaserProcesser2("frequency-doubling-prism") {{
                size = 1;
                fullOverride = this.name + "-private";
                hasItems = false;
                hasPower = true;
                consumePower(4f);
                consumeItem(Items.phaseFabric, 1);
                craftTime = 600f;
                conductivePower = true;
                powerAdd = -6f;
                wavelengthAdd = -50f;
            }};
            power_exchange_prism = new md_LaserProcesser2("power-exchange-prism") {{
                size = 1;
                fullOverride = this.name + "-private";
                hasItems = true;
                hasPower = true;
                consumePower(2f);
                consumeItem(Items.silicon, 1);
                craftTime = 300f;
                conductivePower = true;
                powerAdd = 5f;
                wavelengthAdd = 100f;
            }};

            polymer_compressor = new GenericCrafter("polymer-compressor") {{
                requirements(Category.crafting, ItemStack.with(
                        md_items.aluminium, 70,
                        md_items.al_alloy, 40,
                        Items.silicon, 100,
                        Items.titanium, 80
                ));
                size = 3;
                consumeLiquids(LiquidStack.with(Liquids.oil, 25f / 60f, Liquids.hydrogen, 2f / 60f));
                consumePower(4f);
                outputItem = new ItemStack(md_items.polymer, 2);
                craftTime = 30f;

            }};

            ngm_launch_pad = new md_LaunchPadCrafter("ngm-launch-pad") {{
                size = 10;
                hasItems = true;
                hasLiquids = true;
                itemCapacity = 500;
                liquidCapacity = 2000;
                baseHoverTime = 120f;
                craftTime = 300f;
                health = 8000;
                squareSprite = false;
                launchTime = 400f;
                landTime = 200f;
                launchEffect = md_Fx.loadLaunch(launchTime, this.name + "-pod", 17f * 8f, 0f, 1f, 1f);
                landEffect = md_Fx.loadLand(landTime, this.name + "-returnpod", 17 * 8f, 0, 1, 1.5f, loadStayTime);
                requirements(Category.crafting, with(Items.beryllium, 70, Items.graphite, 80));
                consumePower(5f);

                consumeItems(ItemStack.with(
                        md_items.ti_alloy, 150,
                        md_items.al_alloy, 250,
                        md_items.polymer, 200
                ));
                consumeLiquids(LiquidStack.with(Liquids.hydrogen, 100f / 60f, Liquids.ozone, 1f));
                outputItem = new ItemStack(Items.copper, 50);
                outputLiquid = new LiquidStack(md_liquids.dimension_fluid, 1000);
                drawer = new DrawMulti(new DrawLiquidTile(md_liquids.dimension_fluid, 5f), new DrawRegion());
            }};

            TestCrafter e = new TestCrafter("entity-test") {{
                requirements(Category.crafting, with());
                size = 1;
                craftTime = 60f;
            }};
        }
        distribution:
        {
            al_alloy_duct_bridge = new DuctBridge("al-alloy-duct-bridge") {{
                requirements(Category.distribution, with(Items.silicon, 10, Items.titanium, 10, md_items.al_alloy, 10));
                health = 200;
                armor = 2;
                speed = 2f;
                range = 6;
                buildCostMultiplier = 3f;
                researchCostMultiplier = 0.3f;
                squareSprite = false;
            }};

            light_duct = new Duct("light-duct") {{
                requirements(Category.distribution, with(Items.silicon, 1, md_items.aluminium, 1));
                speed = 3f;
                health = 180;
                armor = 2;
                bridgeReplacement = al_alloy_duct_bridge;
                buildCostMultiplier = 0.5f;
                researchCost = ItemStack.with(Items.silicon, 20, md_items.aluminium, 20);
                fullOverride = this.name + "-private";
            }};

            multiway_unloader = new md_MultiwayUnloader("multiway-unloader") {{
                requirements(Category.distribution, ItemStack.with(
                        Items.silicon, 20,
                        md_items.al_alloy, 20,
                        Items.titanium, 30
                ));
                squareSprite = false;
                size = 1;
                health = 120;
                speed = 3f;
                solid = true;
                underBullets = true;
                regionRotated1 = 1;

            }};

            stack_rail_conveyor = new StackConveyor("stack-rail-conveyor") {{
                requirements(Category.distribution, with(md_items.aluminium, 1, md_items.al_alloy, 1, Items.silicon, 1));
                health = 210;
                armor = 3;
                itemCapacity = 15;
                speed = 6 / 60f;
            }};
        }
        turret:
        {
            ionize = new ContinuousTurret("ionize") {{
                requirements(Category.turret, ItemStack.with(Items.copper, 50, Items.silicon, 20, md_items.aluminium, 20));
                //outlineColor = Pal.darkOutline;
                shootType = new PointLaserBulletType() {{
                    beamEffect = md_Fx.polyFacula(4, 3.2f, 0, 60f, Color.valueOf("d0d0ff"), 0.87f);
                    beamEffectInterval = 6f;
                    hitSound = Sounds.shootAtrax;
                    shootSound = Sounds.shootMerui;
                    hitEffect = md_Fx.Mulitpleslash(20f, 1, Color.valueOf("d0d0ff"), 24f, 3f, 8f);
                    damageInterval = 12f;
                    setDefaults = false;
                    despawnHit = false;
                    fragOnDespawn = false;
                    sprite = modname + "ionize-point-laser";
                    targetAir = true;
                    targetGround = false;
                    damage = 40f / (60f / damageInterval);

                    pierceArmor = true;
                    hitColor = Color.valueOf("d0d0ff");
                    fragAngle = 90;
                    fragVelocityMin = 1;
                    fragVelocityMax = 1.2f;
                    fragLifeMax = 1f;
                    fragLifeMin = 1.5f;
                    fragOffsetMax = 1f;
                    fragOffsetMin = 1f;
                    fragBullets = 1;
                    fragBullet = new BasicBulletType(2.5f, 10f) {{

                        lifetime = 20;
                        pierce = true;
                        pierceCap = 2;
                        targetAir = true;
                        targetGround = false;
                        width = 6f;
                        height = 8f;
                        homingPower = 0.5f;
                        trailLength = 6;
                        trailWidth = 1.2f;
                        despawnEffect = hitEffect = md_Fx.polyWave(16, 3, 0, 2.4f, 16f, Color.valueOf("f5f5ff"), 0.7f);
                        trailColor = backColor = hitColor = Color.valueOf("d0d0ff");
                        frontColor = Color.valueOf("f5f5ff");
                    }};

                }};
                scaledHealth = 250;
                armor = 2;
                scaleDamageEfficiency = true;
                recoil = 0f;
                range = 8 * 30f;
                aimChangeSpeed = 14f;
                rotateSpeed = 14f;
                drawer = new DrawTurret("steady-state-") {{
                    Color heatc = Pal.turretHeat;
                    heatColor = heatc;
                    parts.addAll(
                            new RegionPart("-blade") {{
                                progress = PartProgress.warmup;
                                heatProgress = PartProgress.warmup;
                                heat = Core.atlas.find(modname + "ionize" + suffix + "-heat");
                                mirror = true;
                                moveX = 3f / 4f;
                                moveY = 3f / 4f;
                                under = true;
                                heatColor = heatc;
                            }}
                    );
                }};
                size = 1;
                consumePower(4f);
            }};

            fracture = new ItemTurret("fracture") {{
                requirements(Category.turret, ItemStack.with(md_items.aluminium, 120, Items.silicon, 80, Items.titanium, 80));
                ammo(
                        md_items.aluminium, new BasicBulletType(7f, 10) {{
                            hitColor = backColor = frontColor = md_items.aluminium.color;
                            trailColor = new Color(0xd0c0c0f0);
                            sprite = "circle";
                            width = 6;
                            height = 6;
                            shrinkX = 0;
                            shrinkY = 0;
                            lifetime = 32f;
                            trailWidth = 2.5f;
                            trailLength = 8;
                            trailSinScl = 8f;

                            reloadMultiplier = 1.2f;
                            status = md_StatusEffects.embrittlement;
                            statusDuration = 0.5f * 60f;

                            ammoMultiplier = 4;

                            splashDamage = 20f;
                            splashDamageRadius = 35;
                            hitEffect = despawnEffect = new MultiEffect(
                                    md_Fx.polyWave(48, 35, 0, 4f, 25f, hitColor, 0.85f),
                                    md_Fx.spatter,
                                    md_Fx.polygonalStar(40f, 4, hitColor.a(0.85f), 45f, 7f, 45f)
                            );
                        }},
                        md_items.al_alloy, new BasicBulletType(8f, 15f) {{
                            hitColor = backColor = frontColor = md_items.al_alloy.color;
                            trailColor = new Color(0xd0d0d8f8);
                            sprite = "circle";
                            width = 7;
                            height = 7;
                            shrinkX = 0;
                            shrinkY = 0;
                            lifetime = 32f;
                            trailWidth = 2.7f;
                            trailLength = 8;
                            trailSinScl = 8f;
                            rangeChange = 4 * 8f;

                            status = md_StatusEffects.embrittlement;
                            statusDuration = 1.5f * 60f;
                            ammoMultiplier = 4;

                            splashDamage = 30f;
                            splashDamageRadius = 37;
                            hitEffect = despawnEffect = new MultiEffect(
                                    md_Fx.polyWave(48, splashDamageRadius, 0, 4f, 25f, hitColor, 0.85f),
                                    md_Fx.spatter,
                                    md_Fx.polygonalStar(40f, 4, hitColor.a(0.85f), splashDamageRadius + 10f, 8f, 45f)
                            );
                            despawnHit = true;
                            fragBullets = 3;
                            fragBullet = new LaserBulletType() {{
                                colors = new Color[]{new Color(0xe2e2fff0), md_items.al_alloy.color, Color.white};
                                length = 30f;
                                width = 12f;
                                sideWidth = 3.5f;
                                sideLength = 8f;
                                sideAngle = 60f;
                                damage = 15;
                                pierceArmor = true;
                                lifetime = 40f;
                            }};

                        }}
                );
                reload = 10f;
                range = 28 * 8f;
                shootEffect = Fx.shootSmokeSquareBig;

                shoot = new ShootMulti(
                        new ShootBarrel() {{
                            barrels = new float[]{
                                    -12f, -4f, -1f,
                                    6f, -1f, 0.8f,
                                    -6f, -1f, -0.8f,
                                    12f, -4f, 1f
                            };
                        }},
                        new ShootHelix() {{
                            scl = 2f;
                            mag = 2f;
                        }}
                );
                recoils = 4;
                warmupMaintainTime = 30f;
                shootSound = Sounds.shootMissilePlasmaShort;
                soundPitchMin = 1.2f;
                soundPitchMax = 1.4f;
                minWarmup = 0.90f;
                heatColor = Color.valueOf("d8d8ff");

                drawer = new DrawTurret("steady-state-") {{
                    for (int i : new int[]{1, 4, 2, 3}) {
                        int f = i;
                        parts.add(new RegionPart("-barrel-" + i) {{
                            progress = PartProgress.recoil;
                            recoilIndex = f - 1;
                            under = true;
                            moveY = -3f;

                            if (f == 1) {
                                moves.add(new PartMove(PartProgress.warmup, -15 / 4f, 9 / 4f, 0f));
                            } else if (f == 2) {
                                moves.add(new PartMove(PartProgress.warmup, 9f / 4f, 12f / 4f, 0f));
                            } else if (f == 3) {
                                moves.add(new PartMove(PartProgress.warmup, -9f / 4f, 12f / 4f, 0f));
                            } else {
                                moves.add(new PartMove(PartProgress.warmup, 15f / 4f, 9f / 4f, 0f));
                            }

                        }});
                    }
                    parts.add(new RegionPart("-side") {{
                        heatColor = Color.valueOf("d8d8ff");
                        under = true;
                        mirror = true;
                        progress = PartProgress.warmup;
                        heatProgress = PartProgress.warmup;
                        moveX = 1f;
                        moveY = -1f;
                        x = -0.1f;
                        y = 0.1f;

                    }});
                }};
                hasLiquids = true;

                size = 3;
                scaledHealth = 250;
                armor = 3;
                outlineColor = Pal.darkOutline;
            }};

            break_water = new ItemTurret("break-water") {{
                hideDatabase = true;
                scaledHealth = 250;

                requirements(Category.turret, with(md_items.al_alloy, 120, md_items.polymorphic_crystal, 50, Items.phaseFabric, 80, Items.silicon, 150));
                ammo(
                        Items.phaseFabric, new BallLightningBulletType(7f, 20f,"mine-bullet") {{
                            shrinkX= shrinkY = 0;
                            lifetime = 30f;
                            lightningRange = 80f;
                            lightningDamage = 30;
                            lightning = 10;
                            lightningCooldown = 10f;
                            shockLimit = 1;
                            frontColor = Color.valueOf("f8f8ff");
                            lightningColor = backColor = trailColor = hitColor = Color.valueOf("d8e0ff");
                            hitEffect = new MultiEffect(
                                    md_Fx.polyWave(4,25,45,5f,35f,hitColor,0.95f),
                                    md_Fx.spatter
                            );
                            fragBullets = 1;
                            width = 32f;
                            height = 32f;
                            trailLength = 12;
                            spin = 10f;
                            trailWidth = 4f;
                            fragOffsetMax = 0;
                            fragOffsetMin = 0;
                            fragSpread = fragRandomSpread = intervalRandomSpread = 0f;
                            fragBullet = new EntityCrafterBulletType() {{
                                        hitEffect = Fx.none;
                                        craft = b -> {
                                            EntityShield shield = new EntityShield() {{
                                                sides = 4;
                                                radius = 40f;
                                                shieldRotation = 0f;
                                                shieldHealth = 500;
                                                lifeTime = 300;
                                            }};
                                            shield.create(b.x, b.y, b.rotation()+45f, b.team, Color.valueOf("e0e8ff"));
                                        fragBullets = 4;
                                        fragLifeMax = 1.2f;
                                        fragLifeMin = 0.6f;
                                        fragVelocityMax = fragVelocityMin = 1;
                                        fragBullet = new BasicBulletType(2f,20,"mine-bullet"){{
                                            backRegion = Core.atlas.find("mine-bullet-back");
                                            frontRegion = Core.atlas.find("mine-bullet");
                                            width = 16;
                                            height = 16;
                                            shrinkX= shrinkY = 0.5f;
                                            splashDamageRadius = 28f;
                                            splashDamage = 40f;
                                            lifetime = 16f;
                                            backColor = hitColor = Color.valueOf("d8e0ff");
                                            despawnEffect = hitEffect = new MultiEffect(
                                                    md_Fx.polyWave,
                                                    md_Fx.polygonalStar(30f,4,hitColor,15f,4f,0)
                                                    );
                                            despawnHit = true;
                                        }};
                                        };
                                    }};

                        }}
                );
                range = 8f * 45;
                size = 4;
                reload = 120f;
                shootY = 0;
                shoot = new ShootSwing() {{
                    startRotation = -12;
                    endRotation = 12;
                    shots = 3;
                    shotDelay = 5f;
                    drawer = new DrawTurret("steady-state-");
                }};
            }};

            dawn = new ItemTurret("dawn"){{
                requirements(Category.turret,with());
                scaledHealth = 250;
                predictTarget = false;
                shootSound = Sounds.shootMissileLong;
                moveWhileCharging = false;
                soundPitchMax = 0.8f;
                soundPitchMin = 0.65f;
                shoot.firstShotDelay = 50f;
                rotateSpeed = 1.5f;
                ammo(
                        md_items.polymorphic_crystal,new BallLightningBulletType(48f/60f,120,"large-orb"){{

                            lifetime = 60f*6;
                            despawnHit = true;
                            splashDamage = 200f;
                            splashDamageRadius = 72f;
                            chargeEffect = md_Fx.dawnCharge;
                            shootEffect = new MultiEffect(md_Fx.starExplosionSmall,md_Fx.spatterBig);

                            hitSound = Sounds.explosionReactor2;
                            hitSoundVolume = 0.45f;
                            hitEffect = new MultiEffect(md_Fx.gradientWave(40f,50f),md_Fx.starExplosionBig);

                            trailColor = lightningColor = hitColor = backColor = md_items.polymorphic_crystal.color;

                            trailLength = 8;
                            trailWidth = 3f;

                            width = height = 18f;
                            shrinkX = shrinkY = 0;

                            bulletDrawer = b->{
                                Draw.color(hitColor);
                                for(int i: Mathf.zeroOne){
                                Drawf.tri(b.x,b.y,10,30, Time.time%360+i*180f);

                                Drawf.tri(b.x,b.y,10,20, (Time.time*-2f)%360+i*180f);
                                }
                            };

                            lightningEffect = new MultiEffect(md_Fx.chainLightningBig,md_Fx.waveColor(25,10,3.5f));
                            lightningDamage = 120f;
                            lightningRange = 100f;
                            maxStrikes = 20;
                            shockLimit = 3;

                            lightningCooldown = 12f;
                            lightningStatu = md_StatusEffects.dimension_slip;
                            statusDuration = 2*60f;

                            collidesAir = false;
                            collidesGround = false;

                            intervalBullets = 2;
                            bulletInterval = 6f;
                            intervalBullet = new LightningBulletType(){{
                                damage = 15f;
                                lightningLength = 9;
                                lightningLengthRand = 4;
                                lightningColor = md_items.polymorphic_crystal.color;
                            }};
                        }}
                );
                range = 8f*35;
                ammoPerShot = 5;
                size = 4;
                reload = 120f;
                warmupMaintainTime = 40f;
                shootY = 15.5f;
                minWarmup = 0.99f;
                shootWarmupSpeed = 0.06f;

                drawer = new DrawTurret("steady-state-"){{
                    parts.addAll(
                            new RegionPart("-mid-under-blade"){{
                                mirror = true;
                                x = -1.5f;
                                progress = PartProgress.warmup;
                                moveY = 7.8f;
                                moveX = 6.5f;
                                moves.add(new PartMove(PartProgress.recoil,1,-2,0));
                                under = true;
                            }},
                            new RegionPart("-mid-under-l"){{
                                progress = PartProgress.warmup;
                                moveY = 7.8f;
                                moveX = -5f;
                                moves.add(new PartMove(PartProgress.recoil,-1,-2,0));
                                under = true;
                            }},
                            new RegionPart("-mid-under-r"){{
                                progress = PartProgress.warmup;
                                moveY = 7.8f;
                                moveX = 5f;
                                moves.add(new PartMove(PartProgress.recoil,1,-2,0));
                                under = true;
                            }},

                            new RegionPart("-blade-der-l"){{
                                x = 3f;
                                moves.add(new PartMove(PartProgress.warmup,-10f,4f,30));
                                moves.add(new PartMove(PartProgress.recoil,0,0,5));
                                under = true;
                            }},
                            new RegionPart("-blade-l"){{
                                moves.add(new PartMove(PartProgress.warmup,-8f,4f,30));
                                moves.add(new PartMove(PartProgress.recoil,0,0,5));
                                under = true;
                            }},

                            new RegionPart("-blade-der-r"){{
                                x = -3f;
                                moves.add(new PartMove(PartProgress.warmup,10f,4f,-30));
                                moves.add(new PartMove(PartProgress.recoil,0,0,-5));
                                under = true;
                            }},
                            new RegionPart("-blade-r"){{
                                moves.add(new PartMove(PartProgress.warmup,8f,4f,-30));
                                moves.add(new PartMove(PartProgress.recoil,0,0,-5));
                                under = true;
                            }},
                            new RegionPart("-mid-middle"){{
                                under = true;
                            }},
                            new RegionPart("-mid"){{
                                moveY = 2f;
                                moves.add(new PartMove(PartProgress.recoil,0,-2,0));
                                under = false;
                            }}


                    );
                }};

                outlineColor = Pal.darkOutline;
            }};

            polarization = new ItemTurret("polarization"){{
                requirements(Category.turret,with());
                scaledHealth = 250;

                ammo(
                        md_items.plasma,new BulletType(0,0){{
                            shootEffect = Fx.none;
                            smokeEffect = md_Fx.shootSmokeMissileSmallColor;
                            hitColor =  Color.valueOf("c0d8ff");
                            ammoMultiplier = 1f;
                            spawnUnit = new MissileUnitType("polarization-missile"){{
                                softShadowScl = 0.6f;
                                speed = 6f;
                                maxRange = 6f;
                                lifetime = 60f*2.25f;
                                hitSize = 10f;
                                outlineColor = Pal.darkOutline;
                                engineColor = trailColor = Color.valueOf("c0d8ff");
                                engineLayer = Layer.effect;
                                engineSize = 2.2f;
                                engineOffset = 8f;
                                rotateSpeed = 1f;
                                trailLength = 18;
                                missileAccelTime = 40f;
                                lowAltitude = true;
                                loopSound = Sounds.loopMissileTrail;
                                loopSoundVolume = 0.6f;
                                deathSound = Sounds.explosionMissile;
                                targetAir = true;
                                targetUnderBlocks = false;

                                fogRadius = 4f;

                                health = 200;

                                weapons.add(new Weapon() {{
                                    shootCone = 360f;
                                    mirror = false;
                                    reload = 1f;
                                    deathExplosionEffect = Fx.massiveExplosion;
                                    shootOnDeath = true;
                                    shake = 10f;
                                    bullet = new ExplosionBulletType(500, 45) {{
                                        hitColor = Color.valueOf("c0d8ff");
                                        shootEffect = new MultiEffect(md_Fx.starExplosionBig,md_Fx.spatterBig,new WaveEffect() {{
                                            lifetime = 20f;
                                            strokeFrom = 4f;
                                            sizeTo = 60f;
                                        }});

                                        collidesAir = true;
                                        buildingDamageMultiplier = 0.1f;

                                        ammoMultiplier = 1;
                                        fragLifeMax = 1.2f;
                                        fragLifeMin = 0.8f;
                                        fragBullets = 1;
                                        fragBullet = new BallLightningBulletType(0f, 70,"large-orb") {{
                                            shrinkX = 0.3f;
                                            shrinkY = 0.3f;

                                            lightningCooldown = 12f;
                                            lightningStatu = StatusEffects.shocked;
                                            lightningEffect = new MultiEffect(md_Fx.starExplosionSmall,Fx.chainLightning);
                                            lightning = 5;
                                            lightningRange = 80f;
                                            lightningDamage = damage;
                                            lightningColor = backColor = trailColor = hitColor = Color.valueOf("c0d8ff");

                                            collidesAir = false;
                                            collidesGround = false;
                                            collidesTiles = false;
                                            buildingDamageMultiplier = 0.1f;
                                            drag = 0.02f;
                                            hitEffect = md_Fx.starExplosion;
                                            despawnHit = true;
                                            despawnSound = Sounds.shootEnergyField;
                                            knockback = 0.8f;
                                            lifetime = 70f;
                                            width = height = 34f;

                                            splashDamageRadius = 60f;
                                            splashDamage = 120;

                                            frontColor = Color.white;
                                        }};
                                    }};
                                }});
                            }};
                        }}
                );
                shootY = 0;
                float[] barr = new float[]{
                        -12f,1.5f,0,
                        -6.5f,3f,0,
                        6.5f,3f,0f,
                        12f,1.5f,0f
                };;
                shoot = new ShootBarrel(){{
                    barrels = barr;
                    shots = 4;
                    shotDelay = 12f;

                }};

                shootSound = Sounds.shootMissileLarge;
                soundPitchMax = 0.57f;
                soundPitchMin = 0.45f;
                shootSoundVolume = 0.65f;

                reload = 12*60f;
                warmupMaintainTime = 200f;
                shootWarmupSpeed = 0.045f;
                minWarmup = 0.98f;
                coolant = consumeCoolant(20f/60f);
                coolantMultiplier = 2f;
                range = 8f*80;


                drawer = new DrawTurret("steady-state-"){{
                    parts.addAll(
                            new RegionPart("-mid"){{
                                under = false;
                            }},
                            new RegionPart("-outside-l"){{
                                moves.add(new PartMove(PartProgress.warmup,-20f/4f,0,0));
                                under = true;
                            }},
                            new RegionPart("-outside-r"){{
                                moves.add(new PartMove(PartProgress.warmup,20f/4f,0,0));
                                under = true;
                            }},
                            new RegionPart("-inside-l"){{
                                moves.add(new PartMove(PartProgress.warmup,-10f/4f,0,0));
                                under = true;
                            }},
                            new RegionPart("-inside-r"){{
                                moves.add(new PartMove(PartProgress.warmup,10f/4f,0,0));
                                under = true;
                            }}
                    );
                    for(int i=0;i<4;i++){
                        int f = i;
                        parts.add(new RegionPart("-missile"){{
                            x = barr[f*3]*0.5f;
                            y = barr[f*3+1]*0.5f;
                            moves.add(new PartMove(PartProgress.warmup,barr[f*3]*0.5f,barr[f*3+1]*0.5f,0));
                            progress = PartProgress.reload.curve(Interp.pow2In);
                            colorTo = new Color(1f, 1f, 1f, 0f);
                            color = Color.white;
                            mixColorTo = Pal.accent;
                            mixColor = new Color(1f, 1f, 1f, 0f);
                            outline = false;
                            under = true;
                            layerOffset = -0.01f;
                        }});
                    }
                }};
                size = 4;
            }};
        }
        core:
        {
            coreSteady = new md_ElectricFieldCoreBlock("core-steady"){{
                requirements(Category.effect, BuildVisibility.coreZoneOnly, with(Items.silicon, 1200, md_items.aluminium,1500,Items.titanium,1500));
                alwaysUnlocked = true;
                hasPower = true;
                conductivePower = true;

                powerProduction = 5f;

                isFirstTier = true;
                unitType =  UnitTypes.evoke;
                armor = 5;
                health = 4000;
                itemCapacity = 6000;
                buildCostMultiplier = 1f;
                thrusterLength = 38f/4f;

                lightningEffect = new MultiEffect(Fx.chainLightning, md_Fx.waveColor(20f,12f,4f));

                unitCapModifier = 15;
                size = 4;
                fullOverride = this.name + "-private";
            }};
        }
        power:
        {
            internal_energy_pile = new md_MonoblockBattery("internal-energy-pile"){{
                requirements(Category.power,with());
                health = 300;
                armor = 2;
                consumePowerBuffered(6000f);
                size = 1;
                drawer = new DrawMulti(new DrawDefault(),
                        new DrawRegion("-content"){{
                            color = Color.valueOf("995A46");
                        }},
                        new DrawRegion("-shard1"),
                        new DrawRegion("-shard2"),
                        new DrawRegion("-shard3"),
                        new DrawRegion("-shard4"),
                        new DrawRegion("-shard5"),
                        new DrawRegion("-shard6"),
                        new DrawRegion("-shard7"),
                        new DrawRegion("-shard8")
                        );
            }};
        }

    }
}
