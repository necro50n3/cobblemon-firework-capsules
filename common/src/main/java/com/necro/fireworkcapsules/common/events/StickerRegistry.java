package com.necro.fireworkcapsules.common.events;

import com.necro.fireworkcapsules.common.particles.CapsuleParticle;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.util.CustomParticleFunction;

public class StickerRegistry {
    public static void register(StickerExplosion explosion, CustomParticleFunction particle) {
        CapsuleParticle.register(explosion.id(), particle);
    }
}
