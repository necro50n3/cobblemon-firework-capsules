package com.necro.fireworkcapsules.common.item;

import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.tooltip.StickerBookTooltipProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class StickerBookItem extends Item {
    public static final Component CONTAINER_TITLE = Component.translatable("container.fireworkcapsules.sticker_book");

    public StickerBookItem() {
        super(new Item.Properties().stacksTo(1).component(FireworkCapsuleComponents.STICKER_CONTAINER.value(), new CompoundTag()));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);

        byte b;
        if (interactionHand == InteractionHand.MAIN_HAND) b = 0;
        else if (interactionHand == InteractionHand.OFF_HAND) b = 1;
        else return InteractionResultHolder.fail(itemStack);

        if (!level.isClientSide()) {
            player.awardStat(Stats.ITEM_USED.get(this));
            this.openMenu((ServerPlayer) player, b);
        }
        else {
            level.playLocalSound(player, SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 0.5f, 1.0f);
        }
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack itemStack) {
        CompoundTag tag = itemStack.get(FireworkCapsuleComponents.STICKER_CONTAINER.value());
        if (tag == null || !tag.contains("Items")) return Optional.empty();
        return Optional.of(new StickerBookTooltipProvider(tag));
    }

    protected void openMenu(ServerPlayer player, byte b) {
        player.openMenu(this.getMenu(b));
    }

    protected abstract MenuProvider getMenu(byte hand);
}
