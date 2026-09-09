package dev.wexra.modules.movement;

import dev.wexra.modules.setting.BooleanSetting;
import dev.wexra.modules.setting.SliderSetting;
import dev.wexra.events.Event;
import dev.wexra.events.impl.EventUpdate;
import dev.wexra.manager.ClientManager;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "Timer", type = Type.Move, desc = "Ускорение игры")
public class Timer extends Function {

    private final SliderSetting timerAmount = new SliderSetting("Скорость", 2, 0f, 10, 0.01f);
    private final BooleanSetting smart = new BooleanSetting("Умный", true);
    private final SliderSetting ticks = new SliderSetting("Скорость убывания", 3.8f, 0.15f, 5.0f, 0.1f,() -> smart.get());

    private float maxViolation = 100.0F;
    private float violation = 0.0F;
    private boolean isCooldown = false;

    public Timer() {
        addSettings(timerAmount, smart, ticks);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (!smart.get()) {
                ClientManager.TICK_TIMER = timerAmount.get().floatValue();
                return;
            }

            if (isCooldown) {
                violation -= ticks.get().floatValue();
                ClientManager.TICK_TIMER = 1.0F;

                if (violation <= 0) {
                    violation = 0;
                    isCooldown = false;
                }
            } else {
                ClientManager.TICK_TIMER = timerAmount.get().floatValue();
                violation += ticks.get().floatValue();

                if (violation >= maxViolation) {
                    violation = maxViolation;
                    isCooldown = true;
                }
            }
        }
    }

    @Override
    public void onDisable() {
        ClientManager.TICK_TIMER = 1.0F;
        violation = 0.0F;
        isCooldown = false;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        violation = 0.0F;
        isCooldown = false;
        super.onEnable();
    }
}