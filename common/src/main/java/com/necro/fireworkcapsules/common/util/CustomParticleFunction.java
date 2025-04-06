package com.necro.fireworkcapsules.common.util;

import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;

@FunctionalInterface
public interface CustomParticleFunction {
    void accept(ClientLevel clientLevel, double x, double y, double z, float rot, ParticleEngine particleEngine, StickerExplosion explosion, float scale, double scaleFactor);
}
