package dev.wexra.modules.render;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import dev.wexra.events.Event;
import dev.wexra.events.impl.EventUpdate;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "FullBright",desc  = "Освещает местность", type = Type.Render)
public class FullBright extends Function {
    private final StatusEffectInstance nightVisionEffect = new StatusEffectInstance(
            StatusEffects.NIGHT_VISION,
            -1,
            255,
            false,
            false,
            true
    );
    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
          mc.player.addStatusEffect(nightVisionEffect,mc.player);
        }
    }

    @Override
    public void onDisable() {
        mc.player.removeStatusEffect(nightVisionEffect.getEffectType());
        super.onDisable();
    }
}