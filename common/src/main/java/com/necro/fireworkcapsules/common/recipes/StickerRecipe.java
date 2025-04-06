package com.necro.fireworkcapsules.common.recipes;

import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class StickerRecipe extends CustomRecipe {
    private static final Ingredient STICKER_INGREDIENT = Ingredient.of(FireworkCapsuleItems.STICKERS);
    private static final Ingredient TRAIL_INGREDIENT = Ingredient.of(Items.DIAMOND);
    private static final Ingredient TWINKLE_INGREDIENT = Ingredient.of(Items.GLOWSTONE_DUST);

    public StickerRecipe(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        boolean sticker = false;
        boolean trail = false;
        boolean twinkle = false;
        int colors = 0;

        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack itemStack = recipeInput.getItem(i);
            if (itemStack.isEmpty()) continue;
            else if (STICKER_INGREDIENT.test(itemStack)) {
                if (sticker) return false;
                else sticker = true;
            }
            else if (TRAIL_INGREDIENT.test(itemStack)) {
                if (trail) return false;
                else trail = true;
            }
            else if (TWINKLE_INGREDIENT.test(itemStack)) {
                if (twinkle) return false;
                else twinkle = true;
            }
            else if (itemStack.getItem() instanceof DyeItem) colors++;
            else return false;
        }

        return sticker && (trail || twinkle || colors > 0);
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
        ItemStack sticker = ItemStack.EMPTY;
        IntList fadeColors = new IntArrayList();
        boolean hasTrail = false;
        boolean hasTwinkle = false;

        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack itemStack = recipeInput.getItem(i);
            if (itemStack.isEmpty()) continue;
            else if (STICKER_INGREDIENT.test(itemStack)) {
                sticker = itemStack;
            }
            else if (TRAIL_INGREDIENT.test(itemStack)) hasTrail = true;
            else if (TWINKLE_INGREDIENT.test(itemStack)) hasTwinkle = true;
            else if (itemStack.getItem() instanceof DyeItem dye) fadeColors.add(dye.getDyeColor().getFireworkColor());
        }

        ItemStack stack = sticker.copy();
        stack.setCount(1);

        StickerExplosion explosion = stack.get(FireworkCapsuleComponents.STICKER_EXPLOSION.value());
        assert explosion != null;
        fadeColors.addAll(explosion.fadeColors());

        StickerExplosion newExplosion = new StickerExplosion(
            explosion.id(), explosion.colors(), fadeColors, hasTrail || explosion.hasTrail(), hasTwinkle || explosion.hasTwinkle()
        );
        stack.set(FireworkCapsuleComponents.STICKER_EXPLOSION.value(), newExplosion);
        return stack;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i * j >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return FireworkCapsuleRecipes.STICKER_RECIPE.value();
    }
}
