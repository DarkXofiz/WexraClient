package dev.wexra.modules.player;

import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import dev.wexra.events.Event;
import dev.wexra.events.impl.input.EventKey;
import dev.wexra.events.impl.input.EventMouse;
import dev.wexra.manager.Manager;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;
import dev.wexra.modules.setting.BindSetting;
import dev.wexra.modules.setting.BooleanSetting;
import dev.wexra.modules.setting.ModeSetting;
import dev.wexra.util.player.InventoryUtil;

@FunctionAnnotation(name = "MiddleClickPearl",keywords = {"MCP"}, desc = "Откидывание пёрла по колёсику мыши", type = Type.Player)
public class MiddleClickPearl extends Function {
    private final ModeSetting mode = new ModeSetting("Тип","Обычный","Обычный","По бинду");
    private final BindSetting bind = new BindSetting("Кнопка кидания",0,() -> mode.is("По бинду"));
    private final BooleanSetting inventoryUse = new BooleanSetting("Использовать из инвентаря",true,"Не используйте на HollyWorld (баниться)");

    public MiddleClickPearl() {
        addSettings(mode,bind,inventoryUse);
    }

    @Override
    public void onEvent(Event event) {
        if (mode.is("Обычный")) {
            if (event instanceof EventMouse mouseTick) {
                if (mouseTick.getButton() == 2) {
                    handleMouseTickEvent();
                }
            }
        }
        if (mode.is("По бинду")) {
            if (event instanceof EventKey e) {
                if (e.key == bind.getKey()) {
                    handleMouseTickEvent();
                }
            }
        }
    }

    private void handleMouseTickEvent() {
        if (!mc.player.getItemCooldownManager().isCoolingDown(Items.ENDER_PEARL.getDefaultStack())) {
            if (Manager.FUNCTION_MANAGER.attackAura.target != null) {
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround(), false));
            }
            InventoryUtil.inventorySwapClick2(Items.ENDER_PEARL, inventoryUse.get(), true);
        }
    }
}
