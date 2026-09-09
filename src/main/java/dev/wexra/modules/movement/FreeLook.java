package dev.wexra.modules.movement;

import net.minecraft.client.option.Perspective;
import dev.wexra.modules.setting.BindSetting;
import dev.wexra.events.Event;
import dev.wexra.events.impl.input.EventKey;
import dev.wexra.manager.Manager;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;
import dev.wexra.modules.movement.freelook.FreeLookState;
import dev.wexra.manager.ClientManager;

@FunctionAnnotation(name = "FreeLook", desc = "Позволит вращать камеру, при этом не меняя направления движения", type = Type.Move)
public class FreeLook extends Function {
    private final BindSetting bind = new BindSetting("Кнопка", 0);
    private Perspective previousPerspective;

    public FreeLook() {
        addSettings(bind);
    }

    @Override
    public void onEvent(Event event) {
        if (!(event instanceof EventKey keyEvent)) return;
        if (keyEvent.key != bind.getKey()) return;
        if (mc == null || mc.options == null) return;
        var attackAura = Manager.FUNCTION_MANAGER.attackAura;
        if (attackAura != null && attackAura.state && attackAura.target != null) {
            ClientManager.message("Нельзя использовать с " + attackAura.name);
            return;
        }

        FreeLookState.active = !FreeLookState.active;

        if (FreeLookState.active) {
            previousPerspective = mc.options.getPerspective();
            if (previousPerspective != Perspective.THIRD_PERSON_FRONT) {
                mc.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
            }
        } else {
            mc.options.setPerspective(previousPerspective != null ? previousPerspective : Perspective.FIRST_PERSON);
        }
    }
    @Override
    public void onDisable() {
        FreeLookState.active = false;
        if (mc != null && mc.options != null) {
            mc.options.setPerspective(previousPerspective != null ? previousPerspective : Perspective.FIRST_PERSON);
        }
        previousPerspective = null;
    }

}