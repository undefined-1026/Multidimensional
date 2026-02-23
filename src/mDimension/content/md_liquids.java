package mDimension.content;
import arc.graphics.Color;
import mindustry.type.*;
import mDimension.type.md_Fx;

public class md_liquids {
    public static Liquid
            helium,dimension_fluid;
    public static void load(){

        helium = new Liquid("helium",new Color(0xffcfffff)){{
            gas = true;
            flammability = 0f;
            explosiveness = 0f;
            blockReactive = false;
            coolant = false;
            incinerable = false;
            boilPoint = -1f;
            hidden = false;
            alwaysUnlocked = true;
        }};

        dimension_fluid = new CellLiquid("dimension-fluid",new Color(0xFFD188ff)){{
            effect = md_StatusEffects.dimension_slip;
            heatCapacity = 0.4f;
            boilPoint = 2f;
            viscosity = 0.85f;
            temperature = 0.1f;
            heatCapacity = 2.718f;
            alwaysUnlocked = true;
            vaporEffect = md_Fx.dimension_vapor;
            particleEffect = md_Fx.dimension_vapor_small;
            colorFrom = Color.valueOf("FFB578");
            colorTo = Color.valueOf("FFE491");
            cells = 7;


        }};
    }
}
