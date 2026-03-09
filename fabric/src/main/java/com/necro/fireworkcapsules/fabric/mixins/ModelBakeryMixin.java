package com.necro.fireworkcapsules.fabric.mixins;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.fabric.util.ILoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin implements ILoader {
    @Final
    @Shadow
    private Map<ResourceLocation, UnbakedModel> unbakedCache;

    @Final
    @Shadow
    private Map<ModelResourceLocation, UnbakedModel> topLevelModels;

    @Shadow
    abstract UnbakedModel getModel(ResourceLocation resourceLocation);

    @Unique
    private static boolean fc_HAS_LOADED = false;

    @Override
    public void fc_finishedLoading() {
        fc_HAS_LOADED = false;
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/ModelBakery;loadItemModelAndDependencies(Lnet/minecraft/resources/ResourceLocation;)V"))
    private void initInject(BlockColors blockColors, ProfilerFiller profilerFiller, Map<ResourceLocation, BlockModel> map, Map<ResourceLocation, List<BlockStateModelLoader.LoadedJson>> map2, CallbackInfo ci){
        if (fc_HAS_LOADED) return;
        FireworkCapsules.LOGGER.info("Loading custom sticker models");
        Set<ResourceLocation> models = Minecraft.getInstance().getResourceManager()
            .listResources("models/item/stickers", model -> FireworkCapsules.MOD_ID.equals(model.getNamespace()) && model.getPath().endsWith(".json"))
            .keySet();

        models.forEach(model -> {
            String id = model.toString().replace("models/", "").replace(".json", "");
            ResourceLocation resourceLocation = ResourceLocation.parse(id);
            UnbakedModel unbakedmodel = this.getModel(resourceLocation);
            unbakedCache.put(resourceLocation, unbakedmodel);
            topLevelModels.put(new ModelResourceLocation(resourceLocation, "standalone"), unbakedmodel);
        });
        FireworkCapsules.LOGGER.info("Loaded {} sticker models", models.size());
        fc_HAS_LOADED = true;
    }
}
