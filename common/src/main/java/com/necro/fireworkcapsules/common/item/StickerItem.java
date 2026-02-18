package com.necro.fireworkcapsules.common.item;

import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StickerItem extends Item {
    public StickerItem(StickerExplosion explosion) {
        super(new Item.Properties().component(FireworkCapsuleComponents.STICKER_EXPLOSION.value(), explosion));
    }

    public StickerItem() {
        super(new Item.Properties());
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        StickerExplosion sticker = itemStack.get(FireworkCapsuleComponents.STICKER_EXPLOSION.value());
        if (sticker == null) return super.getName(itemStack);
        return Component.translatable("item.fireworkcapsules." + sticker.id().getPath());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        StickerExplosion stickerExplosion = itemStack.get(FireworkCapsuleComponents.STICKER_EXPLOSION.value());
        if (list == null) return;
        if (stickerExplosion == null) list.add(Component.translatable("item.fireworkcapsules.sticker.id.sticker").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        else stickerExplosion.addToTooltip(tooltipContext, list::add, tooltipFlag);
    }
}
