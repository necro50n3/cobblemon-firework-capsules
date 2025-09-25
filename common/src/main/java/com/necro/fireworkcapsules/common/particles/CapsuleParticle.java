package com.necro.fireworkcapsules.common.particles;

import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.util.CustomParticleFunction;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class CapsuleParticle extends FireworkParticles {
    private static final Map<ResourceLocation, CustomParticleFunction> EXPLOSION_MAP = new HashMap<>();

    public CapsuleParticle() {}

    public static void register(ResourceLocation id, CustomParticleFunction consumer) {
        EXPLOSION_MAP.put(id, consumer);
    }

    @Environment(EnvType.CLIENT)
    public static class Starter extends NoRenderParticle {
        private int life;
        private final float rot;
        private final ParticleEngine engine;
        private final List<StickerExplosion> explosions;
        protected final float scale;
        protected final double scaleFactor;

        public Starter(ClientLevel clientLevel, double d, double e, double f, float rot, ParticleEngine particleEngine, List<StickerExplosion> list, float scale) {
            super(clientLevel, d, e, f, 0, 0, 0);
            this.life = 0;
            this.rot = rot;
            this.engine = particleEngine;
            this.scale = scale;
            if (scale < 1.0f) this.scaleFactor = 2.0;
            else if (scale < 2.0f) this.scaleFactor = 1.0;
            else if (scale < 3.0f) this.scaleFactor = 0.5;
            else this.scaleFactor = 0.25;

            if (list.isEmpty()) {
                throw new IllegalArgumentException("Cannot create capsule starter with no explosions");
            } else {
                this.explosions = list;
                this.lifetime = list.size() * 2 - 1;
                if (this.explosions.stream().anyMatch(StickerExplosion::hasTwinkle)) this.lifetime += 15;
            }
        }

        @Override
        public void tick() {
            if (this.life % 2 == 0 && this.life / 2 < this.explosions.size()) {
                StickerExplosion explosion = this.explosions.get(this.life / 2);
                boolean hasTrail = explosion.hasTrail();
                boolean hasTwinkle = explosion.hasTwinkle();
                IntList colors = explosion.colors();
                IntList fadeColors = explosion.fadeColors();
                if (colors.isEmpty()) {
                    colors = IntList.of(DyeColor.BLACK.getFireworkColor());
                }

                if (explosion.id().getNamespace().equals("minecraft")) {
                    switch (explosion.id().getPath()) {
                        case "small_ball" -> this.createParticleBall(0.2, 2, colors, fadeColors, hasTrail, hasTwinkle);
                        case "large_ball" -> this.createParticleBall(0.25, 3, colors, fadeColors, hasTrail, hasTwinkle);
                        case "star" -> this.createParticleShape(0.3, FireworkParticles.Starter.STAR_PARTICLE_COORDS, colors, fadeColors, hasTrail, hasTwinkle, false);
                        case "creeper" -> this.createParticleShape(0.3, FireworkParticles.Starter.CREEPER_PARTICLE_COORDS, colors, fadeColors, hasTrail, hasTwinkle, true);
                        case "burst" -> this.createParticleBurst(colors, fadeColors, hasTrail, hasTwinkle);
                        default -> {}
                    }
                }
                else {
                    CustomParticleFunction explosionParticle = EXPLOSION_MAP.get(explosion.id());
                    if (explosionParticle != null) explosionParticle.accept(this.level, this.x, this.y, this.z, this.rot, this.engine, explosion, this.scale, this.scaleFactor);
                }

                int colorValue = colors.getInt(0);
                Particle particle = this.engine.createParticle(ParticleTypes.FLASH, this.x, this.y, this.z, 0.0, 0.0, 0.0);
                if (particle != null) particle.setColor((float) FastColor.ARGB32.red(colorValue) / 255.0F, (float) FastColor.ARGB32.green(colorValue) / 255.0F, (float) FastColor.ARGB32.blue(colorValue) / 255.0F);
            }

            if (++this.life > this.lifetime) this.remove();
        }

        public void createParticle(double d, double e, double f, double g, double h, double i, IntList colors, IntList fadeColors, boolean hasTrail, boolean hasTwinkle) {
            SparkParticle sparkParticle = (SparkParticle)this.engine.createParticle(ParticleTypes.FIREWORK, d, e, f, g, h, i);
            if (sparkParticle == null) return;

            sparkParticle.setTrail(hasTrail);
            sparkParticle.setTwinkle(hasTwinkle);
            sparkParticle.setAlpha(0.99F);
            sparkParticle.setColor(Util.getRandom(colors, this.random));
            if (!fadeColors.isEmpty()) {
                sparkParticle.setFadeColor(Util.getRandom(fadeColors, this.random));
            }
        }

        public void createParticleBall(double d, int i, IntList colors, IntList fadeColors, boolean hasTrail, boolean hasTwinkle) {
            d *= this.scale;

            for(double j = -i; j <= i; j += this.scaleFactor) {
                for(double k = -i; k <= i; k += this.scaleFactor) {
                    for(double l = -i; l <= i; l += this.scaleFactor) {
                        double h = k + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                        double m = j + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                        double n = l + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                        double o = Math.sqrt(h * h + m * m + n * n) / d + this.random.nextGaussian() * 0.05;
                        this.createParticle(this.x, this.y, this.z, h / o, m / o, n / o, colors, fadeColors, hasTrail, hasTwinkle);
                        if (j != -i && j != i && k != -i && k != i) {
                            l += i * 2 - 1;
                        }
                    }
                }
            }
        }

        public void createParticleShape(double d, double[][] ds, IntList colors, IntList fadeColors, boolean hasTrail, boolean hasTwinkle, boolean bl3) {
            d *= this.scale;
            double e = ds[0][0];
            double f = ds[0][1];
            this.createParticle(this.x, this.y, this.z, e * d, f * d, 0.0, colors, fadeColors, hasTrail, hasTwinkle);
            float g = this.random.nextFloat() * 3.1415927F;
            double h = bl3 ? 0.034 : 0.34;

            for(int i = 0; i < 3; ++i) {
                double j = (double)g + (double)((float)i * 3.1415927F) * h;
                double k = e;
                double l = f;

                for(int m = 1; m < ds.length; ++m) {
                    double n = ds[m][0];
                    double o = ds[m][1];

                    for(double p = 0.25; p <= 1.0; p += 0.25 * this.scaleFactor) {
                        double q = Mth.lerp(p, k, n) * d;
                        double r = Mth.lerp(p, l, o) * d;
                        double s = q * Math.sin(j);
                        q *= Math.cos(j);

                        for(double t = -1.0; t <= 1.0; t += 2.0) {
                            this.createParticle(this.x, this.y, this.z, q * t, r, s * t, colors, fadeColors, hasTrail, hasTwinkle);
                        }
                    }

                    k = n;
                    l = o;
                }
            }

        }

        public void createParticleBurst(IntList colors, IntList fadeColors, boolean hasTrail, boolean hasTwinkle) {
            double d = this.random.nextGaussian() * 0.05;
            double e = this.random.nextGaussian() * 0.05;

            for(int i = 0; i < (70 / this.scaleFactor); ++i) {
                double f = (this.xd * 0.5 + this.random.nextGaussian() * 0.15 + d) * this.scale * 0.5;
                double g = (this.zd * 0.5 + this.random.nextGaussian() * 0.15 + e) * this.scale * 0.5;
                double h = (this.yd * 0.5 + this.random.nextDouble() * 0.5) * this.scale * 0.5;
                this.createParticle(this.x, this.y, this.z, f, h, g, colors, fadeColors, hasTrail, hasTwinkle);
            }

        }
    }
}