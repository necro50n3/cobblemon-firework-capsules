package com.necro.fireworkcapsules.neoforge.blocks;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.blocks.block.CapsuleStationBlock;
import com.necro.fireworkcapsules.common.blocks.FireworkCapsuleBlocks;
import com.necro.fireworkcapsules.common.blocks.block.StickerBlock;
import com.necro.fireworkcapsules.common.blocks.entity.StickerBlockEntity;
import com.necro.fireworkcapsules.neoforge.items.NeoForgeItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NeoForgeBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(FireworkCapsules.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, FireworkCapsules.MOD_ID);

    public static void register() {
        FireworkCapsuleBlocks.CAPSULE_STATION = BLOCKS.register("capsule_station", () ->
            new CapsuleStationBlock(BlockBehaviour.Properties.of().strength(3.5f).noOcclusion())
        );
        NeoForgeItems.registerBlockItem("capsule_station", (DeferredBlock<?>) FireworkCapsuleBlocks.CAPSULE_STATION);

        FireworkCapsuleBlocks.STICKER = BLOCKS.register("sticker_block", () ->
            new StickerBlock(BlockBehaviour.Properties.of())
        );

        FireworkCapsuleBlocks.STICKER_ENTITY = registerBlockEntity(
            "sticker_entity",
                (blockPos, blockState) -> new StickerBlockEntity(FireworkCapsuleBlocks.STICKER_ENTITY.value(), blockPos, blockState),
            () -> FireworkCapsuleBlocks.STICKER.value()
        );
    }

    @SuppressWarnings("unchecked")
    private static <T extends BlockEntity> Holder<BlockEntityType<T>> registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<T> supplier, Supplier<Block> block) {
        return (Holder<BlockEntityType<T>>) (Object)  BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
    }
}
