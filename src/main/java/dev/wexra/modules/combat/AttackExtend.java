package dev.wexra.modules.combat;

import dev.wexra.events.Event;
import dev.wexra.events.impl.input.EventKeyBoard;
import dev.wexra.events.impl.player.EventAttack;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;
import dev.wexra.modules.setting.BooleanSetting;
import dev.wexra.util.move.MoveUtil;

@FunctionAnnotation(name = "WTap", type = Type.Combat, keywords = {"ExtendedAttack","ExtendedKnockBack"}, desc = "Позволяет оттолкнуть противника дальше")
public class AttackExtend extends Function {
    private final BooleanSetting onlyOnGround = new BooleanSetting("Только на земле", true);

    public AttackExtend() {
        addSettings(onlyOnGround);
    }

    private int sprintResetTicks;

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventKeyBoard e && sprintResetTicks > 0 && MoveUtil.isMoving()) {
            e.setMovementForward(0);
            sprintResetTicks--;
        }

        if (event instanceof EventAttack && (!onlyOnGround.get() || mc.player.isOnGround()) && !mc.player.isInFluid() && mc.player.isSprinting()) {
            sprintResetTicks = 1;
        }
    }
}
