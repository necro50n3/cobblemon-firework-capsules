package com.necro.fireworkcapsules.common.stickers;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public record StickerExplosion(ResourceLocation id, IntList colors, IntList fadeColors, boolean hasTrail, boolean hasTwinkle, String sound, StickerType type) implements TooltipProvider {
    public static final ResourceKey<Registry<StickerExplosion>> STICKERS = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("sticker", "explosion"));

    public StickerExplosion(ResourceLocation id, int color, String sound, StickerType type) {
        this(id, IntList.of(color), IntList.of(), false, false, sound, type);
    }

    public StickerExplosion(ResourceLocation id, int color, StickerType type) {
        this(id, IntList.of(color), IntList.of(), false, false, "", type);
    }

    public static StickerExplosion fromFireworks(FireworkExplosion explosion) {
        ResourceLocation id = switch (explosion.shape()) {
            case FireworkExplosion.Shape.SMALL_BALL -> ResourceLocation.withDefaultNamespace("small_ball");
            case FireworkExplosion.Shape.LARGE_BALL -> ResourceLocation.withDefaultNamespace("large_ball");
            case FireworkExplosion.Shape.STAR -> ResourceLocation.withDefaultNamespace("star");
            case FireworkExplosion.Shape.CREEPER -> ResourceLocation.withDefaultNamespace("creeper");
            case FireworkExplosion.Shape.BURST -> ResourceLocation.withDefaultNamespace("burst");
        };
        return new StickerExplosion(id, explosion.colors(), explosion.fadeColors(), explosion.hasTrail(), explosion.hasTwinkle(), "", StickerType.FIREWORK);
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        this.addParticleNameTooltip(consumer);
        this.addAdditionalTooltip(consumer);
    }

    public void addParticleNameTooltip(Consumer<Component> consumer) {
        MutableComponent name = this.getName();
        if (this.colors.isEmpty()) name = name.withStyle(ChatFormatting.GRAY);
        else name = name.withColor(this.isTooDark(this.color()) ? 11184810 : this.color());
        consumer.accept(name);
    }

    private boolean isTooDark(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int distanceSquared = r*r + g*g + b*b;
        return distanceSquared < 5000;
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

    private int color() {
        return this.colors.getFirst();
    }

    public SoundEvent createSound() {
        if (this.sound == null || this.sound.isEmpty()) return null;
        return SoundEvent.createVariableRangeEvent(ResourceLocation.parse(this.sound));
    }

    public FireworkExplosion toFirework() {
        if (this.type() != StickerType.FIREWORK) return null;

        FireworkExplosion.Shape shape = null;
        if (this.id().equals(ResourceLocation.withDefaultNamespace("small_ball"))) shape = FireworkExplosion.Shape.SMALL_BALL;
        else if (this.id().equals(ResourceLocation.withDefaultNamespace("large_ball"))) shape = FireworkExplosion.Shape.LARGE_BALL;
        else if (this.id().equals(ResourceLocation.withDefaultNamespace("star"))) shape = FireworkExplosion.Shape.STAR;
        else if (this.id().equals(ResourceLocation.withDefaultNamespace("creeper"))) shape = FireworkExplosion.Shape.CREEPER;
        else if (this.id().equals(ResourceLocation.withDefaultNamespace("burst"))) shape = FireworkExplosion.Shape.BURST;
        if (shape == null) return null;

        return new FireworkExplosion(shape, this.colors(), this.fadeColors(), this.hasTrail(), this.hasTwinkle());
    }

    @Override
    public @NotNull String toString() {
        String string = "id=" + this.id().toString() + " colors=" + this.colors().toString();
        if (!this.fadeColors().isEmpty()) string += " fadeColors=" + this.fadeColors();
        string += " hasTrail=" + this.hasTrail() + " hasTwinkle=" + this.hasTwinkle();
        if (!this.sound().isBlank()) string += " sound=" + this.sound();
        return string;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StickerExplosion sticker)) return false;
        return this.id().equals(sticker.id());
    }

    public static final Codec<StickerExplosion> DIRECT_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(StickerExplosion::id),
            FireworkExplosion.COLOR_LIST_CODEC.fieldOf("colors").forGetter(StickerExplosion::colors),
            FireworkExplosion.COLOR_LIST_CODEC.fieldOf("fade_colors").orElse(IntList.of()).forGetter(StickerExplosion::fadeColors),
            Codec.BOOL.fieldOf("has_trail").orElse(false).forGetter(StickerExplosion::hasTrail),
            Codec.BOOL.fieldOf("has_twinkle").orElse(false).forGetter(StickerExplosion::hasTwinkle),
            Codec.STRING.fieldOf("sound").orElse("").forGetter(StickerExplosion::sound),
            StickerType.CODEC.fieldOf("type").orElse(StickerType.BEDROCK).forGetter(StickerExplosion::type))
        .apply(instance, StickerExplosion::new)
    );

    public static final Codec<StickerExplosion> SIMPLE_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(StickerExplosion::id),
            Codec.INT.fieldOf("color").orElse(-1).forGetter(StickerExplosion::color),
            Codec.STRING.fieldOf("sound").orElse("").forGetter(StickerExplosion::sound),
            StickerType.CODEC.fieldOf("type").orElse(StickerType.BEDROCK).forGetter(StickerExplosion::type))
        .apply(instance, StickerExplosion::new)
    );

    public static final Codec<StickerExplosion> CODEC = Codec.either(DIRECT_CODEC, SIMPLE_CODEC)
        .xmap(either -> either.map(direct -> direct, simple -> simple), Either::left);
}
