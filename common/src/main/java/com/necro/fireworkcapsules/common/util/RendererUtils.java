package com.necro.fireworkcapsules.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class RendererUtils {
    public static String shortenCount(long count) {
        if (count < 1_000) return Long.toString(count);

        final String[] suffixes = {"k", "M", "G"};
        final long[] values = {1_000L, 1_000_000L, 1_000_000_000L};

        for (int i = values.length - 1; i >= 0; i--) {
            if (count >= values[i]) {
                double value = (double) count / values[i];
                double rounded = Math.round(value * 10.0) / 10.0;

                if (rounded == (long) rounded) return String.format("%d%s", (long) rounded, suffixes[i]);
                else return String.format("%.1f%s", rounded, suffixes[i]);
            }
        }
        return Long.toString(count);
    }

    public static int getRandomVariant(BlockPos blockPos, Direction direction) {
        int variantCount = 3;
        long seed = blockPos.asLong() ^ direction.hashCode();
        seed ^= 25214903917L;
        int hash = Long.hashCode(seed);
        return Math.floorMod(hash, variantCount);
    }
}
