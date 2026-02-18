package com.necro.fireworkcapsules.fabric.gui;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.gui.capsulestation.CapsuleStationScreen;
import com.necro.fireworkcapsules.common.gui.capsulestation.CapsuleStationMenu;
import com.necro.fireworkcapsules.common.gui.FireworkCapsuleMenus;
import com.necro.fireworkcapsules.common.gui.stickerbook.StickerBookMenu;
import com.necro.fireworkcapsules.common.gui.stickerbook.StickerBookScreen;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class FabricMenus {
    @SuppressWarnings("unchecked")
    public static void register() {
        FireworkCapsuleMenus.CAPSULE_STATION_MENU = (Holder<MenuType<CapsuleStationMenu>>) (Object) Registry.registerForHolder(BuiltInRegistries.MENU,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "capsule_station_menu"),
            new MenuType<>(CapsuleStationMenu::new, FeatureFlags.VANILLA_SET)
        );
        FireworkCapsuleMenus.STICKER_BOOK_MENU = (Holder<MenuType<StickerBookMenu>>) (Object) Registry.registerForHolder(BuiltInRegistries.MENU,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "sticker_book_menu"),
            new ExtendedScreenHandlerType<>(StickerBookMenu::new, StreamCodec.of((buf, b) -> buf.writeByte(b), FriendlyByteBuf::readByte))
        );
    }

    public static void registerClient() {
        MenuScreens.register(FireworkCapsuleMenus.CAPSULE_STATION_MENU.value(), CapsuleStationScreen::new);
        MenuScreens.register(FireworkCapsuleMenus.STICKER_BOOK_MENU.value(), StickerBookScreen::new);
    }
}
