package dev.wexra.modules.misc;

import dev.wexra.events.Event;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "NoCommands", desc = "Отключение команд через точку", type = Type.Misc)
public class NoCommands extends Function {
    public NoCommands() {
    }

    @Override
    public void onEvent(Event event) {

    }
}