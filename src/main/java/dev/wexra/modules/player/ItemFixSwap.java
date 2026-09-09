package dev.wexra.modules.player;

import net.minecraft.network.packet.c2s.play.SlotChangedStateC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import dev.wexra.events.Event;
import dev.wexra.events.impl.EventPacket;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(
        name = "ItemFixSwap",
        keywords = {"NoSlotChange", "NoServerDesync", "СлотФиксер"},
        desc = "Убирает переключение слота от античита",
        type = Type.Player
)
public class ItemFixSwap extends Function {
    public ItemFixSwap() {
        addSettings();
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventPacket e) {
            if (e.isReceivePacket()) {
               if (e.getPacket() instanceof UpdateSelectedSlotC2SPacket packetHeldItemChange) {
                   event.setCancel(true);
               }
            }
        }
    }
}
