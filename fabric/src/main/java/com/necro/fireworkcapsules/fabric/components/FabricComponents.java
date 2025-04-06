package com.necro.fireworkcapsules.fabric.components;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.stickers.Stickers;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.UnaryOperator;

public class FabricComponents {
    public static void register() {
        FireworkCapsuleComponents.STICKERS = registerComponent("stickers", builder -> builder.persistent(Stickers.CODEC));
        FireworkCapsuleComponents.STICKER_EXPLOSION = registerComponent("sticker_explosion", builder -> builder.persistent(StickerExplosion.CODEC));
    }

    private static <T> Holder<DataComponentType<T>> registerComponent(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return (Holder<DataComponentType<T>>) (Object) Registry.registerForHolder(BuiltInRegistries.DATA_COMPONENT_TYPE,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, name),
            builderOperator.apply(DataComponentType.builder()).build()
        );
    }
}
