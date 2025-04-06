package com.necro.fireworkcapsules.fabric.gui;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.gui.CapsuleStationScreen;
import com.necro.fireworkcapsules.common.gui.CapsuleStationMenu;
import com.necro.fireworkcapsules.common.gui.FireworkCapsuleMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class FabricMenus {
    public static void register() {
        FireworkCapsuleMenus.CAPSULE_STATION_MENU = (Holder<MenuType<CapsuleStationMenu>>) (Object) Registry.registerForHolder(BuiltInRegistries.MENU,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "capsule_station_menu"),
            new MenuType<>(CapsuleStationMenu::new, FeatureFlags.VANILLA_SET)
        );
    }

    public static void registerClient() {
        MenuScreens.register(FireworkCapsuleMenus.CAPSULE_STATION_MENU.value(), CapsuleStationScreen::new);
    }
}
