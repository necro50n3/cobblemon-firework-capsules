package com.necro.fireworkcapsules.common.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.necro.fireworkcapsules.common.FireworkCapsules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StickerBookTooltip implements ClientTooltipComponent {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "textures/gui/tooltip/sticker_book_slot.png");
    private static final int ROWS = 3;
    private final List<ItemStack> items;

    public StickerBookTooltip(CompoundTag tag) {
        assert Minecraft.getInstance().player != null;
        this.items = new ArrayList<>();

        RegistryAccess registryAccess = Minecraft.getInstance().player.registryAccess();
        ListTag list = tag.getList("Items", 10);

        for (int i = 0; i < this.inventorySize(); i++) {
            CompoundTag data = list.getCompound(i);
            ItemStack item = ItemStack.parse(registryAccess, data).orElse(ItemStack.EMPTY);
            if (item.isEmpty()) continue;
            item.setCount(data.getInt("Count"));
            this.items.add(item);
        }
    }

    @Override
    public int getHeight() {
        return this.items.isEmpty() ? 0 : 16 + this.colSize() * 18;
    }

    @Override
    public int getWidth(Font font) {
        return this.items.isEmpty() ? 0 : 14 + this.rowSize() * 18;
    }

    private int colSize() {
        return (int) Math.ceil((double) this.inventorySize() / this.rowSize());
    }

    private int rowSize() {
        return Math.min(this.inventorySize(), 9);
    }

    private int inventorySize() {
        return Math.min(this.items.size(), ROWS * 9);
    }

    @Override
    public void renderImage(Font font, int i, int j, GuiGraphics guiGraphics) {
        if (this.items.isEmpty()) return;

        RenderSystem.enableDepthTest();
        this.renderBackground(i, j, guiGraphics);
        this.renderItems(font, i, j, guiGraphics);
    }

    private void renderBackground(int i, int j, GuiGraphics guiGraphics) {
        int invSize = this.inventorySize();
        int xOffset = 7;
        int yOffset = 7;
        int rowTexYPos = 7;
        int rowSize = this.rowSize();
        int rowWidth = rowSize * 18;

        for (int size = rowSize; size > 0; size -= 9) {
            int s = Math.min(size, 9);

            guiGraphics.blit(TEXTURE, i + xOffset, j, 0, 7, 0, s * 18, 7, 256, 256);
            xOffset += s * 18;
        }

        while (invSize > 0) {
            xOffset = 7;
            guiGraphics.blit(TEXTURE, i, j + yOffset, 0, 0, rowTexYPos, 7, 18, 256, 256);
            for (int rSize = rowSize; rSize > 0; rSize -= 9) {
                int s = Math.min(rSize, 9);

                guiGraphics.blit(TEXTURE, i + xOffset, j + yOffset, 0, 7, rowTexYPos, s * 18, 18, 256, 256);
                xOffset += s * 18;
            }
            guiGraphics.blit(TEXTURE, i + xOffset, j + yOffset, 0, 169, rowTexYPos, 7, 18, 256, 256);
            yOffset += 18;
            invSize -= rowSize;
            rowTexYPos = rowTexYPos >= 43 ? 7 : rowTexYPos + 18;
        }

        xOffset = 7;
        for (int size = rowSize; size > 0; size -= 9) {
            int s = Math.min(size, 9);
            guiGraphics.blit(TEXTURE, i + xOffset, j + yOffset, 0, 7, 61, s * 18, 7, 256, 256);
            xOffset += s * 18;
        }

        guiGraphics.blit(TEXTURE, i, j, 0, 0, 0, 7, 7, 256, 256);
        guiGraphics.blit(TEXTURE, i + rowWidth + 7, j, 0, 169, 0, 7, 7, 256, 256);
        guiGraphics.blit(TEXTURE, i + rowWidth + 7, j + yOffset, 0, 169, 61, 7, 7, 256, 256);
        guiGraphics.blit(TEXTURE, i, j + yOffset, 0, 0, 61, 7, 7, 256, 256);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderItems(Font font, int i, int j, GuiGraphics guiGraphics) {
        for (int slot = 0; slot < this.inventorySize(); slot++) {
            this.renderItem(this.items.get(slot), i, j, guiGraphics, font, slot);
        }
    }

    private void renderItem(ItemStack itemStack, int i, int j, GuiGraphics guiGraphics, Font font, int slot) {
        int maxRowSize = this.rowSize();
        int sx = 8 + i + 18 * (slot % maxRowSize);
        int sy = 8 + j + 18 * (slot / maxRowSize);
        guiGraphics.renderItem(itemStack, sx, sy);
        guiGraphics.renderItemDecorations(font, itemStack, sx, sy);
    }
}
