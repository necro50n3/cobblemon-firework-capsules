package com.necro.fireworkcapsules.fabric.particles;

import com.necro.fireworkcapsules.common.particles.FireworkCapsuleParticles;
import com.necro.fireworkcapsules.common.particles.custom.FlashParticle;
import com.necro.fireworkcapsules.common.particles.custom.StickerParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class FabricParticlesClient {
    public static void registerClient() {
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.BUG_STICKER, StickerParticle.VariedStickerProvider::new);
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.DARK_STICKER, StickerParticle.StickerProvider::new);
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.DRAGON_STICKER, (sprite) -> new StickerParticle.ColoredStickerProvider(sprite, 9327353));
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.ELECTRIC_STICKER, FlashParticle.FlashProvider::new);
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.FAIRY_STICKER, StickerParticle.StickerProvider::new);
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.FIGHTING_STICKER, FlashParticle.VariedFlashProvider::new);
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.FIRE_STICKER, StickerParticle.StickerProvider::new);
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.FLYING_STICKER, (sprite) -> new StickerParticle.ColoredStickerProvider(sprite, 12972793));
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.GHOST_STICKER, StickerParticle.StickerProvider::new);
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.GRASS_STICKER, (sprite) -> new StickerParticle.VariedColoredStickerProvider(sprite, 7194922));
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.GROUND_STICKER, (sprite) -> new StickerParticle.ColoredStickerProvider(sprite, 14139023));
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.ICE_STICKER, StickerParticle.VariedStickerProvider::new);
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.NORMAL_STICKER, StickerParticle.StickerProvider::new);
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.POISON_STICKER, StickerParticle.StickerProvider::new);
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.PSYCHIC_STICKER, (sprite) -> new StickerParticle.ColoredStickerProvider(sprite, 14637291));
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.STEEL_STICKER, StickerParticle.StickerProvider::new);
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.ROCK_STICKER, StickerParticle.VariedStickerProvider::new);
        ParticleFactoryRegistry.getInstance().register(FireworkCapsuleParticles.WATER_STICKER, StickerParticle.StickerProvider::new);
    }
}
