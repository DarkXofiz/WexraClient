package dev.wexra.modules.player;

import dev.wexra.modules.setting.SliderSetting;
import dev.wexra.events.Event;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "ItemScroll", desc = "Быстрое перемещение", type = Type.Player)
public class ItemScroller extends Function {
    public SliderSetting scroll = new SliderSetting("Задержка", 100f, 1f, 100f,1f);

    public ItemScroller() {
        addSettings(scroll);
    }

    @Override
    public void onEvent(Event event) {

    }
}