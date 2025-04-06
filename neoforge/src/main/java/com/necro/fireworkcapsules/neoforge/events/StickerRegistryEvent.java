package com.necro.fireworkcapsules.neoforge.events;

import com.necro.fireworkcapsules.common.events.StickerRegistry;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.util.CustomParticleFunction;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

public class StickerRegistryEvent extends Event implements IModBusEvent {
    public void register(StickerExplosion explosion, CustomParticleFunction particle) {
        StickerRegistry.register(explosion, particle);
    }
}
