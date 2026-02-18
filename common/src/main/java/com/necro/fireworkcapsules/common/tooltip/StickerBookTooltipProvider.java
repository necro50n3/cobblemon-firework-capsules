package com.necro.fireworkcapsules.common.tooltip;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record StickerBookTooltipProvider(CompoundTag tag) implements TooltipComponent {}
