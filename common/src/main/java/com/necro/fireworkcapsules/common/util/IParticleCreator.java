package com.necro.fireworkcapsules.common.util;

import com.necro.fireworkcapsules.common.stickers.StickerExplosion;

import java.util.List;

public interface IParticleCreator {
    default void fc_createCapsuleParticles(double x, double y, double z, int entityId, List<StickerExplosion> stickers, float scale) {}
}
