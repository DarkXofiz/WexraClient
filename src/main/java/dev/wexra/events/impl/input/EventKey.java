package dev.wexra.events.impl.input;


import dev.wexra.events.Event;

public class EventKey extends Event {
    public int key;

    public EventKey(int key) {
        this.key = key;
    }
}
