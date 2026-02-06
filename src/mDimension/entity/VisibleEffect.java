package mDimension.entity;

import arc.func.Cons;
import mindustry.content.Fx;
import mindustry.entities.Effect;

import static mindustry.Vars.headless;

public class VisibleEffect extends Effect {
    @Override
    public boolean shouldCreate(){
        return !headless && this != Fx.none;
    }

    public VisibleEffect(float life, Cons<EffectContainer> renderer){
        super(life,renderer);
    }
    public VisibleEffect(float life,float cilpsize, Cons<EffectContainer> renderer){
        super(life,cilpsize,renderer);
    }
    public VisibleEffect(){
        super();
    }

}
