package dev.wexra.modules.player;

import dev.wexra.events.Event;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "NoRayTrace",keywords = {"NoEntityTrace"}, desc = "Убирает хитбокс энтити", type = Type.Player)
public class NoRayTrace extends Function {

    @Override
    public void onEvent(Event event) {
    }
}