package dev.wexra.modules.player;

import dev.wexra.modules.setting.BooleanSetting;
import dev.wexra.events.Event;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "NoInteract", desc = "Не даст вам открыть контейнер по нажатию на ПКМ", type = Type.Player)
public class NoInteract extends Function {
    public final BooleanSetting onlyAura = new BooleanSetting("Только с AttackAura",false);

    public NoInteract() {
        addSettings(onlyAura);
    }
    @Override
    public void onEvent(Event event) {

    }
}