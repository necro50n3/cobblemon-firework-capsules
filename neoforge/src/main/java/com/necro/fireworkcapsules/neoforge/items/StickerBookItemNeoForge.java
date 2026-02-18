package com.necro.fireworkcapsules.neoforge.items;

import com.necro.fireworkcapsules.common.gui.stickerbook.StickerBookMenu;
import com.necro.fireworkcapsules.common.item.StickerBookItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;

public class StickerBookItemNeoForge extends StickerBookItem {
    @Override
    protected void openMenu(ServerPlayer player, byte b) {
        player.openMenu(this.getMenu(b), buf -> buf.writeByte(b));
    }

    @Override
    protected MenuProvider getMenu(byte hand) {
        return new SimpleMenuProvider(
            (i, inventory, player) -> new StickerBookMenu(i, inventory, hand),
            CONTAINER_TITLE
        );
    }
}
