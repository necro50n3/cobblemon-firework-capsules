package com.necro.fireworkcapsules.neoforge.components;

import com.mojang.serialization.Codec;
import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.stickers.Stickers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class NeoForgeComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, FireworkCapsules.MOD_ID);

    @SuppressWarnings("unchecked")
    public static void register() {
        FireworkCapsuleComponents.STICKERS = (Holder<DataComponentType<Stickers>>) (Object) registerComponent("stickers", Stickers.CODEC);
        FireworkCapsuleComponents.STICKER_EXPLOSION = (Holder<DataComponentType<StickerExplosion>>) (Object) registerComponent("sticker_explosion", StickerExplosion.CODEC);
    }

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> registerComponent(String name, Codec<T> codec) {
        UnaryOperator<DataComponentType.Builder<T>> builderOperator = builder -> builder.persistent(codec);
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }
}
