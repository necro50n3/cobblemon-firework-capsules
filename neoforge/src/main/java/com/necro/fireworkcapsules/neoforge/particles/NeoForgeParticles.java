package com.necro.fireworkcapsules.neoforge.particles;

import com.necro.fireworkcapsules.common.particles.FireworkCapsuleParticles;
import net.minecraft.core.particles.SimpleParticleType;

public class NeoForgeParticles {
    public static void register() {
        FireworkCapsuleParticles.CAPSULE_PARTICLE = new SimpleParticleType(false);
    }
}
