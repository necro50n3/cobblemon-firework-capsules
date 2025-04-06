package com.necro.fireworkcapsules.neoforge.events;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.gui.CapsuleStationScreen;
import com.necro.fireworkcapsules.common.gui.FireworkCapsuleMenus;
import com.necro.fireworkcapsules.common.particles.FireworkCapsuleParticles;
import com.necro.fireworkcapsules.common.particles.custom.FlashParticle;
import com.necro.fireworkcapsules.common.particles.custom.StickerParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

public class ModEvents {
    @EventBusSubscriber(value = Dist.CLIENT, modid = FireworkCapsules.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientEvents {
        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(FireworkCapsuleParticles.BUG_STICKER, StickerParticle.VariedStickerProvider::new);
            event.registerSpriteSet(FireworkCapsuleParticles.DARK_STICKER, StickerParticle.StickerProvider::new);
            event.registerSpriteSet(FireworkCapsuleParticles.DRAGON_STICKER, (sprite) -> new StickerParticle.ColoredStickerProvider(sprite, 9327353));
            event.registerSpriteSet(FireworkCapsuleParticles.ELECTRIC_STICKER, FlashParticle.FlashProvider::new);
            event.registerSpriteSet(FireworkCapsuleParticles.FAIRY_STICKER, StickerParticle.StickerProvider::new);
            event.registerSpriteSet(FireworkCapsuleParticles.FIGHTING_STICKER, FlashParticle.VariedFlashProvider::new);
            event.registerSpriteSet(FireworkCapsuleParticles.FIRE_STICKER, StickerParticle.StickerProvider::new);
            event.registerSpriteSet(FireworkCapsuleParticles.FLYING_STICKER, (sprite) -> new StickerParticle.ColoredStickerProvider(sprite, 12972793));
            event.registerSpriteSet(FireworkCapsuleParticles.GHOST_STICKER, StickerParticle.StickerProvider::new);
            event.registerSpriteSet(FireworkCapsuleParticles.GRASS_STICKER, (sprite) -> new StickerParticle.VariedColoredStickerProvider(sprite, 7194922));
            event.registerSpriteSet(FireworkCapsuleParticles.GROUND_STICKER, (sprite) -> new StickerParticle.ColoredStickerProvider(sprite, 14139023));
            event.registerSpriteSet(FireworkCapsuleParticles.ICE_STICKER, StickerParticle.VariedStickerProvider::new);
            event.registerSpriteSet(FireworkCapsuleParticles.NORMAL_STICKER, StickerParticle.StickerProvider::new);
            event.registerSpriteSet(FireworkCapsuleParticles.POISON_STICKER, StickerParticle.StickerProvider::new);
            event.registerSpriteSet(FireworkCapsuleParticles.PSYCHIC_STICKER, (sprite) -> new StickerParticle.ColoredStickerProvider(sprite, 14637291));
            event.registerSpriteSet(FireworkCapsuleParticles.STEEL_STICKER, StickerParticle.StickerProvider::new);
            event.registerSpriteSet(FireworkCapsuleParticles.ROCK_STICKER, StickerParticle.VariedStickerProvider::new);
            event.registerSpriteSet(FireworkCapsuleParticles.WATER_STICKER, StickerParticle.StickerProvider::new);
        }
    }

    @EventBusSubscriber(modid = FireworkCapsules.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class CommonEvents {
        @SubscribeEvent
        public static void registerParticles(RegisterEvent event) {
            registerParticle("capsule_particle", FireworkCapsuleParticles.CAPSULE_PARTICLE, event);

            registerParticle("bug_sticker", FireworkCapsuleParticles.BUG_STICKER, event);
            registerParticle("dark_sticker", FireworkCapsuleParticles.DARK_STICKER, event);
            registerParticle("dragon_sticker", FireworkCapsuleParticles.DRAGON_STICKER, event);
            registerParticle("electric_sticker", FireworkCapsuleParticles.ELECTRIC_STICKER, event);
            registerParticle("fairy_sticker", FireworkCapsuleParticles.FAIRY_STICKER, event);
            registerParticle("fighting_sticker", FireworkCapsuleParticles.FIGHTING_STICKER, event);
            registerParticle("fire_sticker", FireworkCapsuleParticles.FIRE_STICKER, event);
            registerParticle("flying_sticker", FireworkCapsuleParticles.FLYING_STICKER, event);
            registerParticle("ghost_sticker", FireworkCapsuleParticles.GHOST_STICKER, event);
            registerParticle("grass_sticker", FireworkCapsuleParticles.GRASS_STICKER, event);
            registerParticle("ground_sticker", FireworkCapsuleParticles.GROUND_STICKER, event);
            registerParticle("ice_sticker", FireworkCapsuleParticles.ICE_STICKER, event);
            registerParticle("normal_sticker", FireworkCapsuleParticles.NORMAL_STICKER, event);
            registerParticle("poison_sticker", FireworkCapsuleParticles.POISON_STICKER, event);
            registerParticle("psychic_sticker", FireworkCapsuleParticles.PSYCHIC_STICKER, event);
            registerParticle("steel_sticker", FireworkCapsuleParticles.STEEL_STICKER, event);
            registerParticle("rock_sticker", FireworkCapsuleParticles.ROCK_STICKER, event);
            registerParticle("water_sticker", FireworkCapsuleParticles.WATER_STICKER, event);
        }

        private static void registerParticle(String name, ParticleType<?> particle, RegisterEvent event) {
            event.register(Registries.PARTICLE_TYPE, helper ->
                helper.register(ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, name), particle)
            );
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(FireworkCapsuleMenus.CAPSULE_STATION_MENU.value(), CapsuleStationScreen::new);
        }
    }
}
