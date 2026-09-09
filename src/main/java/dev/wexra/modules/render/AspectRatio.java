package dev.wexra.modules.render;


import dev.wexra.events.Event;
import dev.wexra.events.impl.EventUpdate;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;
import dev.wexra.modules.setting.ModeSetting;
import dev.wexra.modules.setting.SliderSetting;

@FunctionAnnotation(name = "AspectRatio" ,desc  = "Позволяет изменять соотношение сторон экрана", type = Type.Render)
public class AspectRatio extends Function {
    public final ModeSetting mods = new ModeSetting("Режим","16:9","4:3","16:9","1:1","16:10","Кастомный");
    public final SliderSetting slider = new SliderSetting("Соотношение", 1.8f, 0.1f, 5.0f,0.1f,() -> mods.is("Кастомный"));

    public AspectRatio() {
        addSettings(mods,slider);
    }

    @Override
    public void onEvent(Event event) {
    }

}
