package com.necro.fireworkcapsules.fabric.recipes;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.recipes.CapsuleRecipe;
import com.necro.fireworkcapsules.common.recipes.FireworkCapsuleRecipes;
import com.necro.fireworkcapsules.common.recipes.StickerRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class FabricRecipes {
    public static void register() {
        FireworkCapsuleRecipes.CAPSULE_RECIPE = Registry.registerForHolder(BuiltInRegistries.RECIPE_SERIALIZER,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "capsule_recipe"),
            new SimpleCraftingRecipeSerializer<>(CapsuleRecipe::new)
        );

        FireworkCapsuleRecipes.STICKER_RECIPE = Registry.registerForHolder(BuiltInRegistries.RECIPE_SERIALIZER,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "sticker_recipe"),
            new SimpleCraftingRecipeSerializer<>(StickerRecipe::new)
        );
    }
}
