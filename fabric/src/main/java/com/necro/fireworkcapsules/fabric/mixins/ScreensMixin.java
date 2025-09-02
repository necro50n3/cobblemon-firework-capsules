package com.necro.fireworkcapsules.fabric.mixins;

import com.necro.fireworkcapsules.common.gui.CapsuleStationScreen;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(Screens.class)
public class ScreensMixin {
    @Inject(method = "getButtons", at = @At("HEAD"), cancellable = true)
    private static void getButtonsInject(Screen screen, CallbackInfoReturnable<List<AbstractWidget>> cir) {
        if (screen instanceof CapsuleStationScreen) cir.setReturnValue(new ArrayList<>());
    }
}
