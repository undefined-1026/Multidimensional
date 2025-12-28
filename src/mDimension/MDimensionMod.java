package mDimension;
import arc.Core;
import arc.Events;
import arc.scene.event.ClickListener;
import arc.util.Time;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.BaseDialog;

import java.util.TimerTask;

public class MDimensionMod extends Mod {
    public MDimensionMod(){
        Events.on(ClickListener.class,e->{
            Time.runTask(10f,()->{
                BaseDialog welcome = new BaseDialog("Welcome to play 'Multidimensional'");
                welcome.cont.add("A new journey\nLet's begin");
                welcome.cont.image(Core.atlas.find("MDimension-java-mod-welcome1")).pad(30f).row();
                Time.runTask(200f, welcome::addCloseButton);
                welcome.show();
            });
        });
    }
    @Override
    public void loadContent(){

    }
}