package com.necro.fireworkcapsules.fabric;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.fabric.blocks.FabricBlocks;
import com.necro.fireworkcapsules.fabric.components.FabricComponents;
import com.necro.fireworkcapsules.fabric.entities.FabricEntities;
import com.necro.fireworkcapsules.fabric.gui.FabricMenus;
import com.necro.fireworkcapsules.fabric.items.FabricItems;
import com.necro.fireworkcapsules.fabric.particles.FabricParticles;
import com.necro.fireworkcapsules.fabric.recipes.FabricRecipes;
import net.fabricmc.api.ModInitializer;

public class FireworkCapsulesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FireworkCapsules.init();
        FabricBlocks.register();
        FabricComponents.register();
        FabricEntities.register();
        FabricItems.register();
        FabricMenus.register();
        FabricParticles.register();
        FabricRecipes.register();
    }

}
