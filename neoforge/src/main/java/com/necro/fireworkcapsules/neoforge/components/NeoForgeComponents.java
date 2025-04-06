package com.necro.fireworkcapsules.neoforge.components;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.stickers.Stickers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class NeoForgeComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, FireworkCapsules.MOD_ID);

    public static void register() {
        FireworkCapsuleComponents.STICKERS = registerComponent("stickers", builder -> builder.persistent(Stickers.CODEC));
        FireworkCapsuleComponents.STICKER_EXPLOSION = registerComponent("sticker_explosion", builder -> builder.persistent(StickerExplosion.CODEC));
    }

    private static <T> Holder<DataComponentType<T>> registerComponent(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return (Holder<DataComponentType<T>>) (Object) DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }
}
