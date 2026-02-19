package com.necro.fireworkcapsules.common.particles;

import com.cobblemon.mod.common.client.net.effect.SpawnSnowstormEntityParticleHandler;
import com.cobblemon.mod.common.net.messages.client.effect.SpawnSnowstormEntityParticlePacket;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.stickers.StickerType;
import com.necro.fireworkcapsules.common.util.CustomParticleFunction;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class CapsuleParticle extends FireworkParticles {
    private static final Map<ResourceLocation, CustomParticleFunction> CUSTOM_PARTICLE_MAP = new HashMap<>();

    public static void register(ResourceLocation sticker, CustomParticleFunction function) {
        CUSTOM_PARTICLE_MAP.put(sticker, function);
    }

    @Environment(EnvType.CLIENT)
    public static class Starter extends NoRenderParticle {
        private int life;
        private final int entityId;
        private final ParticleEngine engine;
        private final List<StickerExplosion> stickers;
        protected final float scale;
        protected final double scaleFactor;

        public Starter(ClientLevel level, double d, double e, double f, int entityId, ParticleEngine engine, List<StickerExplosion> list, float scale) {
            super(level, d, e, f, 0, 0, 0);
            this.life = 0;
            this.entityId = entityId;
            this.engine = engine;
            this.scale = scale;
            if (scale < 1.0f) this.scaleFactor = 2.0;
            else if (scale < 2.0f) this.scaleFactor = 1.0;
            else if (scale < 3.0f) this.scaleFactor = 0.5;
            else this.scaleFactor = 0.25;

            if (list.isEmpty()) {
                throw new IllegalArgumentException("Cannot create capsule starter with no stickers");
            } else {
                this.stickers = list;
                this.lifetime = list.size() * 2 - 1;
                if (this.stickers.stream().anyMatch(StickerExplosion::hasTwinkle)) this.lifetime += 15;
            }
        }

        @Override
        public void tick() {
            if (this.life % 2 == 0 && this.life / 2 < this.stickers.size()) {
                StickerExplosion sticker = this.stickers.get(this.life / 2);
                boolean hasTrail = sticker.hasTrail();
                boolean hasTwinkle = sticker.hasTwinkle();
                IntList colors = sticker.colors();
                IntList fadeColors = sticker.fadeColors();
                if (colors.isEmpty()) {
                    colors = IntList.of(DyeColor.BLACK.getFireworkColor());
                }

                if (sticker.type() == StickerType.FIREWORK) {
                    if (sticker.id().getNamespace().equals("minecraft")) {
                        switch (sticker.id().getPath()) {
                            case "small_ball" -> this.createParticleBall(0.2, 2, colors, fadeColors, hasTrail, hasTwinkle);
                            case "large_ball" -> this.createParticleBall(0.25, 3, colors, fadeColors, hasTrail, hasTwinkle);
                            case "star" -> this.createParticleShape(0.3, FireworkParticles.Starter.STAR_PARTICLE_COORDS, colors, fadeColors, hasTrail, hasTwinkle, false);
                            case "creeper" -> this.createParticleShape(0.3, FireworkParticles.Starter.CREEPER_PARTICLE_COORDS, colors, fadeColors, hasTrail, hasTwinkle, true);
                            case "burst" -> this.createParticleBurst(colors, fadeColors, hasTrail, hasTwinkle);
                            default -> {}
                        }
                    }

                    int colorValue = colors.getInt(0);
                    Particle particle = this.engine.createParticle(ParticleTypes.FLASH, this.x, this.y, this.z, 0.0, 0.0, 0.0);
                    if (particle != null) particle.setColor((float) FastColor.ARGB32.red(colorValue) / 255.0F, (float) FastColor.ARGB32.green(colorValue) / 255.0F, (float) FastColor.ARGB32.blue(colorValue) / 255.0F);
                }
                else if (sticker.type() == StickerType.BEDROCK) {
                    SpawnSnowstormEntityParticlePacket packet = new SpawnSnowstormEntityParticlePacket(sticker.id(), this.entityId, List.of("root"), null, null);
                    SpawnSnowstormEntityParticleHandler.INSTANCE.handle(packet, Minecraft.getInstance());
                }
                else if (sticker.type() == StickerType.CUSTOM) {
                    CustomParticleFunction function = CUSTOM_PARTICLE_MAP.get(sticker.id());
                    if (function != null) function.create(this.level, this.x, this.y, this.z, this.entityId, this.engine, sticker, this.scale);
                }
                SoundEvent sound = sticker.createSound();
                if (sound != null) this.level.playLocalSound(this.x, this.y, this.z, sound, SoundSource.AMBIENT, 1.0F, 0.95F + this.random.nextFloat() * 0.1F, true);
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