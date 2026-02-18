package com.necro.fireworkcapsules.common.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import com.necro.fireworkcapsules.common.util.RendererUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
    @Final
    @Shadow
    private PoseStack pose;

    @Shadow
    public abstract int drawString(Font font, String string, int i, int j, int color, boolean bl);

    @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private void renderItemDecorationsInject(Font font, ItemStack itemStack, int i, int j, String string, CallbackInfo ci) {
        if (itemStack.is(FireworkCapsuleItems.STICKER.value())) {
            this.pose.pushPose();
            if (itemStack.getCount() != 1) {
                String string2 = RendererUtils.shortenCount(itemStack.getCount());
                this.pose.translate(0.0F, 0.0F, 200.0F);
                this.drawString(font, string2, i + 19 - 2 - font.width(string2), j + 6 + 3, 16777215, true);
            }
            this.pose.popPose();
            ci.cancel();
        }
    }
}
