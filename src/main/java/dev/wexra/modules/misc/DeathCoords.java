package dev.wexra.modules.misc;


import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.util.Formatting;
import dev.wexra.events.Event;
import dev.wexra.events.impl.EventUpdate;
import dev.wexra.manager.ClientManager;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "DeathCoords", type = Type.Misc,desc = "Отправляет координаты при смерти")
public class DeathCoords extends Function {
    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (isPlayerDead()) {
                int positionX = (int) mc.player.getX();
                int positionY = (int) mc.player.getY();
                int positionZ = (int) mc.player.getZ();

                if (mc.player.deathTime < 1) {
                    String message = "Координаты: " + Formatting.GRAY + "X: " + positionX + " Y: " + positionY + " Z: " + positionZ + Formatting.RESET;
                    ClientManager.message(message);
                }
            }
        }
    }

    private boolean isPlayerDead() {
        return mc.player.getHealth() < 1.0f && mc.currentScreen instanceof DeathScreen;
    }
}
