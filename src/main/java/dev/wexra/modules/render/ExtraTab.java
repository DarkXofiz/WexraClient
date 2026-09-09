package dev.wexra.modules.render;

import dev.wexra.events.Event;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "ExtraTab",desc  = "Количество игроков табе больше", type = Type.Render)
public class ExtraTab extends Function {

    @Override
    public void onEvent(Event event) {

    }
}