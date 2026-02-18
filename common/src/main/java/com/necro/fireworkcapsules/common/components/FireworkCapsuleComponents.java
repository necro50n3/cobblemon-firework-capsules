package com.necro.fireworkcapsules.common.components;

import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.stickers.Stickers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class FireworkCapsuleComponents {
    public static Holder<DataComponentType<Stickers>> STICKERS;
    public static Holder<DataComponentType<StickerExplosion>> STICKER_EXPLOSION;
    public static Holder<DataComponentType<CompoundTag>> STICKER_CONTAINER;
}
