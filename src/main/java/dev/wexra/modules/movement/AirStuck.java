package dev.wexra.modules.movement;

import net.minecraft.util.math.Vec3d;
import dev.wexra.events.Event;
import dev.wexra.events.impl.move.EventMotion;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;
import dev.wexra.modules.setting.BooleanSetting;

@FunctionAnnotation(name = "AirStuck", desc = "Позволяет зависать в воздухе", type = Type.Move)
public class AirStuck extends Function {
    private BooleanSetting packet = new BooleanSetting("Отменять движение",true,"Отменяет пакет на движения для сервер");
    private Vec3d freezePosition = Vec3d.ZERO;

    public AirStuck() {
        addSettings(packet);
    }

    @Override
    public void onEnable() {
        if (mc.player != null) {
            freezePosition = mc.player.getPos();
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventMotion eventMotion) {
            if (mc.player != null && freezePosition != Vec3d.ZERO) {
                if (packet.get()) {
                    eventMotion.setCancel(true);
                }
                mc.player.setPosition(freezePosition);
                mc.player.setVelocity(Vec3d.ZERO);
            }
        }
    }
}