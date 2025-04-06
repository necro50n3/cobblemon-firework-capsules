package com.necro.fireworkcapsules.common.recipes;

import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import com.necro.fireworkcapsules.common.item.StickerItem;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.stickers.Stickers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CapsuleRecipe extends CustomRecipe {
    private static final Ingredient STAR_INGREDIENT = Ingredient.of(Items.FIREWORK_STAR);
    private static final Ingredient POKEBALL_INGREDIENT = Ingredient.of(FireworkCapsuleItems.BALL_CAPSULE.value());

    public CapsuleRecipe(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        int stickers = 0;
        int capsules = 0;

        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack itemStack = recipeInput.getItem(i);
            if (itemStack.isEmpty()) continue;
            else if (STAR_INGREDIENT.test(itemStack) || itemStack.getItem() instanceof StickerItem) stickers++;
            else if (POKEBALL_INGREDIENT.test(itemStack)) capsules++;
            else return false;
        }

        return stickers > 0 && capsules == 1;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
        List<StickerExplosion> list = new ArrayList<>();

        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack itemStack = recipeInput.getItem(i);
            if (itemStack.isEmpty()) continue;
            else if (STAR_INGREDIENT.test(itemStack)) {
                FireworkExplosion fireworkExplosion = itemStack.get(DataComponents.FIREWORK_EXPLOSION);
                if (fireworkExplosion != null) list.add(StickerExplosion.fromFireworks(fireworkExplosion));
            }
            else if (itemStack.getItem() instanceof StickerItem) {
                StickerExplosion stickerExplosion = itemStack.get(FireworkCapsuleComponents.STICKER_EXPLOSION.value());
                if (stickerExplosion != null) list.add(stickerExplosion);
            }
        }

        ItemStack stack = this.getResultItem(provider);
        stack.set(FireworkCapsuleComponents.STICKERS.value(), new Stickers(list));
        return stack;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return new ItemStack(FireworkCapsuleItems.BALL_CAPSULE);
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i * j >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return FireworkCapsuleRecipes.CAPSULE_RECIPE.value();
    }
}
