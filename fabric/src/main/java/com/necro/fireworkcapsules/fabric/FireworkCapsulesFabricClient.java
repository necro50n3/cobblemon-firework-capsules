package com.necro.fireworkcapsules.fabric;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.client.StickerModel;
import com.necro.fireworkcapsules.fabric.gui.FabricMenus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

public class FireworkCapsulesFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricMenus.registerClient();

        ModelLoadingPlugin.register(context ->
            context.modifyModelAfterBake().register(((model, context1) -> {
                ModelResourceLocation id = context1.topLevelId();
                return id != null && id.id().equals(ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "sticker"))
                    ? new StickerModel(model)
                    : model;
            }))
        );
    }
}
