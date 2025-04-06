package com.necro.fireworkcapsules.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface StickerRegistryEvent {
    Event<StickerRegistryEvent> EVENT = EventFactory.createArrayBacked(StickerRegistryEvent.class,
        (listeners) -> (event) -> {
            for (StickerRegistryEvent listener : listeners) {
                listener.register(event);
            }
        }
);

    void register(StickerEvent event);
}
