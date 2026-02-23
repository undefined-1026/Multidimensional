package mDimension.type;

import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mDimension.meta.md_Stat;
import mDimension.meta.md_StatUnit;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.ctype.ContentType;
import mindustry.entities.units.StatusEntry;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.content;

public class md_complexStatusEffect extends StatusEffect {

    public float percentageDamage = 0;
    public float percentageShieldDamage = 0;
    public float armorAdditional = 0;
    public float armorMultiplier = 1;
    public Acts act =e -> {};
    public Draws draw =e -> {};



    public md_complexStatusEffect(String name){
        super(name);
    }
    public interface Acts{
        void acting(Unit u);
    }
    public interface Draws{
        void drawing(Unit u);
    }


    @Override
    public void setStats(){

        super.setStats();
        if(!reactive) {
            if (percentageDamage > 0) {
                stats.add(md_Stat.percentageDamage, percentageDamage * 6000f, md_StatUnit.percentsecond);
            } else if (percentageDamage < 0) {
                stats.add(md_Stat.percentageReply, -1 * percentageDamage * 6000f, md_StatUnit.percentsecond);
            }
            if(percentageShieldDamage !=0) {
                stats.add(md_Stat.percentageShieldDamage, percentageShieldDamage * 6000f, md_StatUnit.percentsecond);
            }
            if(armorAdditional !=0) {
                stats.add(md_Stat.armorAdditional, armorAdditional);
            }
            if(armorMultiplier !=1) {
                stats.add(md_Stat.armorMultiplier, armorMultiplier, StatUnit.multiplier);
            }
        }else {
            if (percentageDamage > 0) {
                stats.add(md_Stat.percentageDamage, percentageDamage*100f,StatUnit.percent);
            } else if (percentageDamage < 0) {
                stats.add(md_Stat.percentageReply, -1 * percentageDamage*100f,StatUnit.percent);
            }
            if(percentageShieldDamage !=0) {
                stats.add(md_Stat.percentageShieldDamage, percentageShieldDamage*100f, StatUnit.percent);
            }
        }

    }
    @Override
    public void update(Unit unit, StatusEntry entry){



        if(unit.dead) return;
        if(damage > 0){
            unit.damageContinuousPierce(damage);
        }else if(damage < 0){ //heal unit
            unit.heal(-1f * damage * Time.delta);
        }

        if(intervalDamageTime > 0){
            entry.damageTime += Time.delta;
            if(entry.damageTime >= intervalDamageTime){
                entry.damageTime %= intervalDamageTime;
                if(intervalDamagePierce){
                    unit.damagePierce(intervalDamage);
                }else{
                    unit.damage(intervalDamage);
                }
            }
        }
        /// percentageDamage
        if(percentageDamage > 0){
            unit.damageContinuousPierce(unit.maxHealth*percentageDamage);
        }else if(percentageDamage < 0){ //heal unit
            unit.heal(-1f * percentageDamage *unit.maxHealth* Time.delta);
        }
        if(percentageShieldDamage > 0){
            if(unit.shield > 1) {
                unit.damageContinuousPierce(unit.shield * percentageShieldDamage);
            }else unit.shield = 0;
        }
        /// armor
        float TmpArmorAdd = 0,TmpArmorMul = 1f;
        for(int i = 0;i<unit.statusBits().length();i++){
            if(unit.hasEffect(content.getByID(ContentType.status,i))) {
                if (content.getByID(ContentType.status, i) instanceof md_complexStatusEffect cStatus) {
                    if (cStatus.armorAdditional != 0 || cStatus.armorMultiplier != 1) {
                        TmpArmorAdd += cStatus.armorAdditional;
                        TmpArmorMul *= cStatus.armorMultiplier;
                    }
                }
            }
        }
        if(TmpArmorAdd!=0||TmpArmorMul!=1) {
            unit.armor = (unit.type.armor*TmpArmorMul)+TmpArmorAdd;
        }

        act.acting(unit);
        if(!Vars.headless && effect != Fx.none && Mathf.chanceDelta(effectChance) && !unit.inFogTo(Vars.player.team())){
            Tmp.v1.rnd(Mathf.range(unit.type.hitSize/2f));
            effect.at(unit.x + Tmp.v1.x, unit.y + Tmp.v1.y, 0, color, parentizeEffect ? unit : null);
        }

    }
    @Override
    public void onRemoved(Unit unit){
        unit.armor = unit.type.armor;
    }

    @Override
    public void draw(Unit u){
        draw.drawing(u);
    }
}
