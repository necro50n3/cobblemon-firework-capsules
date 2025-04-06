package com.necro.fireworkcapsules.common.entity;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.stickers.Stickers;
import com.necro.fireworkcapsules.common.util.IParticleCreator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.OptionalInt;

public class CapsuleEntity extends Entity implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> DATA_ID_CAPSULE_ITEM = SynchedEntityData.defineId(CapsuleEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<OptionalInt> DATA_OWNER_ENTITY = SynchedEntityData.defineId(CapsuleEntity.class, EntityDataSerializers.OPTIONAL_UNSIGNED_INT);

    private PokemonEntity owner;

    public CapsuleEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public CapsuleEntity(Level level, double x, double y, double z, ItemStack itemStack, PokemonEntity entity) {
        this(FireworkCapsuleEntities.CAPSULE_ENTITY.value(), level);
        this.owner = entity;
        this.setPos(x, y, z);
        this.entityData.set(DATA_ID_CAPSULE_ITEM, itemStack.copy());
        this.entityData.set(DATA_OWNER_ENTITY, OptionalInt.of(entity.getId()));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ID_CAPSULE_ITEM, getDefaultItem());
        builder.define(DATA_OWNER_ENTITY, OptionalInt.empty());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("CapsuleItem", 10)) {
            this.entityData.set(DATA_ID_CAPSULE_ITEM, ItemStack.parse(this.registryAccess(), compoundTag.getCompound("CapsuleItem")).orElseGet(CapsuleEntity::getDefaultItem));
        }
        else {
            this.entityData.set(DATA_ID_CAPSULE_ITEM, getDefaultItem());
        }

        if (compoundTag.contains("EntityId")) {
            this.entityData.set(DATA_OWNER_ENTITY, OptionalInt.of(compoundTag.getInt("EntityId")));
            this.owner = (PokemonEntity) this.level().getEntity(compoundTag.getInt("EntityId"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.put("CapsuleItem", this.getItem().save(this.registryAccess()));
        if (this.owner != null) compoundTag.putInt("EntityId", this.owner.getId());
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 17 && this.level().isClientSide) {
            OptionalInt entityId = this.entityData.get(DATA_OWNER_ENTITY);
            if (entityId.isEmpty()) return;
            Entity entity = this.level().getEntity(entityId.getAsInt());
            if (!(entity instanceof PokemonEntity pokemonEntity)) return;
            float scale = this.getScale(pokemonEntity);
            ((IParticleCreator) this.level()).createCapsuleParticles(this.getX(), this.getY(), this.getZ(), pokemonEntity.yBodyRot, this.getExplosions(), scale);
        }

        super.handleEntityEvent(b);
    }

    private List<StickerExplosion> getExplosions() {
        ItemStack itemStack = this.entityData.get(DATA_ID_CAPSULE_ITEM);
        Stickers stickers = itemStack.get(FireworkCapsuleComponents.STICKERS.value());
        return stickers != null ? stickers.explosions() : List.of();
    }

    @Override
    public @NotNull ItemStack getItem() {
        return this.entityData.get(DATA_ID_CAPSULE_ITEM);
    }

    private static ItemStack getDefaultItem() {
        return new ItemStack(FireworkCapsuleItems.BALL_CAPSULE);
    }

    public void explode() {
        this.level().broadcastEntityEvent(this, (byte)17);
        this.discard();
    }

    private float getScale(PokemonEntity entity) {
        AABB boundingBox = entity.getBoundingBox();
        return (float) Math.pow((boundingBox.getXsize() + boundingBox.getYsize() + boundingBox.getZsize()) / 3.0f, 0.6);
    }
}
