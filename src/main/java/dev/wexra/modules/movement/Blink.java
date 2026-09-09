package dev.wexra.modules.movement;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.util.math.Box;
import dev.wexra.events.Event;
import dev.wexra.events.impl.EventPacket;
import dev.wexra.events.impl.EventUpdate;
import dev.wexra.events.impl.render.EventRender3D;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;
import dev.wexra.modules.setting.SliderSetting;
import dev.wexra.util.move.NetworkUtils;
import dev.wexra.util.render.RenderUtil;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

@FunctionAnnotation(name = "Blink", desc = "Задерживает пакеты отправленные на сервер", type = Type.Move)
public class Blink extends Function {
    private final SliderSetting maxTicks = new SliderSetting("Макс. тики", 20f, 1f, 50f, 1f);
    private final CopyOnWriteArrayList<Packet<?>> packetBuffer = new CopyOnWriteArrayList<>();

    private Box playerBoundingBox;
    private int currentTick = 0;

    public Blink() {
        addSettings(maxTicks);
    }

    @Override
    public void onEvent(Event event) {
        if (mc.player == null || mc.world == null) {
            toggle();
            return;
        }

        if (event instanceof EventPacket packetEvent) {
            Packet<?> packet = packetEvent.getPacket();
            if (packetEvent.isSendPacket() && !(packet instanceof KeepAliveC2SPacket)) {
                packetBuffer.add(packet);
                packetEvent.setCancel(true);
            }
        }

        if (event instanceof EventUpdate) {
            currentTick++;
            if (currentTick >= maxTicks.get().intValue()) {
                send();
                currentTick = 0;
            }
        }

        if (event instanceof EventRender3D) {
            if (playerBoundingBox != null) {
                RenderUtil.render3D.drawHoleOutline(playerBoundingBox, Color.WHITE.getRGB(), 2f);
            }
        }
    }

    private void send() {
        if (mc.player == null || mc.world == null || packetBuffer.isEmpty()) return;
        for (Packet<?> packet : packetBuffer) {
            NetworkUtils.sendSilentPacket(packet);
        }
        packetBuffer.clear();
        playerBoundingBox = mc.player.getBoundingBox();
    }

    @Override
    public void onDisable() {
        send();
        playerBoundingBox = null;
        currentTick = 0;
    }
}
