package com.necro.fireworkcapsules.common.gui;

import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CapsuleSlot extends Slot {
    private final int slot;

    public CapsuleSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
        this.slot = index;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.is(FireworkCapsuleItems.BALL_CAPSULE) && ((CapsuleContainer) this.container).isActive(this.slot);
    }

    @Override
    public void setByPlayer(ItemStack itemStack) {
        super.setByPlayer(itemStack);
        ((CapsuleContainer) this.container).onCapsuleChange(this.slot, this.getItem());
    }

    @Override
    public void onTake(Player player, ItemStack itemStack) {
        super.onTake(player, itemStack);
        ((CapsuleContainer) this.container).onCapsuleChange(this.slot, this.getItem());
    }

    @Override
    public boolean isActive() {
        return ((CapsuleContainer) this.container).isActive(this.slot);
    }
}
