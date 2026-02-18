package com.necro.fireworkcapsules.fabric.items;

import com.necro.fireworkcapsules.common.gui.stickerbook.StickerBookMenu;
import com.necro.fireworkcapsules.common.item.StickerBookItem;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public class StickerBookItemFabric extends StickerBookItem {
    @Override
    protected MenuProvider getMenu(byte b) {
        return new ExtendedScreenHandlerFactory<Byte>() {
            @Override
            public @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                return new StickerBookMenu(i, inventory, b);
            }

            @Override
            public @NotNull Component getDisplayName() {
                return CONTAINER_TITLE;
            }

            @Override
            public Byte getScreenOpeningData(ServerPlayer player) {
                return b;
            }
        };
    }
}
