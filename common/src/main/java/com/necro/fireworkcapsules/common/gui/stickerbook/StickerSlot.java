package com.necro.fireworkcapsules.common.gui.stickerbook;

import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class StickerSlot extends Slot {
    public StickerSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.is(FireworkCapsuleItems.STICKER);
    }

    @Override
    public int getMaxStackSize(ItemStack itemStack) {
        return this.getMaxStackSize();
    }
}
