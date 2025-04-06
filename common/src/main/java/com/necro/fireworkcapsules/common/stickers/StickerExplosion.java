package com.necro.fireworkcapsules.common.stickers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public record StickerExplosion(ResourceLocation id, IntList colors, IntList fadeColors, boolean hasTrail, boolean hasTwinkle) implements TooltipProvider {
    private static final StreamCodec<ByteBuf, IntList> COLOR_LIST_STREAM_CODEC = ByteBufCodecs.INT.apply(ByteBufCodecs.list()).map(IntArrayList::new, ArrayList::new);

    public static StickerExplosion fromFireworks(FireworkExplosion explosion) {
        ResourceLocation id = switch (explosion.shape()) {
            case FireworkExplosion.Shape.SMALL_BALL -> ResourceLocation.withDefaultNamespace("small_ball");
            case FireworkExplosion.Shape.LARGE_BALL -> ResourceLocation.withDefaultNamespace("large_ball");
            case FireworkExplosion.Shape.STAR -> ResourceLocation.withDefaultNamespace("star");
            case FireworkExplosion.Shape.CREEPER -> ResourceLocation.withDefaultNamespace("creeper");
            case FireworkExplosion.Shape.BURST -> ResourceLocation.withDefaultNamespace("burst");
        };
        return new StickerExplosion(id, explosion.colors(), explosion.fadeColors(), explosion.hasTrail(), explosion.hasTwinkle());
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        this.addParticleNameTooltip(consumer);
        this.addAdditionalTooltip(consumer);
    }

    public void addParticleNameTooltip(Consumer<Component> consumer) {
        consumer.accept(this.getName().withStyle(ChatFormatting.GRAY));
    }

    public void addAdditionalTooltip(Consumer<Component> consumer) {
        if (!this.colors.isEmpty()) {
            List<DyeColor> dyeColors = getDyeColors(this.colors);
            if (!dyeColors.isEmpty()) consumer.accept(appendColors(Component.empty().withStyle(ChatFormatting.GRAY), dyeColors));
        }

        if (!this.fadeColors.isEmpty()) {
            List<DyeColor> dyeColors = getDyeColors(this.fadeColors);
            if (!dyeColors.isEmpty()) consumer.accept(appendColors(Component.translatable("item.minecraft.firework_star.fade_to").append(CommonComponents.SPACE).withStyle(ChatFormatting.GRAY), dyeColors));
        }

        if (this.hasTrail) {
            consumer.accept(Component.translatable("item.minecraft.firework_star.trail").withStyle(ChatFormatting.GRAY));
        }

        if (this.hasTwinkle) {
            consumer.accept(Component.translatable("item.minecraft.firework_star.flicker").withStyle(ChatFormatting.GRAY));
        }
    }

    private static List<DyeColor> getDyeColors(IntList colors) {
        List<DyeColor> dyeColors = new ArrayList<>();
        for (int color: colors) {
            DyeColor dyeColor = DyeColor.byFireworkColor(color);
            if (dyeColor != null) dyeColors.add(dyeColor);
        }
        return dyeColors;
    }

    private static Component appendColors(MutableComponent mutableComponent, List<DyeColor> dyeColors) {
        for (int i = 0; i < dyeColors.size(); ++i) {
            if (i > 0) {
                mutableComponent.append(", ");
            }
            mutableComponent.append(Component.translatable("item.minecraft.firework_star." + dyeColors.get(i).getName()));
        }

        return mutableComponent;
    }

    private MutableComponent getName() {
        return Component.translatable(String.format("item.%s.sticker.id.%s", this.id().getNamespace(), this.id().getPath()));
    }

    public static final Codec<StickerExplosion> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(StickerExplosion::id),
            FireworkExplosion.COLOR_LIST_CODEC.optionalFieldOf("colors", IntList.of()).forGetter(StickerExplosion::colors),
            FireworkExplosion.COLOR_LIST_CODEC.optionalFieldOf("fade_colors", IntList.of()).forGetter(StickerExplosion::fadeColors),
            Codec.BOOL.optionalFieldOf("has_trail", false).forGetter(StickerExplosion::hasTrail),
            Codec.BOOL.optionalFieldOf("has_twinkle", false).forGetter(StickerExplosion::hasTwinkle))
        .apply(instance, StickerExplosion::new)
    );

    public static final StreamCodec<ByteBuf, StickerExplosion> STREAM_CODEC = StreamCodec.composite(
        ResourceLocation.STREAM_CODEC, StickerExplosion::id,
        COLOR_LIST_STREAM_CODEC, StickerExplosion::colors,
        COLOR_LIST_STREAM_CODEC, StickerExplosion::fadeColors,
        ByteBufCodecs.BOOL, StickerExplosion::hasTrail,
        ByteBufCodecs.BOOL, StickerExplosion::hasTwinkle,
        StickerExplosion::new
    );
}
