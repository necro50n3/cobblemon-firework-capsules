package com.necro.fireworkcapsules.neoforge.gui;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.gui.FireworkCapsuleMenus;
import com.necro.fireworkcapsules.common.gui.capsulestation.CapsuleStationMenu;
import com.necro.fireworkcapsules.common.gui.stickerbook.StickerBookMenu;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, FireworkCapsules.MOD_ID);

    @SuppressWarnings("unchecked")
    public static void register() {
        FireworkCapsuleMenus.CAPSULE_STATION_MENU = (Holder<MenuType<CapsuleStationMenu>>) (Object) MENU_TYPES.register("capsule_station_menu",
            () -> IMenuTypeExtension.create(CapsuleStationMenu::new)
        );
        FireworkCapsuleMenus.STICKER_BOOK_MENU = (Holder<MenuType<StickerBookMenu>>) (Object) MENU_TYPES.register("sticker_book_menu",
            () -> IMenuTypeExtension.create(StickerBookMenu::new)
        );
    }
}
