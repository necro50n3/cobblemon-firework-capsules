package com.necro.fireworkcapsules.common.compat.jei;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JeiPlugin
public class FireworkCapsulesJEIPlugin implements IModPlugin {
    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(
            FireworkCapsuleItems.STICKER.value(),
            new ISubtypeInterpreter<>() {
                @Override
                public @Nullable Object getSubtypeData(ItemStack ingredient, UidContext context) {
                    return ingredient.get(FireworkCapsuleComponents.STICKER_EXPLOSION.value());
                }

                @Override
                public @NotNull String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
                    return "null";
                }
            }
        );
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "stickers");
    }
}
