package com.necro.fireworkcapsules.common.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.necro.fireworkcapsules.common.blocks.block.StickerBlock;
import com.necro.fireworkcapsules.common.blocks.entity.StickerBlockEntity;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;

public class StickerBlockRenderer implements BlockEntityRenderer<StickerBlockEntity> {
    private static final Coords[] COORDS = new Coords[8];

    public StickerBlockRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(StickerBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        if (blockEntity.getStickers().isEmpty()) return;

        BlockState blockState = blockEntity.getBlockState();
        Direction facing = blockState.getValue(StickerBlock.FACING);
        int size = Math.min(blockEntity.getStickers().size(), 8);
        for (int i = 0; i < size; i++) {
            Coords coords = COORDS[i];
            renderSticker(blockEntity.getStickers().get(i), facing, i, coords.x(), coords.y(), coords.rot(), poseStack, multiBufferSource, light, overlay);
        }
    }

    private void renderSticker(StickerExplosion explosion, Direction facing, int index, double x, double y, float rot, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(explosion.id().getNamespace(), "textures/item/stickers/" + explosion.id().getPath() + ".png");

        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindForSetup(texture);
        float size = 0.25f;

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);

        switch (facing) {
            case NORTH -> {}
            case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180F));
            case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(-90F));
            case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(90F));
            case UP -> poseStack.mulPose(Axis.XP.rotationDegrees(90F));
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(-90F));
        }

        poseStack.mulPose(Axis.ZP.rotationDegrees(rot));
        poseStack.translate(0.5 - x, y - 0.5, 0.499F - index * 0.001F);

        VertexConsumer buffer = multiBufferSource.getBuffer(RenderType.text(texture));
        Matrix4f matrix = poseStack.last().pose();

        buffer.addVertex(matrix, -size, -size, 0).setUv(1, 1).setColor(255, 255, 255, 255).setLight(light).setOverlay(overlay).setNormal(0, 0, 1);
        buffer.addVertex(matrix, -size, size, 0).setUv(1, 0).setColor(255, 255, 255, 255).setLight(light).setOverlay(overlay).setNormal(0, 0, 1);
        buffer.addVertex(matrix, size, size, 0).setUv(0, 0).setColor(255, 255, 255, 255).setLight(light).setOverlay(overlay).setNormal(0, 0, 1);
        buffer.addVertex(matrix, size, -size, 0).setUv(0, 1).setColor(255, 255, 255, 255).setLight(light).setOverlay(overlay).setNormal(0, 0, 1);

        poseStack.popPose();
    }

    private record Coords(double x, double y, float rot) {}

    static {
        COORDS[0] = new Coords(0.5, 0.5, 0F);
        COORDS[1] = new Coords(0.25, 0.21875, -10F);
        COORDS[2] = new Coords(0.90625, 0.84375, -5F);
        COORDS[3] = new Coords(0.21875, 0.9375, 0F);
        COORDS[4] = new Coords(0.6875, 0.15625, 15F);
        COORDS[5] = new Coords(0.84375, 0.4375, 5F);
        COORDS[6] = new Coords(0.53125, 0.8125, -10F);
        COORDS[7] = new Coords(0.125, 0.5625, 10F);
    }
}
