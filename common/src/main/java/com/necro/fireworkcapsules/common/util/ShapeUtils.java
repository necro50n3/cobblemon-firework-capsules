package com.necro.fireworkcapsules.common.util;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShapeUtils {
    public static VoxelShape rotateVertical(Direction to, VoxelShape shape) {
        if (to != Direction.UP && to != Direction.DOWN) return shape;
        VoxelShape newShape = Shapes.empty();

        for (AABB boxShape : shape.toAabbs()) {
            double minX = boxShape.minX;
            double maxX = boxShape.maxX;
            double minY = boxShape.minY;
            double maxY = boxShape.maxY;
            double minZ = boxShape.minZ;
            double maxZ = boxShape.maxZ;

            double newMinY = to == Direction.UP ? 1 - maxZ : minZ;
            double newMaxY = to == Direction.UP ? 1 - minZ : maxZ;
            double newMinZ = to == Direction.UP ? minY : 1 - maxY;
            double newMaxZ = to == Direction.UP ? maxY : 1 - minY;

            newShape = Shapes.or(newShape, Shapes.box(minX, newMinY, newMinZ, maxX, newMaxY, newMaxZ));
        }

        return newShape;
    }
}