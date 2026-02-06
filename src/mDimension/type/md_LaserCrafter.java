package mDimension.type;

import arc.Core;
import arc.func.Floatp;
import arc.graphics.Color;
import mDimension.tool.Str;
import mDimension.tool.Vector2D;
import mDimension.tool.continuousRGB;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.HeatCrafter;

import java.util.ArrayList;

public class md_LaserCrafter extends GenericCrafter{
    public float powerRequirement = 10f;

    public float powerBaseEff = 0f;
    public float polarizationBaseEff = 0f;

    public float maxWavelength = -1;
    public float minWavelength = -1;

    public boolean isNeedPolarization = false;
    public Vector2D needPolarization = new Vector2D(0,0);

    public float maxEfficiency = 8f;
    public md_LaserCrafter(String name){
        super(name);
        category = Category.crafting;
    }
    @Override
    public void setBars(){
        super.setBars();//Core.bundle.format("bar.efficiency", (int)(entity.amass + 0.01f), (int)(entity.efficiencyScale() * 100 + 0.01f)

        addBar("bunch power", (md_LaserCrafter.md_LaserCrafterBuild entity) ->
                new Bar(String.format("power %s|%s|%d%s", Str.goStr(entity.amass,1),Str.goStr(powerRequirement,1),(int)(entity.efficiencyScale()*100),"%"),
                        new Color(0.8f,0.85f,1f),
                        ()->(entity.amass/powerRequirement)));
        addBar("wavelength", (md_LaserCrafter.md_LaserCrafterBuild entity) ->

                    new Bar(String.format("WL|%s %s",
                            entity.getMaxWavelength()<0?"":"max"+Str.goStr(entity.getMaxWavelength(),0),
                            entity.getMinWavelength()<0?"":"min"+Str.goStr(entity.getMinWavelength(),0)
                    ),
                        continuousRGB.contWhiteRGB(entity.getMaxWavelength()/1000,0.4f),
                        ()->(entity.Filter(entity.getMaxWavelength())?1f:0f)));
    }

    public class md_LaserCrafterBuild extends GenericCrafterBuild{
        public float amass = 0f;
        public ArrayList<md_LaserData> CacheLasers = new ArrayList<>();


        @Override
        public void updateTile(){

            super.updateTile();
            CacheLasers.clear();
        }
        public float getMaxWavelength(){
            float CmaxWavelength = -1f;
            if(!CacheLasers.isEmpty()) {
                for (md_LaserData l : CacheLasers) {
                    if(l!=null) {
                        CmaxWavelength = CmaxWavelength < 0 ? l.waveLength : CmaxWavelength;
                        CmaxWavelength = Math.max(CmaxWavelength, l.waveLength);
                    }
                }
            }
            return  CmaxWavelength;
        }
        public float getMinWavelength(){
            float CminWavelength = -1f;
            if(!CacheLasers.isEmpty()) {
                for (md_LaserData l : CacheLasers) {
                    if(l!=null) {
                        CminWavelength = CminWavelength < 0 ? l.waveLength : CminWavelength;
                        CminWavelength = Math.min(CminWavelength, l.waveLength);
                    }
                }
            }
            return  CminWavelength;
        }
        @Override
        public float efficiencyScale(){
            amass  = 0f;
            if(!CacheLasers.isEmpty()) {
                for (md_LaserData l : CacheLasers) {

                    if (Filter(l.waveLength)) {
                        amass += l.laserPower;
                    }
                }
            }
            return Math.min(maxEfficiency,(amass/powerRequirement)+powerBaseEff);
        }

        public boolean Filter(float wavelength){
            return (wavelength > minWavelength || minWavelength < 0)
                    &&
                    (wavelength < maxWavelength || maxWavelength < 0);
        }

    }
}
