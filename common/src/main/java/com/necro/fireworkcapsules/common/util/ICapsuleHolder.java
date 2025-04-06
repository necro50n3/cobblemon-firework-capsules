package com.necro.fireworkcapsules.common.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;

public interface ICapsuleHolder {
    void setCapsule(HolderLookup.Provider provider, ItemStack itemStack);

    ItemStack getCapsule(HolderLookup.Provider provider);
}
