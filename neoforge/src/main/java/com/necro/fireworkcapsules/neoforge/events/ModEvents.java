package com.necro.fireworkcapsules.neoforge.events;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.client.StickerModel;
import com.necro.fireworkcapsules.common.gui.capsulestation.CapsuleStationScreen;
import com.necro.fireworkcapsules.common.gui.FireworkCapsuleMenus;
import com.necro.fireworkcapsules.common.gui.stickerbook.StickerBookScreen;
import com.necro.fireworkcapsules.common.particles.FireworkCapsuleParticles;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.tooltip.StickerBookTooltip;
import com.necro.fireworkcapsules.common.tooltip.StickerBookTooltipProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Set;

public class ModEvents {
    @EventBusSubscriber(value = Dist.CLIENT, modid = FireworkCapsules.MOD_ID)
    public static class ClientEvents {
        @SubscribeEvent
        public static void registerStickerBakedModel(ModelEvent.RegisterAdditional event) {
            Set<ResourceLocation> models = Minecraft.getInstance().getResourceManager()
                .listResources("models", model -> FireworkCapsules.MOD_ID.equals(model.getNamespace()) && model.getPath().contains("/stickers/") && model.getPath().endsWith(".json"))
                .keySet();

            models.forEach(model -> {
                String id = model.toString().replace("models/", "").replace(".json", "");
                event.register(ModelResourceLocation.standalone(ResourceLocation.parse(id)));
            });
        }

        @SubscribeEvent
        public static void modifyStickerBakedModel(ModelEvent.ModifyBakingResult event) {
            ModelResourceLocation key = ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "sticker"));
            BakedModel original = event.getModels().get(key);
            if (original != null) event.getModels().put(key, new StickerModel(original));
        }

        @SubscribeEvent
        public static void onGatherTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(StickerBookTooltipProvider.class, data -> new StickerBookTooltip(data.tag()));
        }
    }

    @EventBusSubscriber(modid = FireworkCapsules.MOD_ID)
    public static class CommonEvents {
        @SubscribeEvent
        public static void registerParticles(RegisterEvent event) {
            registerParticle("capsule_particle", FireworkCapsuleParticles.CAPSULE_PARTICLE, event);
        }

        private static void registerParticle(String name, ParticleType<?> particle, RegisterEvent event) {
            event.register(Registries.PARTICLE_TYPE, helper ->
                helper.register(ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, name), particle)
            );
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(FireworkCapsuleMenus.CAPSULE_STATION_MENU.value(), CapsuleStationScreen::new);
            event.register(FireworkCapsuleMenus.STICKER_BOOK_MENU.value(), StickerBookScreen::new);
        }

        @SubscribeEvent
        public static void registerDatapack(DataPackRegistryEvent.NewRegistry event) {
            event.dataPackRegistry(StickerExplosion.STICKERS, StickerExplosion.SIMPLE_CODEC, StickerExplosion.SIMPLE_CODEC);
        }
    }
}
