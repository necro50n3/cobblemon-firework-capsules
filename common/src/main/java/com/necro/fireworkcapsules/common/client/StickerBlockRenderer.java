package com.necro.fireworkcapsules.common.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.blocks.block.StickerBlock;
import com.necro.fireworkcapsules.common.blocks.entity.StickerBlockEntity;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;

public class StickerBlockRenderer implements BlockEntityRenderer<StickerBlockEntity> {
    private static final Coords[][] COORDS = new Coords[3][8];

    public StickerBlockRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(StickerBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        if (blockEntity.getStickers().isEmpty()) return;

        BlockState blockState = blockEntity.getBlockState();
        Direction horizontal = blockState.getValue(StickerBlock.HORIZONTAL);
        Direction vertical = blockState.getValue(StickerBlock.VERTICAL);
        int variant = Math.floorMod(BlockPos.offset(blockEntity.getBlockPos().asLong(), StickerBlock.getDirection(blockState)), 3);
        int size = Math.min(blockEntity.getStickers().size(), 8);
        for (int i = 0; i < size; i++) {
            Coords coords = COORDS[variant][i];
            renderSticker(blockEntity.getStickers().get(i), horizontal, vertical, i, coords.x(), coords.y(), coords.rot(), poseStack, multiBufferSource, light, overlay);
        }
    }

    private void renderSticker(StickerExplosion sticker, Direction horizontal, Direction vertical, int index, double x, double y, float rot, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "textures/item/stickers/" + sticker.id().getPath() + ".png");

        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindForSetup(texture);
        float size = 0.25f;

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);

        switch (horizontal) {
            case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180F));
            case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(-90F));
            case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(90F));
        }

        switch (vertical) {
            case UP -> poseStack.mulPose(Axis.XP.rotationDegrees(90F));
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(-90F));
        }

        poseStack.translate(0.5 - x, y - 0.5, 0.499F - index * 0.001F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(rot));

        VertexConsumer buffer = multiBufferSource.getBuffer(RenderType.text(texture));
        Matrix4f matrix = poseStack.last().pose();

        buffer.addVertex(matrix, -size, -size, 0).setUv(1, 1).setColor(255, 255, 255, 255).setLight(light).setOverlay(overlay).setNormal(0, 0, 1);
        buffer.addVertex(matrix, -size, size, 0).setUv(1, 0).setColor(255, 255, 255, 255).setLight(light).setOverlay(overlay).setNormal(0, 0, 1);
        buffer.addVertex(matrix, size, size, 0).setUv(0, 0).setColor(255, 255, 255, 255).setLight(light).setOverlay(overlay).setNormal(0, 0, 1);
        buffer.addVertex(matrix, size, -size, 0).setUv(0, 1).setColor(255, 255, 255, 255).setLight(light).setOverlay(overlay).setNormal(0, 0, 1);

        poseStack.popPose();
    }

    private record Coords(double x, double y, float rot) {
        public Coords(int x, int y, float rot) {
            this(x / 32.0, y / 32.0, rot);
        }
    }

    static {
        COORDS[0][0] = new Coords(16, 16, 0F);
        COORDS[0][1] = new Coords(8, 7, -10F);
        COORDS[0][2] = new Coords(29, 27, -5F);
        COORDS[0][3] = new Coords(7, 30, 0F);
        COORDS[0][4] = new Coords(22, 5, 15F);
        COORDS[0][5] = new Coords(27, 14, 5F);
        COORDS[0][6] = new Coords(17, 26, -10F);
        COORDS[0][7] = new Coords(4, 18, 10F);

        COORDS[1][0] = new Coords(16, 16, 0F);
        COORDS[1][1] = new Coords(6, 23, -15F);
        COORDS[1][2] = new Coords(19, 4, -5F);
        COORDS[1][3] = new Coords(26, 26, -10F);
        COORDS[1][4] = new Coords(5, 8, 15F);
        COORDS[1][5] = new Coords(14, 29, 5F);
        COORDS[1][6] = new Coords(29, 9, -10F);
        COORDS[1][7] = new Coords(24, 17, 20F);

        COORDS[2][0] = new Coords(16, 16, 0F);
        COORDS[2][1] = new Coords(28, 23, 20F);
        COORDS[2][2] = new Coords(6, 5, -15F);
        COORDS[2][3] = new Coords(8, 18, 5F);
        COORDS[2][4] = new Coords(27, 10, -10F);
        COORDS[2][5] = new Coords(5, 27, 10F);
        COORDS[2][6] = new Coords(18, 28, -5F);
        COORDS[2][7] = new Coords(17, 6, 5F);
    }
}
