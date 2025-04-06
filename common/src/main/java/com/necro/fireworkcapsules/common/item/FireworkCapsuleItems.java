package com.necro.fireworkcapsules.common.item;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class FireworkCapsuleItems {
    public static Holder<Item> BALL_CAPSULE;

    public static Holder<Item> BUG_STICKER;
    public static Holder<Item> DARK_STICKER;
    public static Holder<Item> DRAGON_STICKER;
    public static Holder<Item> ELECTRIC_STICKER;
    public static Holder<Item> FAIRY_STICKER;
    public static Holder<Item> FIGHTING_STICKER;
    public static Holder<Item> FIRE_STICKER;
    public static Holder<Item> FLYING_STICKER;
    public static Holder<Item> GHOST_STICKER;
    public static Holder<Item> GRASS_STICKER;
    public static Holder<Item> GROUND_STICKER;
    public static Holder<Item> ICE_STICKER;
    public static Holder<Item> NORMAL_STICKER;
    public static Holder<Item> POISON_STICKER;
    public static Holder<Item> PSYCHIC_STICKER;
    public static Holder<Item> ROCK_STICKER;
    public static Holder<Item> STEEL_STICKER;
    public static Holder<Item> WATER_STICKER;

    public static Holder<CreativeModeTab> CAPSULE_TAB;
    public static final TagKey<Item> STICKERS = createTag("stickers");

    public static TagKey<Item> createTag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, name));
    }
}
