package com.necro.fireworkcapsules.common.stickers;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.List;
import java.util.function.Consumer;

public record Stickers(List<StickerExplosion> explosions) implements TooltipProvider {
    public static final Codec<Stickers> CODEC = StickerExplosion.CODEC.sizeLimitedListOf(256).xmap(Stickers::new, Stickers::explosions);

    public Stickers(List<StickerExplosion> explosions) {
        if (explosions.size() > 256) {
            throw new IllegalArgumentException("Got " + explosions.size() + " explosions, but maximum is 256");
        } else {
            this.explosions = explosions;
        }
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        for (StickerExplosion explosion : this.explosions) {
            explosion.addParticleNameTooltip(consumer);
            explosion.addAdditionalTooltip((component) ->
                consumer.accept(Component.literal("  ").append(component))
            );
        }
    }
}
