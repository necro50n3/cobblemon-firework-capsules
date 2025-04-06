package com.necro.fireworkcapsules.common.particles.custom;

import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.util.CustomParticleFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class FlashParticle extends StickerParticle {
    FlashParticle(ClientLevel clientLevel, double x, double y, double z, double xd, double yd, double zd, ParticleEngine particleEngine, SpriteSet spriteSet) {
        super(clientLevel, x, y, z, xd, yd, zd, particleEngine, spriteSet);
        this.lifetime = 5 + this.random.nextInt(0, 5);
    }

    @Override
    protected Particle createTrail() {
        double xd = (this.random.nextDouble() - 0.5) * 0.1;
        double yd = (this.random.nextDouble() - 0.5) * 0.1;
        double zd = (this.random.nextDouble() - 0.5) * 0.1;

        return new FlashParticle(this.level, this.x, this.y, this.z, xd, yd, zd, this.engine, this.sprites);
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
    public static class Starter extends StickerParticle.Starter {
        public Starter(ParticleOptions particle, ClientLevel clientLevel, double x, double y, double z, double xd, double yd, double zd, float gravity, ParticleEngine particleEngine, StickerExplosion explosion, float scale, double scaleFactor, double rotateSpeedMulti) {
            super(particle, clientLevel, x, y, z, xd, yd, zd, gravity, particleEngine, explosion, scale, scaleFactor, rotateSpeedMulti);
        }

        public void createParticle(double x, double y, double z, double xd, double yd, double zd) {
            FlashParticle flashParticle = (FlashParticle) this.engine.createParticle(this.particle, x, y, z, xd, yd, zd);
            if (flashParticle == null) return;

            flashParticle.setTrail(this.explosion.hasTrail());
            flashParticle.setTwinkle(this.explosion.hasTwinkle());
            flashParticle.setAlpha(0.99F);
            flashParticle.scale((float) Math.sqrt(this.scale));
            flashParticle.setRotateSpeed(this.rotateSpeedMulti);
            flashParticle.gravity = this.gravity;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class FlashProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public FlashProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            FlashParticle flashParticle = new FlashParticle(clientLevel, d, e, f, g, h, i, Minecraft.getInstance().particleEngine, this.sprites);
            flashParticle.setAlpha(0.99F);
            return flashParticle;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class VariedFlashProvider extends FlashProvider {
        private final RandomSource random;

        public VariedFlashProvider(SpriteSet spriteSet) {
            super(spriteSet);
            this.random = RandomSource.create();
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            FlashParticle flashParticle = (FlashParticle) super.createParticle(simpleParticleType, clientLevel, d, e, f, g, h, i);
            flashParticle.setSprite(flashParticle.sprites.get(this.random));
            flashParticle.setStatic(true);
            return flashParticle;
        }
    }
}
