package com.necro.fireworkcapsules.fabric.items;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.blocks.FireworkCapsuleBlocks;
import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.item.BallCapsuleItem;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import com.necro.fireworkcapsules.common.item.StickerItem;
import com.necro.fireworkcapsules.common.stickers.ElementalStickers;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FabricItems {
    public static void register() {
        FireworkCapsuleItems.BALL_CAPSULE = Registry.registerForHolder(
            BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "ball_capsule"),
            new BallCapsuleItem()
        );
        FireworkCapsuleItems.STICKER_BOOK = Registry.registerForHolder(
            BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "sticker_book"),
            new StickerBookItemFabric()
        );
        FireworkCapsuleItems.STICKER = Registry.registerForHolder(
            BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "sticker"),
            new StickerItem()
        );

        FireworkCapsuleItems.BUG_STICKER = registerSticker("bug_sticker", ElementalStickers.BUG_STICKER);
        FireworkCapsuleItems.DARK_STICKER = registerSticker("dark_sticker", ElementalStickers.DARK_STICKER);
        FireworkCapsuleItems.DRAGON_STICKER = registerSticker("dragon_sticker", ElementalStickers.DRAGON_STICKER);
        FireworkCapsuleItems.ELECTRIC_STICKER = registerSticker("electric_sticker", ElementalStickers.ELECTRIC_STICKER);
        FireworkCapsuleItems.FAIRY_STICKER = registerSticker("fairy_sticker", ElementalStickers.FAIRY_STICKER);
        FireworkCapsuleItems.FIGHTING_STICKER = registerSticker("fighting_sticker", ElementalStickers.FIGHTING_STICKER);
        FireworkCapsuleItems.FIRE_STICKER = registerSticker("fire_sticker", ElementalStickers.FIRE_STICKER);
        FireworkCapsuleItems.FLYING_STICKER = registerSticker("flying_sticker", ElementalStickers.FLYING_STICKER);
        FireworkCapsuleItems.GHOST_STICKER = registerSticker("ghost_sticker", ElementalStickers.GHOST_STICKER);
        FireworkCapsuleItems.GRASS_STICKER = registerSticker("grass_sticker", ElementalStickers.GRASS_STICKER);
        FireworkCapsuleItems.GROUND_STICKER = registerSticker("ground_sticker", ElementalStickers.GROUND_STICKER);
        FireworkCapsuleItems.ICE_STICKER = registerSticker("ice_sticker", ElementalStickers.ICE_STICKER);
        FireworkCapsuleItems.NORMAL_STICKER = registerSticker("normal_sticker", ElementalStickers.NORMAL_STICKER);
        FireworkCapsuleItems.POISON_STICKER = registerSticker("poison_sticker", ElementalStickers.POISON_STICKER);
        FireworkCapsuleItems.PSYCHIC_STICKER = registerSticker("psychic_sticker", ElementalStickers.PSYCHIC_STICKER);
        FireworkCapsuleItems.ROCK_STICKER = registerSticker("rock_sticker", ElementalStickers.ROCK_STICKER);
        FireworkCapsuleItems.STEEL_STICKER = registerSticker("steel_sticker", ElementalStickers.STEEL_STICKER);
        FireworkCapsuleItems.WATER_STICKER = registerSticker("water_sticker", ElementalStickers.WATER_STICKER);

        FireworkCapsuleItems.CAPSULE_TAB = Registry.registerForHolder(BuiltInRegistries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "capsules_tab"),
            FabricItemGroup.builder().title(Component.translatable("itemgroup.fireworkcapsules.capsules_tab"))
                .icon(() -> new ItemStack(FireworkCapsuleItems.BALL_CAPSULE))
                .displayItems((context, entries) -> {
                    entries.accept(FireworkCapsuleBlocks.CAPSULE_STATION.value());
                    entries.accept(FireworkCapsuleItems.BALL_CAPSULE.value());
                    entries.accept(FireworkCapsuleItems.STICKER_BOOK.value());

                    context.holders().lookupOrThrow(StickerExplosion.STICKERS).listElements().forEach(reference -> {
                        StickerExplosion sticker = reference.value();
                        ItemStack itemStack = FireworkCapsuleItems.STICKER.value().getDefaultInstance();
                        itemStack.set(FireworkCapsuleComponents.STICKER_EXPLOSION.value(), sticker);
                        entries.accept(itemStack);
                    });
                }).build()
        );
    }

    private static Holder<Item> registerSticker(String name, StickerExplosion explosion) {
        return Registry.registerForHolder(BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, name),
            new StickerItem(explosion)
        );
    }
}
