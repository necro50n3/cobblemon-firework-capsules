package com.necro.fireworkcapsules.common.item;

import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class StickerItem extends Item {
    public StickerItem(StickerExplosion explosion) {
        super(new Item.Properties().component(FireworkCapsuleComponents.STICKER_EXPLOSION.value(), explosion));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        StickerExplosion stickerExplosion = itemStack.get(FireworkCapsuleComponents.STICKER_EXPLOSION.value());
        if (stickerExplosion == null || list == null) return;
        stickerExplosion.addToTooltip(tooltipContext, list::add, tooltipFlag);
    }
}
