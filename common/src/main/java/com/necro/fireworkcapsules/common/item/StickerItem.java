package com.necro.fireworkcapsules.common.item;

import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StickerItem extends BlockItem {
    public StickerItem(Block block, StickerExplosion explosion) {
        super(block, new Item.Properties().component(FireworkCapsuleComponents.STICKER_EXPLOSION.value(), explosion));
    }

    public StickerItem(Block block) {
        super(block, new Item.Properties());
    }

    @Override
    public @NotNull InteractionResult place(BlockPlaceContext blockPlaceContext) {
        if (!blockPlaceContext.getItemInHand().has(FireworkCapsuleComponents.STICKER_EXPLOSION.value())) return InteractionResult.FAIL;
        return super.place(blockPlaceContext);
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        StickerExplosion sticker = itemStack.get(FireworkCapsuleComponents.STICKER_EXPLOSION.value());
        if (sticker == null) return super.getName(itemStack);
        return Component.translatable(String.format("item.%s.%s", sticker.id().getNamespace(), sticker.id().getPath()));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        StickerExplosion stickerExplosion = itemStack.get(FireworkCapsuleComponents.STICKER_EXPLOSION.value());
        if (list == null) return;
        if (stickerExplosion == null) list.add(Component.translatable("item.fireworkcapsules.sticker.id.sticker").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        else stickerExplosion.addToTooltip(tooltipContext, list::add, tooltipFlag);
    }
}
