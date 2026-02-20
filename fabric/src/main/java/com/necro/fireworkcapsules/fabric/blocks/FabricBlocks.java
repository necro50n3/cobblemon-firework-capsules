package com.necro.fireworkcapsules.fabric.blocks;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.blocks.block.CapsuleStationBlock;
import com.necro.fireworkcapsules.common.blocks.FireworkCapsuleBlocks;
import com.necro.fireworkcapsules.common.blocks.block.StickerBlock;
import com.necro.fireworkcapsules.common.blocks.entity.StickerBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
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

        FireworkCapsuleBlocks.STICKER = Registry.registerForHolder(BuiltInRegistries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, "sticker_block"),
            new StickerBlock(BlockBehaviour.Properties.of())
        );
        FireworkCapsuleBlocks.STICKER_ENTITY = registerBlockEntity(
            "sticker_entity",
            (blockPos, blockState) -> new StickerBlockEntity(FireworkCapsuleBlocks.STICKER_ENTITY.value(), blockPos, blockState),
            FireworkCapsuleBlocks.STICKER.value()
        );
    }

    private static <T extends BlockEntity> Holder<BlockEntityType<T>> registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<T> supplier, Block block) {
        return Holder.direct(Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(FireworkCapsules.MOD_ID, name),
            BlockEntityType.Builder.of(supplier, block).build()
        ));
    }
}
