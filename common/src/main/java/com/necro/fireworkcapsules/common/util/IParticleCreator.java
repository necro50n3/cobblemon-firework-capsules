package com.necro.fireworkcapsules.common.util;

import com.necro.fireworkcapsules.common.stickers.StickerExplosion;

import java.util.List;

public interface IParticleCreator {
    default void createCapsuleParticles(double x, double y, double z, float rot, List<StickerExplosion> explosions, float scale) {}
}
