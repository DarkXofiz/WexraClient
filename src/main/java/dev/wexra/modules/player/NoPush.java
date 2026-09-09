package dev.wexra.modules.player;

import dev.wexra.modules.setting.MultiSetting;
import dev.wexra.events.Event;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;
import java.util.Arrays;

@FunctionAnnotation(name = "NoPush" ,desc  = "Убивает коллизию от разных типов", type = Type.Player)
public class NoPush extends Function {
    public MultiSetting mods = new MultiSetting(
            "Типы",
            Arrays.asList("Игроки", "Блоки"),
            new String[]{"Вода", "Игроки", "Блоки"}
    );
    public NoPush() {
        addSettings(mods);
    }

    @Override
    public void onEvent(Event event) {
    }
}
