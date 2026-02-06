package mDimension;
import arc.Core;
import arc.Events;
import arc.util.Time;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.BaseDialog;

//物品导入
import mDimension.content.*;

public class MDimensionMod extends Mod {
    public MDimensionMod() {
        Events.on(EventType.ClientLoadEvent.class, e -> {
            Time.runTask(20f, () -> {
                BaseDialog welcome = new BaseDialog("Welcome to play Multidimensional");
                welcome.cont.add("A new journey Let's begin").row();
                welcome.cont.image(Core.atlas.find("mdimension-evil")).pad(10f);
                welcome.cont.image(Core.atlas.find("mdimension-neuro")).pad(20f);
                Time.runTask(200f, welcome::addCloseButton);
                welcome.show();
            });
        });


    }
    @Override
    public void loadContent(){
        super.loadContent();
        //载入
        md_status.load();
        md_items.load();
        md_liquids.load();
        md_blocks.load();
        original_reset.load();




    }
}