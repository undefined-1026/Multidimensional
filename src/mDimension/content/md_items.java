package mDimension.content;

import arc.graphics.Color;
import mindustry.type.Item;

public class md_items {
    public static Item
            bauxite,aluminium,al_alloy,ti_alloy,polymer,carbon_fibre;

    public static void load(){
        bauxite = new Item("bauxite",new Color(0xffebd0ff)){{
            hardness = 1;
            cost = 0.35f;
            alwaysUnlocked = true;
        }};
        aluminium = new Item("aluminium",new Color(0xffededff)){{
            healthScaling = 0.3f;
            hardness = 2;
            cost = 0.6f;
            alwaysUnlocked = true;
        }};
        al_alloy = new Item("al-alloy",new Color(0xededffff)){{
            healthScaling = 0.5f;
            hardness = 3;
            cost = 1f;
            alwaysUnlocked = true;
        }};
        ti_alloy = new Item("ti-alloy",new Color(0xd8d8ffff)){{
            healthScaling = 0.8f;
            hardness = 4;
            cost = 3f;
            alwaysUnlocked = true;
        }};
        polymer = new Item("polymer",new Color(0xFFE399ff)){{
            flammability = 0.6f;
            cost = -0.8f;
            hardness = 1;
            alwaysUnlocked = true;
        }};
        carbon_fibre = new Item("carbon-fibre",new Color(0x141414ff)){{
            flammability = 0.2f;
            cost = 1.5f;
            hardness = 1;
            alwaysUnlocked = true;
        }};
    }
}
