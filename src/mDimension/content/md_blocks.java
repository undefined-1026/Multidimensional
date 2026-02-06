package mDimension.content;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import mDimension.type.*;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.DuctBridge;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.world.draw.*;
import mindustry.world.meta.Env;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static mindustry.type.ItemStack.with;

public class md_blocks {
    public static final String modN = "mdimension-";
    public static Block
        aluminium_electrolysis_cell,al_alloy_smelting,
        ti_alloy_smelting, helium_factory, laser_ganerator,test2,
            //运输
        beam_merging_prism,frequency_doubling_prism,power_exchange_prism,
        multiway_unloader,polymer_compressor,al_alloy_duct_bridge,
        ngm_launch_pad
    ;
    public static void load(){
        al_alloy_smelting = new GenericCrafter("al-alloy-smelting"){{
            squareSprite = false;
            id = 1145;
            health = 500;
            armor = 3;
            size = 3;
            buildTime = 3f;
            requirements(Category.crafting, ItemStack.with(
                md_items.aluminium,100,
                Items.lead,120,
                Items.silicon,80
            ));
            alwaysUnlocked = true;
            craftEffect = Fx.pulverizeMedium;
            outputItem = new ItemStack(md_items.al_alloy,3);
            consumeItems(ItemStack.with(
                    md_items.aluminium,4,
                    Items.silicon,2,
                    Items.copper,2
            ));
            consumePower(4f);
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(new Color(0xb0b0ffff)));
            craftTime = 90f;
            hasItems = true;
            hasPower = true;
            isDuct = true;


        }};
        ti_alloy_smelting = new HeatCrafter("ti-alloy-smelting"){{
            requirements(Category.crafting, ItemStack.with(
                    md_items.al_alloy,100,
                    Items.phaseFabric,45,
                    md_items.aluminium,150,
                    Items.silicon,120
            ));
            health = 500;
            armor = 3;
            size = 4;
            buildTime = 6f;
            heatRequirement = 12f;
            maxEfficiency = 4f;
            craftTime = 160f;
            itemCapacity = 20;
            consumeItems(ItemStack.with(md_items.aluminium,2,Items.titanium,6));
            consumeLiquid(md_liquids.helium,0.0131f);
            outputItem  = new ItemStack(md_items.ti_alloy,2);
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
            uiIcon = Core.atlas.find(modN+this.name+"-ui");

        }};
        helium_factory = new GenericCrafter("helium-factory"){{
            squareSprite = false;
            requirements(Category.crafting, ItemStack.with(
                    md_items.aluminium,60,
                    Items.silicon,40,
                    Items.lead,70,
                    Items.copper,40
            ));
            health = 500;
            armor = 3;
            buildTime = 3f;
            consumeLiquid(Liquids.hydrogen,0.1f);
            consumeItem(Items.phaseFabric,1);
            craftTime = 240f;
            size = 2;

            outputLiquid = new LiquidStack(md_liquids.helium,0.025f);
            hasItems = true;
            hasPower = true;
            craftEffect = md_Fx.polyWave(
                    4,10.31f,0,5,100f,new Color(0xffc0ffff),0.7f
            );

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.hydrogen,2f),
                    new DrawLiquidTile(md_liquids.helium,2f),
                    new DrawRegion(),
                    new DrawRegion("-top")

                    );

