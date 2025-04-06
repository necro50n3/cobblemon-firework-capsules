package com.necro.fireworkcapsules.fabric.entities;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.entity.CapsuleEntity;
import com.necro.fireworkcapsules.common.entity.FireworkCapsuleEntities;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class FabricEntities {
    public static void register() {
        FireworkCapsuleEntities.CAPSULE_ENTITY = (Holder<EntityType<CapsuleEntity>>) (Object) Registry.registerForHolder(BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "capsule_entity"),
            EntityType.Builder.of(CapsuleEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build()
        );
    }
}
