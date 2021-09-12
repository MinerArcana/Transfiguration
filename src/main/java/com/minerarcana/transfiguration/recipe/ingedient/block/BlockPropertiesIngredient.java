package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredientSerializer;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.Range;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockPropertiesIngredient extends BasicIngredient {
    private final Boolean tileEntity;
    private final Range<Float> hardness;
    private final List<PushReaction> pushReactions;

    public BlockPropertiesIngredient(Boolean tileEntity, Range<Float> hardness, List<PushReaction> pushReactions) {
        this.tileEntity = tileEntity;
        this.hardness = hardness;
        this.pushReactions = pushReactions;
    }

    @Override
    public boolean test(@Nonnull BlockState blockState) {
        boolean valid = tileEntity == null || blockState.hasTileEntity() == tileEntity;
        valid &= hardness.contains(blockState.getBlockHardness(new IngredientBlockReader(blockState), BlockPos.ZERO));
        valid &= pushReactions.isEmpty() || pushReactions.contains(blockState.getPushReaction());
        return valid;
    }

    @Nonnull
    @Override
    public BasicIngredientSerializer<?> getSerializer() {
        return TransfigurationRecipes.BLOCK_PROPERTIES_INGREDIENT_SERIALIZER.get();
    }

    public Boolean getTileEntity() {
        return tileEntity;
    }

    public List<PushReaction> getPushReactions() {
        return pushReactions;
    }

    public Range<Float> getHardness() {
        return hardness;
    }
}
