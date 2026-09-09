package dev.wexra.modules.player;

import dev.wexra.events.Event;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;
import dev.wexra.modules.setting.ModeSetting;

@FunctionAnnotation(
        name = "ClickAction",
        keywords = "SwapAction",
        desc = "Ставит свапы под выбранный тип сервера (чтобы не забанило)",
        type = Type.Player
)
public class ClickAction extends Function {

    public final ModeSetting type =
            new ModeSetting("Тип", "ReallyWorld", "ReallyWorld", "FunTime", "HollyWorld");

    @Override
    public void onEvent(Event event) {
    }

    public final boolean nonBatch() {
        return type.is("ReallyWorld");
    }

    public final boolean batch() {
        return type.is("FunTime") || type.is("HollyWorld");
    }
}
