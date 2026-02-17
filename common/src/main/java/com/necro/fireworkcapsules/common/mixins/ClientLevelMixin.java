package com.necro.fireworkcapsules.common.mixins;

import com.necro.fireworkcapsules.common.particles.CapsuleParticle;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.util.IParticleCreator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(ClientLevel.class)
public class ClientLevelMixin implements IParticleCreator {
    @Override
    public void fc_createCapsuleParticles(double x, double y, double z, int entityId, List<StickerExplosion> stickers, float scale) {
        if (!stickers.isEmpty()) {
            Minecraft.getInstance().particleEngine.add(new CapsuleParticle.Starter(
                (ClientLevel) (Object) this, x, y, z, entityId, Minecraft.getInstance().particleEngine, stickers, scale
            ));
        }
    }
}
