package com.necro.fireworkcapsules.common.gui;

import com.necro.fireworkcapsules.common.blocks.FireworkCapsuleBlocks;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CapsuleStationMenu extends AbstractContainerMenu {
    private static final int CAPSULE_SLOT_START = 0;
    private static final int CAPSULE_SLOT_END = 6;
    private static final int INV_SLOT_START = 6;
    private static final int INV_SLOT_END = 33;
    private static final int USE_ROW_SLOT_START = 33;
    private static final int USE_ROW_SLOT_END = 42;
    private final CapsuleContainer capsuleSlots;
    private final ContainerLevelAccess access;
    private final Player player;

    public CapsuleStationMenu(int i, Inventory inventory) {
        this(i, inventory, ContainerLevelAccess.NULL);
    }

    public CapsuleStationMenu(int i, Inventory inventory, RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        this(i, inventory, ContainerLevelAccess.NULL);
    }

    public CapsuleStationMenu(int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(FireworkCapsuleMenus.CAPSULE_STATION_MENU.value(), i);
        this.access = containerLevelAccess;
        this.player = inventory.player;
        this.capsuleSlots = CapsuleContainer.create(this.player);

        for(int idx = 0; idx < 6; ++idx) {
            this.addSlot(new CapsuleSlot(
                this.capsuleSlots, idx,
                42 + 55 * (idx % 3),
                26 + 35 * (idx / 3)
            ));
        }

        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 99 + y * 18));
            }
        }

        for(int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(inventory, x, 8 + x * 18, 157));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, FireworkCapsuleBlocks.CAPSULE_STATION.value());
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);

        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (i < CAPSULE_SLOT_END) {
                if (!this.moveItemStackTo(itemStack2, CAPSULE_SLOT_END, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                for (int j = CAPSULE_SLOT_START; j < CAPSULE_SLOT_END; j++) {
                    if (this.moveItemStackTo(itemStack2, j, j + 1, false)) break;
                }
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
}
