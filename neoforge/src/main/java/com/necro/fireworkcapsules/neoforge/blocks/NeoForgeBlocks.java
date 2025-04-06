package com.necro.fireworkcapsules.neoforge.blocks;

import com.necro.fireworkcapsules.common.FireworkCapsules;
import com.necro.fireworkcapsules.common.blocks.CapsuleStationBlock;
import com.necro.fireworkcapsules.common.blocks.FireworkCapsuleBlocks;
import com.necro.fireworkcapsules.neoforge.items.NeoForgeItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(FireworkCapsules.MOD_ID);

    public static void register() {
        FireworkCapsuleBlocks.CAPSULE_STATION = BLOCKS.register("capsule_station", () ->
            new CapsuleStationBlock(BlockBehaviour.Properties.of().strength(3.5f).noOcclusion())
        );
        NeoForgeItems.registerBlockItem("capsule_station", (DeferredBlock<?>) FireworkCapsuleBlocks.CAPSULE_STATION);
    }
}
