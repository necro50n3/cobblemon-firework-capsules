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

        FireworkCapsuleParticles.BUG_STICKER = registerParticle("bug_sticker");
        FireworkCapsuleParticles.DARK_STICKER = registerParticle("dark_sticker");
        FireworkCapsuleParticles.DRAGON_STICKER = registerParticle("dragon_sticker");
        FireworkCapsuleParticles.ELECTRIC_STICKER = registerParticle("electric_sticker");
        FireworkCapsuleParticles.FAIRY_STICKER = registerParticle("fairy_sticker");
        FireworkCapsuleParticles.FIGHTING_STICKER = registerParticle("fighting_sticker");
        FireworkCapsuleParticles.FIRE_STICKER = registerParticle("fire_sticker");
        FireworkCapsuleParticles.FLYING_STICKER = registerParticle("flying_sticker");
        FireworkCapsuleParticles.GHOST_STICKER = registerParticle("ghost_sticker");
        FireworkCapsuleParticles.GRASS_STICKER = registerParticle("grass_sticker");
        FireworkCapsuleParticles.GROUND_STICKER = registerParticle("ground_sticker");
        FireworkCapsuleParticles.ICE_STICKER = registerParticle("ice_sticker");
        FireworkCapsuleParticles.NORMAL_STICKER = registerParticle("normal_sticker");
        FireworkCapsuleParticles.POISON_STICKER = registerParticle("poison_sticker");
        FireworkCapsuleParticles.PSYCHIC_STICKER = registerParticle("psychic_sticker");
        FireworkCapsuleParticles.STEEL_STICKER = registerParticle("steel_sticker");
        FireworkCapsuleParticles.ROCK_STICKER = registerParticle("rock_sticker");
        FireworkCapsuleParticles.WATER_STICKER = registerParticle("water_sticker");
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
