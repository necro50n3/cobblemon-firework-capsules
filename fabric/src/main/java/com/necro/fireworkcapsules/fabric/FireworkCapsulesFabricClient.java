package com.necro.fireworkcapsules.fabric;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.blocks.FireworkCapsuleBlocks;
import com.necro.fireworkcapsules.common.client.StickerBlockRenderer;
import com.necro.fireworkcapsules.common.client.StickerModel;
import com.necro.fireworkcapsules.common.tooltip.StickerBookTooltip;
import com.necro.fireworkcapsules.common.tooltip.StickerBookTooltipProvider;
import com.necro.fireworkcapsules.fabric.gui.FabricMenus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.nbt.CompoundTag;
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

        BlockEntityRenderers.register(FireworkCapsuleBlocks.STICKER_ENTITY.value(), StickerBlockRenderer::new);
        TooltipComponentCallback.EVENT.register(component -> component instanceof StickerBookTooltipProvider(CompoundTag tag) ? new StickerBookTooltip(tag) : null);
    }
}
