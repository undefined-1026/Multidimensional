package mDimension.content;

import arc.graphics.Color;
import mDimension.type.Beam;

public class md_beams {
    public static Beam
            near_infrared_ligth,ultraviolet_ligth;
    public static void load() {
        near_infrared_ligth = new Beam("near-infrared-laser", Color.valueOf("F9BDA3")) {{
            wavelengthLeve = 3;
            lenght = 18;
            laserDrawer = l -> {
                Beam.basicDraw(l, Color.valueOf("ff9863"), 2.5f, 10f, 31f);
                Beam.basicDraw(l, Color.valueOf("F9F2EF"), 0.6f, 10f, 101);
                Beam.DrawPacket(l,Color.valueOf("F9F2EF"), 1.2f,1f,101.1f,80f);
            };
        }};
        ultraviolet_ligth = new Beam("ultraviolet-ligth", Color.valueOf("EA61F9")) {{
            wavelengthLeve = 5;
            lenght = 12;
            laserDrawer = l -> {
                Beam.basicDraw(l, Color.valueOf("EA61F9"), 2.2f, 10f, 31f);
                Beam.basicDraw(l, Color.valueOf("F8F1F9"), 0.5f, 10f, 101);
                Beam.DrawPacket(l,Color.valueOf("F8F1F9"), 1f,1f,101.1f,60f);
            };
        }};
    }

}
