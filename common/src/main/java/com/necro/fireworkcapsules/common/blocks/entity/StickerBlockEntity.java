package com.necro.fireworkcapsules.common.blocks.entity;

import com.necro.fireworkcapsules.common.blocks.block.StickerBlock;
import com.necro.fireworkcapsules.common.stickers.StickerExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StickerBlockEntity extends BlockEntity {
    private final List<StickerExplosion> stickers;

    public StickerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.stickers = new ArrayList<>();
    }

    public boolean addSticker(Level level, BlockPos blockPos, BlockState blockState, StickerExplosion sticker) {
        if (sticker == null || level.isClientSide()) return false;
        int stackSize = blockState.getValue(StickerBlock.STACK);
        if (stackSize >= 8) return false;
        this.stickers.add(sticker);
        this.setChanged();
        if (!level.isClientSide()) level.playSound(null, blockPos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.8F, 1.0F);
        level.setBlock(blockPos, blockState.setValue(StickerBlock.STACK, stackSize + 1), 3);
        return true;
    }

    public void addStartingSticker(StickerExplosion sticker) {
        if (!this.stickers.isEmpty() || sticker == null || this.getLevel() == null || this.getLevel().isClientSide()) return;
        this.stickers.add(sticker);
    }

    public List<StickerExplosion> getStickers() {
        return this.stickers;
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
        this.stickers.clear();
        ListTag list = compoundTag.getList("stickers", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            this.stickers.add(StickerExplosion.load(list.getCompound(i)));
        }
    }


    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
        ListTag list = new ListTag();
        for (StickerExplosion sticker : this.stickers) {
            list.add(sticker.save(new CompoundTag()));
        }
        compoundTag.put("stickers", list);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        if (this.stickers.isEmpty()) return tag;

        ListTag list = new ListTag();
        for (StickerExplosion sticker : this.stickers) {
            list.add(sticker.save(new CompoundTag()));
        }
        tag.put("stickers", list);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
