package dev.wexra.modules.movement;

import dev.wexra.modules.combat.TargetStrafe;
import dev.wexra.modules.setting.ModeSetting;
import dev.wexra.events.Event;
import dev.wexra.events.impl.move.EventMotion;
import dev.wexra.manager.ClientManager;
import dev.wexra.manager.Manager;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;
import dev.wexra.modules.setting.SliderSetting;
import dev.wexra.util.move.MoveUtil;
import dev.wexra.util.player.TimerUtil;

@FunctionAnnotation(name = "Speed", desc = "Поможет умереть быстрее", type = Type.Move)
public class Speed extends Function {

    private final ModeSetting mode = new ModeSetting("Режим", "Vanilla", "Vanilla");

    private final SliderSetting speed = new SliderSetting("Скорость",1f,0.1f,3f,0.1f);
    private final TimerUtil timerUtil = new TimerUtil();

    public Speed() {
        addSettings(mode,speed);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventMotion eventMotion) {
            if (mc.player == null || mc.world == null) return;

            switch (mode.get()) {
                case "Vanilla" -> vanilla();
            }
        }
    }

    private void vanilla() {
        if (MoveUtil.isMoving() && !mc.player.isGliding()) {
            MoveUtil.setSpeed(speed.get().floatValue());
        }
    }


    @Override
    protected void onEnable() {
        TargetStrafe targetStrafe = Manager.FUNCTION_MANAGER.targetStrafe;
        if (targetStrafe.state) {
            targetStrafe.setState(false);
        }
        timerUtil.reset();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        ClientManager.TICK_TIMER = 1.0f;
        super.onDisable();
    }
}
