package com.necro.fireworkcapsules.common.client;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StickerModel implements BakedModel {
    private final BakedModel original;
    private final ItemOverrides overrides;

    public StickerModel(BakedModel original) {
        this.original = original;
        this.overrides = new ItemOverrides() {
            @Override
            public BakedModel resolve(BakedModel original, ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
                BakedModel resolved = StickerModel.this.resolve(original, itemStack);
                return resolved == original ? resolved : resolved.getOverrides().resolve(resolved, itemStack, level, entity, seed);
            }
        };
    }

    public BakedModel resolve(BakedModel original, ItemStack stack) {
        StickerExplosion sticker = stack.get(FireworkCapsuleComponents.STICKER_EXPLOSION.value());
        if (sticker == null) return original;

        return Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "item/stickers/" + sticker.id().getPath()),
            "standalone"
        ));
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource) {
        return this.original.getQuads(blockState, direction, randomSource);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.original.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return this.original.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return this.original.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return this.original.isCustomRenderer();
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return this.original.getParticleIcon();
    }

    @Override
    public @NotNull ItemTransforms getTransforms() {
        return this.original.getTransforms();
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return this.overrides;
    }
}
