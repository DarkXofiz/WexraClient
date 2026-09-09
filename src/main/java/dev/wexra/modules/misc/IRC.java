package dev.wexra.modules.misc;

import dev.wexra.events.Event;
import dev.wexra.manager.Manager;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "IRC", desc = "Чат между юзерами других клиентов", type = Type.Misc)
public class IRC extends Function {

    @Override
    public void onEvent(Event event) {
    }


    @Override
    protected void onDisable() {
        Manager.IRC_MANAGER.shutdown();
        super.onDisable();
    }
}