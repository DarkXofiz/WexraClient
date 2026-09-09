package dev.wexra.events.impl.player;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.item.Item;
import dev.wexra.events.Event;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventCooldown extends Event {
    public Item itemStack;
    public float cooldown;

    public EventCooldown(Item item) {
        this.itemStack = item;
    }
}