            consumePower(1f);
        }};
        aluminium_electrolysis_cell = new GenericCrafter("aluminium-electrolysis-cell"){{
            squareSprite = false;
            requirements(Category.crafting, ItemStack.with(
                    Items.copper,60,
                    Items.lead,50,
                    Items.silicon,30
            ));
            health = 500;
            armor = 3;
            buildTime = 1f;
            size = 2;
            itemCapacity = 20;
            consumeItem(md_items.bauxite,4);
            outputItem = new ItemStack(md_items.aluminium,3);
            consumePower(2f);
            craftEffect = new Effect(90f, e -> {
                color(new Color(0xfff2e0ff));
                alpha(e.fout());

                randLenVectors(e.id, 5, 3f + e.finpow() * 7f, (x, y) -> {
                    Fill.poly(e.x + x, e.y + y,4, 0.6f + e.fin() * 5f,45);
                });
            });
            craftTime = 120f;

            drawer = new DrawMulti(

                    new DrawRegion("-bottom"),
                    new DrawRegion(),
                    new DrawFlame(new Color(0xe8e8e8ff)){{
                        flameRadius = 1.5f;
                        flameRadiusIn = 1f;
                        flameRadiusMag = 0.5f;
                        flameRadiusInMag = 0.33f;
                    }}
                    );
        }};
        laser_ganerator = new md_LaserProducer("laser-generator"){{
            size = 1;
            consumePower(1f);
            hasPower = true;
            hasItems = false;
            alwaysUnlocked = true;
            Baselaser = new md_LaserData(500f,3f);
            fullOverride = modN+"laser-generator"+"-private";
            conductivePower = true;

        }};
        test2 = new md_LaserCrafter("test2"){{
            size = 3;
            consumePower(1f);
            consumeItem(Items.sand,1);
            outputItem = new ItemStack(Items.silicon,1);
            craftTime = 15f;
            hasPower = true;
            hasItems = true;
            alwaysUnlocked = true;
        }};
        beam_merging_prism = new md_LaserProcesser("beam-merging-prism"){{
            size = 1;
            fullOverride = this.name+"-private";
            hasItems = false;
            hasPower = false;
            conductivePower = true;

        }};
        frequency_doubling_prism = new md_LaserProcesser2("frequency-doubling-prism"){{
            size = 1;
            fullOverride = this.name+"-private";
            hasItems = false;
            hasPower = true;
            consumePower(4f);
            consumeItem(Items.phaseFabric,1);
            craftTime = 600f;
            conductivePower = true;
            powerAdd = -6f;
            wavelengthAdd = -50f;
        }};
        power_exchange_prism = new md_LaserProcesser2("power-exchange-prism"){{
            size = 1;
            fullOverride = this.name+"-private";
            hasItems = true;
            hasPower = true;
            consumePower(2f);
            consumeItem(Items.silicon,1);
            craftTime = 300f;
            conductivePower = true;
            powerAdd = 5f;
            wavelengthAdd = 100f;
        }};
        multiway_unloader = new md_MultiwayUnloader("multiway-unloader"){{
            requirements(Category.distribution,ItemStack.with(
                    Items.silicon,50,
                    md_items.aluminium,50,
                    Items.titanium,80,
                    Items.lead,80
            ));
            size = 1;
            health = 120;
            speed = 3f;
            solid = true;
            underBullets = true;
            regionRotated1 = 1;

        }};
        polymer_compressor = new GenericCrafter("polymer-compressor"){{
            requirements(Category.crafting,ItemStack.with(
                    md_items.aluminium,70,
                    md_items.al_alloy,40,
                    Items.silicon,100,
                    Items.titanium,80
            ));
            size = 3;
            consumeLiquids(LiquidStack.with(Liquids.oil,25f/60f,Liquids.hydrogen,2f/60f));
            consumePower(4f);
            outputItem = new ItemStack(md_items.polymer,2);
            craftTime = 30f;

        }};
        al_alloy_duct_bridge = new DuctBridge("al-alloy-duct-bridge"){{
            requirements(Category.distribution, with(Items.silicon,25,Items.titanium,20,md_items.al_alloy,15));
            health = 150;
            speed = 2f;
            range = 6;
            buildCostMultiplier = 3f;
            researchCostMultiplier = 0.3f;
        }};
        ngm_launch_pad = new md_LaunchPadCrefter("ngm-launch-pad"){{
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
            launchEffect = md_Fx.loadLaunch(launchTime,this.name+"-pod",17f*8f,0f,1f,1f);
            landEffect = md_Fx.loadLand(landTime,this.name+"-returnpod",17*8f,0,1,1.5f,loadStayTime);
            requirements(Category.crafting, with(Items.beryllium, 70, Items.graphite, 80));
            consumePower(5f);

            consumeItems(ItemStack.with(
                    md_items.ti_alloy,150,
                    md_items.al_alloy,250,
                    md_items.polymer,200
                    ));
            consumeLiquids(LiquidStack.with(Liquids.hydrogen,100f/60f,Liquids.ozone,1f));
            outputItem = new ItemStack(Items.copper,50);
            outputLiquid = new LiquidStack(md_liquids.dimension_fluid,1000);
            drawer = new DrawMulti(new DrawLiquidTile(md_liquids.dimension_fluid,5f),new DrawRegion());
        }};

    }

}
