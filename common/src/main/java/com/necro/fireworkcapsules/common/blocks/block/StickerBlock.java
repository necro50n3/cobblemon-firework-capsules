package com.necro.fireworkcapsules.common.blocks.block;

import com.cobblemon.mod.common.util.VectorShapeExtensionsKt;
import com.mojang.serialization.MapCodec;
import com.necro.fireworkcapsules.common.blocks.FireworkCapsuleBlocks;
import com.necro.fireworkcapsules.common.blocks.entity.StickerBlockEntity;
import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import com.necro.fireworkcapsules.common.util.ShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StickerBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    private static final MapCodec<StickerBlock> CODEC = simpleCodec(StickerBlock::new);
    public static final IntegerProperty STACK = IntegerProperty.create("stack", 1, 8);
    public static final DirectionProperty HORIZONTAL = DirectionProperty.create("horizontal", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    public static final DirectionProperty VERTICAL = DirectionProperty.create("vertical", Direction.UP, Direction.DOWN, Direction.NORTH);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Shapes.box(0.0, 0.0, 0.9375, 1.0, 1.0, 1.0);

    public StickerBlock(Properties properties) {
        super(properties
            .sound(SoundType.PINK_PETALS)
            .mapColor(MapColor.NONE)
            .pushReaction(PushReaction.DESTROY)
            .noOcclusion()
            .noCollission()
            .instabreak()
        );
        this.registerDefaultState(this.defaultBlockState().setValue(STACK, 1).setValue(WATERLOGGED, false));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack itemStack, @NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        if (!itemStack.is(FireworkCapsuleItems.STICKER)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        StickerExplosion sticker = itemStack.get(FireworkCapsuleComponents.STICKER_EXPLOSION.value());
        if (sticker == null) return ItemInteractionResult.FAIL;

        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (!(blockEntity instanceof StickerBlockEntity stickerEntity)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        boolean success = stickerEntity.addSticker(level, blockPos, blockState, sticker);
        if (success) itemStack.consume(1, player);
        return success ? ItemInteractionResult.CONSUME : ItemInteractionResult.FAIL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STACK, HORIZONTAL, VERTICAL, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        boolean isWater = fluidState.getType() == Fluids.WATER;
        Direction clickedFace = blockPlaceContext.getClickedFace();
        Direction vertical = Direction.NORTH;
        if (clickedFace == Direction.UP || clickedFace == Direction.DOWN) vertical = clickedFace;

        Direction[] directions = blockPlaceContext.getNearestLookingDirections();
        for(Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                BlockState blockState = this.defaultBlockState()
                    .setValue(HORIZONTAL, direction.getOpposite())
                    .setValue(VERTICAL, vertical)
                    .setValue(WATERLOGGED, isWater);
                if (blockState.canSurvive(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos())) {
                    return blockState;
                }
            }
        }

        return null;
    }

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, LivingEntity livingEntity, @NotNull ItemStack itemStack) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (!(blockEntity instanceof StickerBlockEntity stickerEntity)) return;
        stickerEntity.addStartingSticker(itemStack.get(FireworkCapsuleComponents.STICKER_EXPLOSION.value()));
        level.setBlock(blockPos, blockState.setValue(STACK, Math.max(stickerEntity.getStickers().size(), 1)), 3);
    }

    @Override
    protected @NotNull FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor level, BlockPos blockPos, BlockPos blockPos2) {
        if (direction.getOpposite() == getDirection(blockState) && !blockState.canSurvive(level, blockPos)) return Blocks.AIR.defaultBlockState();
        if (blockState.getValue(WATERLOGGED)) level.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return super.updateShape(blockState, direction, blockState2, level, blockPos, blockPos2);
    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        Direction direction = getDirection(blockState);
        BlockPos nextPos = blockPos.relative(direction.getOpposite());
        BlockState nextState = level.getBlockState(nextPos);
        return nextState.isFaceSturdy(level, nextPos, direction);
    }

    public static Direction getDirection(BlockState blockState) {
        Direction horizontal = blockState.getValue(HORIZONTAL);
        Direction vertical = blockState.getValue(VERTICAL);

        if (vertical.getAxis().isVertical()) return vertical;
        return horizontal;
    }

    @Override
    protected @NotNull List<ItemStack> getDrops(@NotNull BlockState blockState, LootParams.@NotNull Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (!(blockEntity instanceof StickerBlockEntity stickerEntity)) return List.of();

        List<ItemStack> items = new ArrayList<>();
        for (StickerExplosion sticker : stickerEntity.getStickers()) {
            ItemStack stickerItem = FireworkCapsuleItems.STICKER.value().getDefaultInstance();
            stickerItem.set(FireworkCapsuleComponents.STICKER_EXPLOSION.value(), sticker);
            items.add(stickerItem);
        }
        return items;
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(LevelReader level, BlockPos blockPos, BlockState blockState) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (!(blockEntity instanceof StickerBlockEntity stickerEntity)) return ItemStack.EMPTY;

        ItemStack sticker = FireworkCapsuleItems.STICKER.value().getDefaultInstance();
        sticker.set(FireworkCapsuleComponents.STICKER_EXPLOSION.value(), stickerEntity.getStickers().getFirst());
        return sticker;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected boolean isPathfindable(@NotNull BlockState blockState, @NotNull PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new StickerBlockEntity(FireworkCapsuleBlocks.STICKER_ENTITY.value(), blockPos, blockState);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context) {
        Direction horizontal = blockState.getValue(HORIZONTAL);
        Direction vertical = blockState.getValue(VERTICAL);
        VoxelShape shape = SHAPE;

        shape = switch (vertical) {
            case UP, DOWN -> ShapeUtils.rotateVertical(vertical, shape);
            default -> shape;
        };

        shape = switch (horizontal) {
            case NORTH, EAST, SOUTH, WEST -> VectorShapeExtensionsKt.rotateShape(Direction.NORTH, horizontal, shape);
            default -> shape;
        };

        return shape;
    }
}
