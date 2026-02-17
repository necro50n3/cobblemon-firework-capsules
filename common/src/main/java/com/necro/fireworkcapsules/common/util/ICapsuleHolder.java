package com.necro.fireworkcapsules.common.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;

public interface ICapsuleHolder {
    void fc_setCapsule(HolderLookup.Provider provider, ItemStack itemStack);

    ItemStack fc_getCapsule(HolderLookup.Provider provider);
}
