package com.necro.fireworkcapsules.common.gui.stickerbook;

import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.gui.FireworkCapsuleMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StickerBookMenu extends AbstractContainerMenu {
    public static final int ROWS = 6;
    public static final int STICKER_SLOTS = ROWS * 9;

    public static final int STICKER_SLOT_START = 0;
    public static final int STICKER_SLOT_END = STICKER_SLOTS;
    public static final int INV_SLOT_START = STICKER_SLOTS;
    public static final int INV_SLOT_END = STICKER_SLOTS + 27;
    public static final int USE_ROW_SLOT_START = STICKER_SLOTS + 27;
    public static final int USE_ROW_SLOT_END = STICKER_SLOTS + 36;

    private final StickerContainer container;
    private final InteractionHand hand;

    public StickerBookMenu(int i, Inventory inventory, FriendlyByteBuf buf) {
        this(i, inventory, buf.readByte());
    }

    public StickerBookMenu(int i, Inventory inventory, byte b) {
        super(FireworkCapsuleMenus.STICKER_BOOK_MENU.value(), i);
        this.hand = b == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        ItemStack stickerBook = inventory.player.getItemInHand(this.hand);
        this.container = new StickerContainer(stickerBook, inventory.player.registryAccess());

        int k = (ROWS - 4) * 18;

        for(int l = 0; l < ROWS; ++l) {
            for(int m = 0; m < 9; ++m) {
                this.addSlot(new StickerSlot(this.container, m + l * 9, 8 + m * 18, 18 + l * 18));
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int m = 0; m < 9; ++m) {
                this.addSlot(new Slot(inventory, m + l * 9 + 9, 8 + m * 18, 103 + l * 18 + k));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, 161 + k));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (i < STICKER_SLOT_END) {
                if (!this.moveItemStackTo(itemStack2, STICKER_SLOT_END, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, STICKER_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemStack;
    }

    @Override
    public void removed(Player player) {
        if (!player.level().isClientSide()) {
            player.getItemInHand(this.hand).set(FireworkCapsuleComponents.STICKER_CONTAINER.value(), this.container.save());
        }
        super.removed(player);
    }
}
