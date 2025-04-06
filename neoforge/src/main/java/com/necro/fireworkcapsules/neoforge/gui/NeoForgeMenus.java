package com.necro.fireworkcapsules.neoforge.gui;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.gui.FireworkCapsuleMenus;
import com.necro.fireworkcapsules.common.gui.CapsuleStationMenu;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, FireworkCapsules.MOD_ID);

    public static void register() {
        FireworkCapsuleMenus.CAPSULE_STATION_MENU = (Holder<MenuType<CapsuleStationMenu>>) (Object) MENU_TYPES.register("capsule_station_menu",
            () -> IMenuTypeExtension.create(CapsuleStationMenu::new)
        );
    }
}
