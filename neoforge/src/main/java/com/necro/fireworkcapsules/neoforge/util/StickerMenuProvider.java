package com.necro.fireworkcapsules.neoforge.util;

import com.necro.fireworkcapsules.common.gui.stickerbook.StickerBookMenu;
import com.necro.fireworkcapsules.common.item.StickerBookItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public record StickerMenuProvider(byte b) implements MenuProvider {
    @Override
    public @NotNull Component getDisplayName() {
        return StickerBookItem.CONTAINER_TITLE;
    }

    @Override
    public @NotNull AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new StickerBookMenu(i, inventory, this.b());
    }
}
