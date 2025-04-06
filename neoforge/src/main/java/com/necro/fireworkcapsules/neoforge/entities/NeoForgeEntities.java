package com.necro.fireworkcapsules.neoforge.entities;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.entity.CapsuleEntity;
import com.necro.fireworkcapsules.common.entity.FireworkCapsuleEntities;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, FireworkCapsules.MOD_ID);

    public static void register() {
        FireworkCapsuleEntities.CAPSULE_ENTITY = (Holder<EntityType<CapsuleEntity>>) (Object) ENTITY_TYPES.register("capsule_entity",
            () -> EntityType.Builder.of(CapsuleEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("capsule_entity")
        );
    }
}
