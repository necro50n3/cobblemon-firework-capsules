package com.necro.fireworkcapsules.common.gui;

import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.client.CobblemonClient;
import com.cobblemon.mod.common.client.gui.summary.widgets.ModelWidget;
import com.cobblemon.mod.common.client.storage.ClientParty;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.necro.fireworkcapsules.common.FireworkCapsules;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class CapsuleStationScreen extends AbstractContainerScreen<CapsuleStationMenu> {
    private static final ResourceLocation SCREEN_LOCATION = ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "textures/gui/container/capsule_station.png");
    private static final int PORTRAIT_SIZE = 28;

    public CapsuleStationScreen(CapsuleStationMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
        this.imageWidth = 176;
        this.imageHeight = 181;
        this.titleLabelX = 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    protected void init() {
        super.init();
        ClientParty party = CobblemonClient.INSTANCE.getStorage().getParty();
        int x = (width - this.imageWidth) / 2;
        int y = (height - this.imageHeight) / 2;

        for (int i = 0; i < 6; i++) {
            Pokemon pokemon = party.get(i);
            if (pokemon == null) continue;

            ModelWidget modelWidget = new ModelWidget(
                x + 9 + 55 * (i % 3),
                y + 19 + 35 * (i / 3),
                PORTRAIT_SIZE,
                PORTRAIT_SIZE,
                pokemon.asRenderablePokemon(),
                0.85f,
                325f,
                -4.0,
                false,
                false
            );
            addRenderableOnly(modelWidget);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int k = this.leftPos;
        int l = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(SCREEN_LOCATION, k, l, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        this.renderTooltip(guiGraphics, i, j);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(CobblemonSounds.PC_OFF, 1.0f));
        super.onClose();
    }
}
