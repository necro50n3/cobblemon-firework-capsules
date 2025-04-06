package com.necro.fireworkcapsules.common.blocks;

import com.cobblemon.mod.common.CobblemonSounds;
import com.mojang.serialization.MapCodec;
import com.necro.fireworkcapsules.common.gui.CapsuleStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CapsuleStationBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<CapsuleStationBlock> CODEC = simpleCodec(CapsuleStationBlock::new);
    public static final Component CONTAINER_TITLE = Component.translatable("container.fireworkcapsules.capsule_station");

    private static final VoxelShape SHAPE_NORTH = Shapes.or(
        Shapes.box(0.0625, 0.0000, 0.0625, 0.9375, 0.6875, 0.9375),
        Shapes.box(0.0625, 0.6875, 0.1250, 0.9375, 0.8125, 0.9375),
        Shapes.box(0.0625, 0.8125, 0.1250, 0.1875, 0.9375, 0.9375),
        Shapes.box(0.1875, 0.8125, 0.8125, 0.8125, 0.9375, 0.9375),
        Shapes.box(0.8125, 0.8125, 0.1250, 0.9375, 0.9375, 0.9375)
    );

    private static final VoxelShape SHAPE_EAST = Shapes.or(
        Shapes.box(0.0625, 0.0000, 0.0625, 0.9375, 0.6875, 0.9375),
        Shapes.box(0.0625, 0.6875, 0.0625, 0.8750, 0.8125, 0.9375),
        Shapes.box(0.0625, 0.8125, 0.0625, 0.8750, 0.9375, 0.1875),
        Shapes.box(0.0625, 0.8125, 0.8125, 0.8750, 0.9375, 0.9375),
        Shapes.box(0.0625, 0.8125, 0.1875, 0.1875, 0.9375, 0.8125)
    );

    private static final VoxelShape SHAPE_SOUTH = Shapes.or(
        Shapes.box(0.0625, 0.0000, 0.0625, 0.9375, 0.6875, 0.9375),
        Shapes.box(0.0625, 0.6875, 0.0625, 0.9375, 0.8125, 0.8750),
        Shapes.box(0.0625, 0.8125, 0.0625, 0.1875, 0.9375, 0.8750),
        Shapes.box(0.1875, 0.8125, 0.0625, 0.8125, 0.9375, 0.1875),
        Shapes.box(0.8125, 0.8125, 0.0625, 0.9375, 0.9375, 0.8750)
    );

    private static final VoxelShape SHAPE_WEST = Shapes.or(
        Shapes.box(0.0625, 0.0000, 0.0625, 0.9375, 0.6875, 0.9375),
        Shapes.box(0.1250, 0.6875, 0.0625, 0.9375, 0.8125, 0.9375),
        Shapes.box(0.1250, 0.8125, 0.0625, 0.9375, 0.9375, 0.1875),
        Shapes.box(0.1250, 0.8125, 0.8125, 0.9375, 0.9375, 0.9375),
        Shapes.box(0.8125, 0.8125, 0.1875, 0.9375, 0.9375, 0.8125)
    );

    public CapsuleStationBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public @NotNull MapCodec<? extends CapsuleStationBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        player.openMenu(blockState.getMenuProvider(level, blockPos));
        level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), CobblemonSounds.PC_ON, SoundSource.NEUTRAL, 0.5f, 1.0f);
        level.gameEvent(player, GameEvent.BLOCK_OPEN, blockPos);
        return InteractionResult.CONSUME;
    }

    @Override
    protected MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        return new SimpleMenuProvider((i, inventory, player) ->
            new CapsuleStationMenu(i, inventory, ContainerLevelAccess.create(level, blockPos)), CONTAINER_TITLE
        );
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context) {
        return switch (blockState.getValue(FACING)) {
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case EAST -> SHAPE_EAST;
            default -> SHAPE_NORTH;
        };
    }

    @Override
    protected @NotNull VoxelShape getOcclusionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return super.getShape(blockState, blockGetter, blockPos, CollisionContext.empty());
    }
}
