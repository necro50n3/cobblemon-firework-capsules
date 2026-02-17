package com.necro.fireworkcapsules.common.recipes;

import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.stickers.Stickers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StickerRecipe extends CustomRecipe {
    private static final Ingredient CAPSULE_INGREDIENT = Ingredient.of(FireworkCapsuleItems.BALL_CAPSULE.value());
    private static final Ingredient PAPER_INGREDIENT = Ingredient.of(Items.PAPER);

    public StickerRecipe(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        int capsule = 0;
        int paper = 0;

        ItemStack capsuleItem = ItemStack.EMPTY;
        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack itemStack = recipeInput.getItem(i);
            if (itemStack.isEmpty()) continue;
            else if (CAPSULE_INGREDIENT.test(itemStack)) {
                if (capsule++ > 0) return false;
                capsuleItem = itemStack;
            }
            else if (PAPER_INGREDIENT.test(itemStack)) {
                if (paper++ > 0) return false;
            }
            else return false;
        }

        Stickers stickers = capsuleItem.get(FireworkCapsuleComponents.STICKERS.value());
        return capsule == 1 && paper == 1 && stickers != null && !stickers.explosions().isEmpty();
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
        ItemStack capsule = ItemStack.EMPTY;

        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack itemStack = recipeInput.getItem(i);
            if (itemStack.isEmpty()) continue;
            else if (CAPSULE_INGREDIENT.test(itemStack)) capsule = itemStack;
        }

        Stickers stickers = capsule.get(FireworkCapsuleComponents.STICKERS.value());
        if (stickers == null || stickers.explosions().isEmpty()) return ItemStack.EMPTY;

        StickerExplosion stickerExplosion = stickers.explosions().getFirst();
        ItemStack sticker = FireworkCapsuleItems.STICKER.value().getDefaultInstance();
        sticker.set(FireworkCapsuleComponents.STICKER_EXPLOSION.value(), stickerExplosion);
        return sticker;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        ItemStack capsule = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack itemStack = input.getItem(i);
            if (itemStack.isEmpty()) continue;
            else if (CAPSULE_INGREDIENT.test(itemStack)) {
                ItemStack copy = itemStack.copyWithCount(1);
                remaining.set(i, copy);
                capsule = copy;
            }
        }

        Stickers stickers = capsule.get(FireworkCapsuleComponents.STICKERS.value());
        if (stickers == null || stickers.explosions().isEmpty()) return remaining;

        Stickers newStickers = new Stickers(new ArrayList<>(stickers.explosions().subList(1, stickers.explosions().size())));
        capsule.set(FireworkCapsuleComponents.STICKERS.value(), newStickers);

        return remaining;
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
