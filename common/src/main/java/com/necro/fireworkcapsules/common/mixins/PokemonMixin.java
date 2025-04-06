package com.necro.fireworkcapsules.common.mixins;

import com.cobblemon.mod.common.api.reactive.SimpleObservable;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import com.necro.fireworkcapsules.common.util.ICapsuleHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Pokemon.class)
public abstract class PokemonMixin implements ICapsuleHolder {
    @Shadow(remap = false)
    public abstract CompoundTag getPersistentData();

    @Shadow(remap = false)
    public abstract SimpleObservable<Pokemon> getAnyChangeObservable();

    @Unique
    private ItemStack capsule;

    @Override
    public void setCapsule(HolderLookup.Provider provider, ItemStack itemStack) {
        if (!itemStack.is(FireworkCapsuleItems.BALL_CAPSULE) && !itemStack.isEmpty()) return;
        this.capsule = itemStack;
        if (!this.capsule.isEmpty()) this.getPersistentData().put("firework_capsule", this.capsule.save(provider));
        else this.getPersistentData().remove("firework_capsule");
        this.getAnyChangeObservable().emit((Pokemon) (Object) this);
    }

    @Override
    public ItemStack getCapsule(HolderLookup.Provider provider) {
        if (this.capsule == null && this.getPersistentData().contains("firework_capsule")) {
            this.capsule = ItemStack.parseOptional(provider, this.getPersistentData().getCompound("firework_capsule"));
        }
        if (this.capsule == null) this.capsule = ItemStack.EMPTY;
        return this.capsule;
    }
}
