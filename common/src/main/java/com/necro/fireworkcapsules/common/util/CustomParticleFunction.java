package com.necro.fireworkcapsules.common.util;

import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;

@FunctionalInterface
public interface CustomParticleFunction {
    void create(ClientLevel level, double x, double y, double z, int entityId, ParticleEngine engine, StickerExplosion sticker, float scale);
}
