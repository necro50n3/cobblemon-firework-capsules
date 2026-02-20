package com.necro.fireworkcapsules.neoforge.items;

import com.cobblemon.mod.common.item.group.CobblemonItemGroups;
import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.blocks.FireworkCapsuleBlocks;
import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.item.BallCapsuleItem;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import com.necro.fireworkcapsules.common.item.StickerItem;
import com.necro.fireworkcapsules.common.stickers.ElementalStickers;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(FireworkCapsules.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FireworkCapsules.MOD_ID);

    public static void register() {
        FireworkCapsuleItems.BALL_CAPSULE = ITEMS.register("ball_capsule", BallCapsuleItem::new);
        FireworkCapsuleItems.STICKER_BOOK = ITEMS.register("sticker_book", StickerBookItemNeoForge::new);
        FireworkCapsuleItems.STICKER = ITEMS.register("sticker", () -> new StickerItem(FireworkCapsuleBlocks.STICKER.value()));

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

        FireworkCapsuleItems.CAPSULE_TAB = CREATIVE_MODE_TABS.register("capsules_tab",
            () -> CreativeModeTab.builder().title(Component.translatable("itemgroup.fireworkcapsules.capsules_tab"))
                .withTabsBefore(CobblemonItemGroups.getUTILITY_ITEMS_KEY())
                .icon(() -> new ItemStack(FireworkCapsuleItems.BALL_CAPSULE))
                .displayItems((context, entries) -> {
                    entries.accept((ItemLike) FireworkCapsuleBlocks.CAPSULE_STATION);
                    entries.accept((ItemLike) FireworkCapsuleItems.BALL_CAPSULE);
                    entries.accept((ItemLike) FireworkCapsuleItems.STICKER_BOOK);

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
        return ITEMS.register(name, () -> new StickerItem(FireworkCapsuleBlocks.STICKER.value(), explosion));
    }

    public static void registerBlockItem(String name, DeferredBlock<?> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
