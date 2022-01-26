package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class BlockTagResult extends Result {
    private final Tag<Block> tag;

    public BlockTagResult(Tag<Block> tag) {
        this.tag = tag;
    }

    public void handle(@Nonnull TransfigurationContainer<?> transfigurationContainer, double powerModifier) {
        BlockState blockState = tag.getRandomElement(transfigurationContainer.getLevel().random).defaultBlockState();
        transfigurationContainer.getLevel().setBlockAndUpdate(transfigurationContainer.getTargetedPos(), blockState);
    }

    @Nonnull
    @Override
    public ResultInstance create() {
        return new AfterDoneResultInstance(this::handle);
    }

    @Nonnull
    @Override
    public ItemStack getRepresentation() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.BLOCK_TAG_RESULT_SERIALIZER.get();
    }

    public Tag<Block> getTag() {
        return tag;
    }
}
