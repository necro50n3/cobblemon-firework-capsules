package com.necro.fireworkcapsules.common.mixins;

import com.necro.fireworkcapsules.common.util.IParticleCreator;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Level.class)
public class LevelMixin implements IParticleCreator {}
