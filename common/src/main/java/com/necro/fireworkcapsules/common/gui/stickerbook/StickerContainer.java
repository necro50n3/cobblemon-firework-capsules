package com.necro.fireworkcapsules.common.gui.stickerbook;

import com.necro.fireworkcapsules.common.components.FireworkCapsuleComponents;
import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StickerContainer extends SimpleContainer {
    private final RegistryAccess registryAccess;

    public StickerContainer(ItemStack stickerBook, RegistryAccess registryAccess) {
        super(StickerBookMenu.STICKER_SLOTS);
        this.registryAccess = registryAccess;

        CompoundTag tag = stickerBook.getOrDefault(FireworkCapsuleComponents.STICKER_CONTAINER.value(), new CompoundTag());
        Helper.loadAllItems(tag, this.getItems(), registryAccess);
    }

    @Override
    public @NotNull ItemStack removeItem(int i, int j) {
        ItemStack itemStack = ContainerHelper.removeItem(this.getItems(), i, Math.min(j, 64));
        if (!itemStack.isEmpty()) this.setChanged();
        return itemStack;
    }

    @Override
    public boolean canAddItem(ItemStack stack) {
        return stack.is(FireworkCapsuleItems.STICKER);
    }

    @Override
    public boolean canPlaceItem(int i, ItemStack stack) {
        return stack.is(FireworkCapsuleItems.STICKER);
    }

    public CompoundTag save() {
        return Helper.saveAllItems(new CompoundTag(), this.getItems(), this.registryAccess);
    }

    @Override
    public int getMaxStackSize() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxStackSize(ItemStack itemStack) {
        return this.getMaxStackSize();
    }

    private static class Helper {
        public static CompoundTag saveAllItems(CompoundTag compoundTag, NonNullList<ItemStack> nonNullList, HolderLookup.Provider provider) {
            ListTag listTag = new ListTag();

            for(int i = 0; i < nonNullList.size(); ++i) {
                ItemStack itemStack = nonNullList.get(i);
                if (!itemStack.isEmpty()) {
                    CompoundTag compoundTag2 = new CompoundTag();
                    compoundTag2.putByte("Slot", (byte) i);
                    compoundTag2.putInt("Count", itemStack.getCount());
                    listTag.add(ItemStack.SINGLE_ITEM_CODEC.encode(itemStack, provider.createSerializationContext(NbtOps.INSTANCE), compoundTag2).getOrThrow());
                }
            }
            compoundTag.put("Items", listTag);
            return compoundTag;
        }

        public static void loadAllItems(CompoundTag compoundTag, NonNullList<ItemStack> nonNullList, HolderLookup.Provider provider) {
            ListTag listTag = compoundTag.getList("Items", 10);

            for(int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag2 = listTag.getCompound(i);
                int j = compoundTag2.getByte("Slot") & 255;
                if (j < nonNullList.size()) {
                    ItemStack itemStack = ItemStack.parse(provider, compoundTag2).orElse(ItemStack.EMPTY);
                    if (itemStack != ItemStack.EMPTY) itemStack.setCount(compoundTag2.getInt("Count"));
                    nonNullList.set(j, itemStack);
                }
            }
        }
    }
}
