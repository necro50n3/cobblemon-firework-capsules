package com.necro.fireworkcapsules.fabric;

import com.necro.fireworkcapsules.common.events.StickerRegistry;
import com.necro.fireworkcapsules.fabric.events.RegisterStickersEvent;
import com.necro.fireworkcapsules.fabric.events.StickerRegistryEvent;
import com.necro.fireworkcapsules.fabric.gui.FabricMenus;
import com.necro.fireworkcapsules.fabric.particles.FabricParticlesClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class FireworkCapsulesFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricMenus.registerClient();
        FabricParticlesClient.registerClient();

        StickerRegistryEvent.EVENT.register(RegisterStickersEvent::register);
        ClientLifecycleEvents.CLIENT_STARTED.register(event -> StickerRegistryEvent.EVENT.invoker().register(StickerRegistry::register));
    }
}
