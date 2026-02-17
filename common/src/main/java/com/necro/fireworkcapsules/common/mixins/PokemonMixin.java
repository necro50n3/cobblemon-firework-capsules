package com.necro.fireworkcapsules.common.mixins;

import com.cobblemon.mod.common.net.messages.client.PokemonUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import com.necro.fireworkcapsules.common.util.ICapsuleHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Pokemon.class)
public abstract class PokemonMixin implements ICapsuleHolder {
    @Shadow(remap = false)
    public abstract CompoundTag getPersistentData();

    @Shadow(remap = false)
    public abstract void onChange(@Nullable PokemonUpdatePacket<?> packet);

    @Unique
    private ItemStack fc_capsule;

    @Override
    public void fc_setCapsule(HolderLookup.Provider provider, ItemStack itemStack) {
        if (!itemStack.is(FireworkCapsuleItems.BALL_CAPSULE) && !itemStack.isEmpty()) return;
        this.fc_capsule = itemStack;
        if (!this.fc_capsule.isEmpty()) this.getPersistentData().put("firework_capsule", this.fc_capsule.save(provider));
        else this.getPersistentData().remove("firework_capsule");
        this.onChange(null);
    }

    @Override
    public ItemStack fc_getCapsule(HolderLookup.Provider provider) {
        if (this.fc_capsule == null && this.getPersistentData().contains("firework_capsule")) {
            this.fc_capsule = ItemStack.parseOptional(provider, this.getPersistentData().getCompound("firework_capsule"));
        }
        if (this.fc_capsule == null) this.fc_capsule = ItemStack.EMPTY;
        return this.fc_capsule;
    }
}
