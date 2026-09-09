package dev.wexra.modules.movement;

import dev.wexra.events.Event;
import dev.wexra.events.impl.EventUpdate;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "AutoSprint" ,desc  = "Автоматически включает бег", type = Type.Move)
public class AutoSprint extends Function {
    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            mc.options.sprintKey.setPressed(true);
        }
    }

    @Override
    protected void onDisable() {
        mc.options.sprintKey.setPressed(false);
        super.onDisable();
    }
}