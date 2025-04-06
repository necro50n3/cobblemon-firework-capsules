package com.necro.fireworkcapsules.neoforge.recipes;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.recipes.CapsuleRecipe;
import com.necro.fireworkcapsules.common.recipes.FireworkCapsuleRecipes;
import com.necro.fireworkcapsules.common.recipes.StickerRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
        Registries.RECIPE_SERIALIZER, FireworkCapsules.MOD_ID
    );

    public static void register() {
        FireworkCapsuleRecipes.CAPSULE_RECIPE = RECIPE_SERIALIZERS.register("capsule_recipe",
            () -> new SimpleCraftingRecipeSerializer<>(CapsuleRecipe::new)
        );

        FireworkCapsuleRecipes.STICKER_RECIPE = RECIPE_SERIALIZERS.register("sticker_recipe",
            () -> new SimpleCraftingRecipeSerializer<>(StickerRecipe::new)
        );
    }
}
