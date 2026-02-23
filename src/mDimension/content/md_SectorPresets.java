package mDimension.content;

import mindustry.content.SectorPresets;
import mindustry.type.Sector;
import mindustry.type.SectorPreset;

public class md_SectorPresets {
    public static SectorPreset starting_point;
    public static void load(){
        starting_point = new SectorPreset("starting-point",md_Planets.depicilon,0){{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 10;
            difficulty = 1;
            overrideLaunchDefaults = true;
            noLighting = true;
            startWaveTimeMultiplier = 3f;
        }};
    }
}
