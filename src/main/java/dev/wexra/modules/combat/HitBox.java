package dev.wexra.modules.combat;

import dev.wexra.modules.setting.SliderSetting;
import dev.wexra.events.Event;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "HitBox", type = Type.Combat, desc = "Позволяет увеличивать хит-бокс игроков")
public class HitBox extends Function {

    public SliderSetting size = new SliderSetting("Размер", 0.4f, 0.1f, 5.5f, 0.1f);

    public HitBox() {
        addSettings(size);
    }
    @Override
    public void onEvent(Event event) {
    }

}