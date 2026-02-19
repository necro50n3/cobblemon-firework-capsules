package com.necro.fireworkcapsules.common.compat.rei;

import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.comparison.ItemComparatorRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class FireworkCapsulesREIPlugin implements REIClientPlugin {
    @Override
    public void registerItemComparators(ItemComparatorRegistry registry) {
        registry.registerComponents(FireworkCapsuleItems.STICKER.value());
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.BUG_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.DARK_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.DRAGON_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.ELECTRIC_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.FAIRY_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.FIGHTING_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.FIRE_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.FLYING_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.GHOST_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.GRASS_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.GROUND_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.ICE_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.NORMAL_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.POISON_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.PSYCHIC_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.ROCK_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.STEEL_STICKER.value()));
        registry.removeEntry(EntryStacks.of(FireworkCapsuleItems.WATER_STICKER.value()));
    }
}
