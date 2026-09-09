package dev.wexra.modules.combat;


import dev.wexra.events.Event;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "NoFriendDamage", keywords = {"NFD","FriendDamage"}, type = Type.Combat, desc = "Отключает урон по друзьям")
public class NoFriendDamage extends Function {
    public NoFriendDamage() {
        addSettings();
    }
    @Override
    public void onEvent(Event event) {
    }
}
