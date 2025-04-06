package com.necro.fireworkcapsules.fabric.events;

import com.necro.fireworkcapsules.common.particles.FireworkCapsuleParticles;
import com.necro.fireworkcapsules.common.particles.custom.FlashParticle;
import com.necro.fireworkcapsules.common.particles.custom.StickerParticle;
import com.necro.fireworkcapsules.common.stickers.ElementalStickers;

public class RegisterStickersEvent {
    public static void register(StickerEvent event) {
        event.register(ElementalStickers.BUG_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.BUG_STICKER, 1.0));
        event.register(ElementalStickers.DARK_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.DARK_STICKER));
        event.register(ElementalStickers.DRAGON_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.DRAGON_STICKER, 1.0));
        event.register(ElementalStickers.ELECTRIC_STICKER, FlashParticle.getConsumer(FireworkCapsuleParticles.ELECTRIC_STICKER));
        event.register(ElementalStickers.FAIRY_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.FAIRY_STICKER, -0.05f));
        event.register(ElementalStickers.FIGHTING_STICKER, FlashParticle.getConsumer(FireworkCapsuleParticles.FIGHTING_STICKER));
        event.register(ElementalStickers.FIRE_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.FIRE_STICKER, -0.05f));
        event.register(ElementalStickers.FLYING_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.FLYING_STICKER));
        event.register(ElementalStickers.GHOST_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.GHOST_STICKER, -0.05f));
        event.register(ElementalStickers.GRASS_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.GRASS_STICKER, 1.0, 0.05f));
        event.register(ElementalStickers.GROUND_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.GROUND_STICKER, 0.2f));
        event.register(ElementalStickers.ICE_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.ICE_STICKER, 1.0, 0.05f));
        event.register(ElementalStickers.NORMAL_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.NORMAL_STICKER));
        event.register(ElementalStickers.POISON_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.POISON_STICKER));
        event.register(ElementalStickers.PSYCHIC_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.PSYCHIC_STICKER, 1.0));
        event.register(ElementalStickers.STEEL_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.STEEL_STICKER, 6.0));
        event.register(ElementalStickers.ROCK_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.ROCK_STICKER, 0.8f));
        event.register(ElementalStickers.WATER_STICKER, StickerParticle.getConsumer(FireworkCapsuleParticles.WATER_STICKER, -0.05f));
    }
}
