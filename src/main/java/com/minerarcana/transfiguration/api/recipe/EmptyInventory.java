package com.minerarcana.transfiguration.api.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class EmptyInventory implements Container {
    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    @Nonnull
    public ItemStack getItem(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack removeItem(int index, int count) {
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack removeItemNoUpdate(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int index, @Nonnull ItemStack stack) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}
