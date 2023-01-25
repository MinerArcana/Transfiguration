package com.minerarcana.transfiguration.recipe.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class CompoundTagBuilder {
    private final CompoundTag tag;

    public CompoundTagBuilder() {
        this.tag = new CompoundTag();
    }

    public CompoundTagBuilder with(String name, CompoundTag subTag) {
        tag.put(name, subTag);
        return this;
    }

    public CompoundTagBuilder with(String name, ItemStack itemStack) {
        return this.with(name, itemStack.save(new CompoundTag()));
    }

    public CompoundTag build() {
        return this.tag.copy();
    }

    public static CompoundTagBuilder start() {
        return new CompoundTagBuilder();
    }
}
