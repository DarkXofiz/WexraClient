package dev.wexra.modules.misc;
import dev.wexra.modules.setting.ModeSetting;
import dev.wexra.modules.setting.MultiSetting;
import dev.wexra.modules.setting.SliderSetting;
import dev.wexra.events.Event;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

import java.util.Arrays;

@FunctionAnnotation(name = "ClientSounds", desc = "Звуки", type = Type.Misc)
public class ClientSounds extends Function {
    public final MultiSetting check = new MultiSetting(
            "Выбрать",
            Arrays.asList("Вход в клиент"),
            new String[]{"Вход в клиент"}
    );
    public final ModeSetting mode = new ModeSetting("Мод", "Type-1", "Type-1", "Type-2", "Type-3","Type-4");
    public final SliderSetting volume = new SliderSetting("Громкость", 100f, 1f, 100f,1f);


    public ClientSounds() {
        addSettings(check,mode,volume);
    }

    @Override
    public void onEvent(Event event) {
    }
}
