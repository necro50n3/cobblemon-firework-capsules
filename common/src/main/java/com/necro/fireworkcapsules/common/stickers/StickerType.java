package com.necro.fireworkcapsules.common.stickers;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum StickerType implements StringRepresentable {
    FIREWORKS("fireworks"),
    BEDROCK("bedrock"),
    CUSTOM("custom");

    public static final Codec<StickerType> CODEC = Codec.STRING.xmap(StickerType::fromString, Enum::name);
    private final String id;

    StickerType(String id) {
        this.id = id;
    }

    public static StickerType fromString(String name) {
        try { return valueOf(name); }
        catch (IllegalArgumentException e) { return BEDROCK; }
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.id;
    }
}
