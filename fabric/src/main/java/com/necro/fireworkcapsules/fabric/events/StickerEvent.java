package com.necro.fireworkcapsules.fabric.events;

import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.util.CustomParticleFunction;

public interface StickerEvent {
    void register(StickerExplosion explosion, CustomParticleFunction particle);
}
