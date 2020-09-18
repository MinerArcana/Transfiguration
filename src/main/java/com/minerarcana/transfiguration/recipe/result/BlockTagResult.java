package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.TransfigurationContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;

import javax.annotation.Nonnull;

public class BlockTagResult extends Result {
    private final ITag<Block> tag;

    public BlockTagResult(ITag<Block> tag) {
        this.tag = tag;
    }

    @Nonnull
    @Override
    public ActionResultType handle(@Nonnull TransfigurationContainer<?> transfigurationContainer) {
        BlockState blockState = tag.getRandomElement(transfigurationContainer.getWorld().rand).getDefaultState();
        return transfigurationContainer.getWorld().setBlockState(transfigurationContainer.getTargetedPos(), blockState) ?
                ActionResultType.SUCCESS : ActionResultType.FAIL;
    }

    @Nonnull
    @Override
    public ItemStack getOutputRepresentation() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.BLOCK_TAG_RESULT_SERIALIZER.get();
    }

    public ITag<Block> getTag() {
        return tag;
    }
}
