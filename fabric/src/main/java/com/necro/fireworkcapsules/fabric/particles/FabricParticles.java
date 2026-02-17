package com.necro.fireworkcapsules.fabric.particles;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.particles.FireworkCapsuleParticles;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class FabricParticles {
    public static void register() {
        FireworkCapsuleParticles.CAPSULE_PARTICLE = registerParticle("capsule_particle");
    }

    private static SimpleParticleType registerParticle(String name) {
        SimpleParticleType particleType = FabricParticleTypes.simple();
        Registry.registerForHolder(BuiltInRegistries.PARTICLE_TYPE,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, name),
            particleType
        );
        return particleType;
    }
}
