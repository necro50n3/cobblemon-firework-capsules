package com.necro.fireworkcapsules.fabric.blocks;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.blocks.CapsuleStationBlock;
import com.necro.fireworkcapsules.common.blocks.FireworkCapsuleBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FabricBlocks {
    public static void register() {
        FireworkCapsuleBlocks.CAPSULE_STATION = Registry.registerForHolder(BuiltInRegistries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "capsule_station"),
            new CapsuleStationBlock(BlockBehaviour.Properties.of().strength(3.5f).noOcclusion())
        );

        Registry.register(BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "capsule_station"),
            new BlockItem(FireworkCapsuleBlocks.CAPSULE_STATION.value(), new Item.Properties())
        );
    }
}
