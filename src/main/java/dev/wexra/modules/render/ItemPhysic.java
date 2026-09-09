package dev.wexra.modules.render;

import dev.wexra.events.Event;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;
import dev.wexra.modules.setting.ModeSetting;

@FunctionAnnotation(name = "ItemPhysic",desc  = "Красиво лежат предметы на земле", type = Type.Render)
public class ItemPhysic extends Function {

    public final ModeSetting mode = new ModeSetting("Физика","Обычная","Обычная","2D");
    public ItemPhysic() {
        addSettings(mode);
    }

    @Override
    public void onEvent(Event event) {
    }
}