package com.minerarcana.transfiguration.recipe.ingedient;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.ITag;

public class TagBlockIngredient extends BlockIngredient {
    private final ITag<Block> tag;

    public TagBlockIngredient(ITag<Block> tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(BlockState blockState) {
        return tag.contains(blockState.getBlock());
    }
}
