package com.necro.fireworkcapsules.common.particles.custom;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.util.CustomParticleFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class StickerParticle extends SimpleAnimatedParticle {
    protected float rotateSpeed;
    protected boolean isStatic;
    protected boolean trail;
    protected boolean twinkle;
    protected final ParticleEngine engine;
    protected float fadeR;
    protected float fadeG;
    protected float fadeB;
    protected boolean hasFade;

    StickerParticle(ClientLevel clientLevel, double x, double y, double z, double xd, double yd, double zd, ParticleEngine particleEngine, SpriteSet spriteSet) {
        super(clientLevel, x, y, z, spriteSet, 0f);
        this.setSize(0.02f, 0.02f);
        this.lifetime = 10 + this.random.nextInt(30);
        this.rotateSpeed = 0f;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.engine = particleEngine;
        this.quadSize *= 0.75F;
        this.isStatic = false;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        if (this.rotateSpeed != 0f) {
            this.oRoll = this.roll;
            this.roll += 3.1415927f * this.rotateSpeed;
        }
        super.tick();
        if (this.trail && this.age < this.lifetime / 2 && (this.age + this.lifetime) % 2 == 0) {
            StickerParticle stickerParticle = (StickerParticle) this.createTrail();
            stickerParticle.setAlpha(0.99F);
            stickerParticle.setColor(this.rCol, this.gCol, this.bCol);
            stickerParticle.age = stickerParticle.lifetime / 2;
            stickerParticle.gravity = this.gravity;
            if (this.hasFade) {
                stickerParticle.hasFade = true;
                stickerParticle.fadeR = this.fadeR;
                stickerParticle.fadeG = this.fadeG;
                stickerParticle.fadeB = this.fadeB;
            }

            stickerParticle.twinkle = this.twinkle;
            this.engine.add(stickerParticle);
        }
    }

    protected Particle createTrail() {
        double xd = (this.random.nextDouble() - 0.5) * 0.1;
        double yd = (this.random.nextDouble() - 0.5) * 0.1;
        double zd = (this.random.nextDouble() - 0.5) * 0.1;

        return new StickerParticle(this.level, this.x, this.y, this.z, xd, yd, zd, this.engine, this.sprites);
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
        if (!this.twinkle || this.age < this.lifetime / 3 || (this.age + this.lifetime) / 3 % 2 == 0) {
            super.render(vertexConsumer, camera, f);
        }

    }

    @Override
    public void setSpriteFromAge(SpriteSet spriteSet) {
        if (!this.isStatic) super.setSpriteFromAge(spriteSet);
    }

    public void setRotateSpeed(double rotateSpeedMulti) {
        this.rotateSpeed = (this.random.nextFloat() - 0.5f) * 0.05f * (float) rotateSpeedMulti;
        if (this.rotateSpeed < 0) this.rotateSpeed = Math.min(this.rotateSpeed, -0.01f * (float) rotateSpeedMulti);
        else this.rotateSpeed = Math.max(this.rotateSpeed, 0.01f * (float) rotateSpeedMulti);
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
        if (isStatic) this.pickSprite(this.sprites);
    }

    public void setTrail(boolean trail) {
        this.trail = trail;
    }

    public void setTwinkle(boolean twinkle) {
        this.twinkle = twinkle;
    }

    public static CustomParticleFunction getConsumer(ParticleOptions particle) {
        return getConsumer(particle, 0.0);
    }

    public static CustomParticleFunction getConsumer(ParticleOptions particle, double rotateSpeedMulti) {
        return getConsumer(particle, rotateSpeedMulti, 0f);
    }

    public static CustomParticleFunction getConsumer(ParticleOptions particle, float gravity) {
        return getConsumer(particle, 0.0, gravity);
    }

    public static CustomParticleFunction getConsumer(ParticleOptions particle, double rotateSpeedMulti, float gravity) {
        return (clientLevel, x, y, z, rot, particleEngine, explosion, scale, scaleFactor) ->
            particleEngine.add(new Starter(particle, clientLevel, x, y, z, 0, 0, 0, gravity, particleEngine, explosion, scale, scaleFactor, rotateSpeedMulti));
    }

    @Environment(EnvType.CLIENT)
    public static class Starter extends NoRenderParticle {
        protected final ParticleOptions particle;
        protected final ParticleEngine engine;
        protected final StickerExplosion explosion;
        protected final float gravity;
        protected final float scale;
        protected final double scaleFactor;
        protected final double rotateSpeedMulti;
        protected int life;

        public Starter(ParticleOptions particle, ClientLevel clientLevel, double x, double y, double z, double xd, double yd, double zd, float gravity, ParticleEngine particleEngine, StickerExplosion explosion, float scale, double scaleFactor, double rotateSpeedMulti) {
            super(clientLevel, x, y, z, xd, yd, zd);
            this.particle = particle;
            this.engine = particleEngine;
            this.explosion = explosion;
            this.gravity = gravity;
            this.scale = scale;
            this.scaleFactor = Math.max(scaleFactor, 1.0);
            this.rotateSpeedMulti = rotateSpeedMulti;
            this.life = 0;
        }

        @Override
        public void tick() {
            for (int i = 0; i < (4 / this.scaleFactor); i++) {
                double xn = (this.random.nextDouble() - 0.5) * 1.5 * this.scale;
                double yn = (this.random.nextDouble() - 0.5) * 1.5 * this.scale;
                double zn = (this.random.nextDouble() - 0.5) * 1.5 * this.scale;

                Vec3 nd = new Vec3(xn, yn, zn).normalize();
                Vec3 n2 = nd.scale(this.scale / 3);

                double x2 = this.x + xn + n2.x();
                double y2 = this.y + yn + n2.y();
                double z2 = this.z + zn + n2.z();

                double xd = nd.x() * 0.01;
                double yd = nd.y() * 0.01;
                double zd = nd.z() * 0.01;

                this.createParticle(x2, y2, z2, xd, yd, zd);
            }

            if (this.life++ > 4) this.remove();
        }

        public void createParticle(double x, double y, double z, double xd, double yd, double zd) {
            StickerParticle stickerParticle = (StickerParticle) this.engine.createParticle(this.particle, x, y, z, xd, yd, zd);
            if (stickerParticle == null) return;

            stickerParticle.setTrail(this.explosion.hasTrail());
            stickerParticle.setTwinkle(this.explosion.hasTwinkle());
            stickerParticle.setAlpha(0.99F);
            stickerParticle.scale((float) Math.sqrt(this.scale));
            stickerParticle.setRotateSpeed(this.rotateSpeedMulti);
            stickerParticle.gravity = this.gravity;
            if (!this.explosion.fadeColors().isEmpty()) {
                stickerParticle.setFadeColor(Util.getRandom(this.explosion.fadeColors(), this.random));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class StickerProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public StickerProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            StickerParticle stickerParticle = new StickerParticle(clientLevel, d, e, f, g, h, i, Minecraft.getInstance().particleEngine, this.sprites);
            stickerParticle.setAlpha(0.99F);
            return stickerParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class ColoredStickerProvider extends StickerProvider {
        private final int color;

        public ColoredStickerProvider(SpriteSet spriteSet, int color) {
            super(spriteSet);
            this.color = color;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            StickerParticle stickerParticle = (StickerParticle) super.createParticle(simpleParticleType, clientLevel, d, e, f, g, h, i);
            stickerParticle.setColor(this.color);
            return stickerParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class VariedStickerProvider extends StickerProvider {
        private final RandomSource random;

        public VariedStickerProvider(SpriteSet spriteSet) {
            super(spriteSet);
            this.random = RandomSource.create();
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            StickerParticle stickerParticle = (StickerParticle) super.createParticle(simpleParticleType, clientLevel, d, e, f, g, h, i);
            stickerParticle.setSprite(stickerParticle.sprites.get(this.random));
            stickerParticle.setStatic(true);
            return stickerParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class VariedColoredStickerProvider extends ColoredStickerProvider {
        private final RandomSource random;

        public VariedColoredStickerProvider(SpriteSet spriteSet, int color) {
            super(spriteSet, color);
            this.random = RandomSource.create();
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            StickerParticle stickerParticle = (StickerParticle) super.createParticle(simpleParticleType, clientLevel, d, e, f, g, h, i);
            stickerParticle.setSprite(stickerParticle.sprites.get(this.random));
            stickerParticle.setStatic(true);
            return stickerParticle;
        }
    }
}
