package com.necro.fireworkcapsules.common.item;

import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.stickers.Stickers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BallCapsuleItem extends Item {
    public BallCapsuleItem() {
        super(new Item.Properties().component(FireworkCapsuleComponents.STICKERS.value(), new Stickers(List.of())));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        Stickers stickers = itemStack.get(FireworkCapsuleComponents.STICKERS.value());
        if (stickers != null) {
            for (StickerExplosion stickerExplosion : stickers.explosions()) {
                stickerExplosion.addParticleNameTooltip(list::add);
                stickerExplosion.addAdditionalTooltip((component) ->
                    list.add(Component.literal("  ").append(component))
                );
            }
        }
    }
}
