package com.necro.fireworkcapsules.neoforge;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.neoforge.blocks.NeoForgeBlocks;
import com.necro.fireworkcapsules.neoforge.components.NeoForgeComponents;
import com.necro.fireworkcapsules.neoforge.entities.NeoForgeEntities;
import com.necro.fireworkcapsules.neoforge.gui.NeoForgeMenus;
import com.necro.fireworkcapsules.neoforge.items.NeoForgeItems;
import com.necro.fireworkcapsules.neoforge.particles.NeoForgeParticles;
import com.necro.fireworkcapsules.neoforge.recipes.NeoForgeRecipes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(FireworkCapsules.MOD_ID)
public class FireworkCapsulesNeoForge {
    public FireworkCapsulesNeoForge(IEventBus modBus, ModContainer container) {
        FireworkCapsules.init();

        NeoForgeBlocks.register();
        NeoForgeBlocks.BLOCKS.register(modBus);
        NeoForgeComponents.register();
        NeoForgeComponents.DATA_COMPONENT_TYPES.register(modBus);
        NeoForgeEntities.register();
        NeoForgeEntities.ENTITY_TYPES.register(modBus);
        NeoForgeItems.register();
        NeoForgeItems.ITEMS.register(modBus);
        NeoForgeItems.CREATIVE_MODE_TABS.register(modBus);
        NeoForgeMenus.register();
        NeoForgeMenus.MENU_TYPES.register(modBus);
        NeoForgeParticles.register();
        NeoForgeRecipes.register();
        NeoForgeRecipes.RECIPE_SERIALIZERS.register(modBus);
    }
}
