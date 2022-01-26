package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredientSerializer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.apache.commons.lang3.Range;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockPropertiesIngredient extends BasicIngredient {
    private final Boolean blockEntity;
    private final Range<Float> hardness;
    private final List<PushReaction> pushReactions;

    public BlockPropertiesIngredient(Boolean tileEntity, Range<Float> hardness, List<PushReaction> pushReactions) {
        this.blockEntity = tileEntity;
        this.hardness = hardness;
        this.pushReactions = pushReactions;
    }

    @Override
    public boolean test(@Nonnull BlockState blockState) {
        boolean valid = blockEntity == null || blockState.hasBlockEntity() == blockEntity;
        valid &= hardness.contains(blockState.getDestroySpeed(new IngredientBlockReader(blockState), BlockPos.ZERO));
        valid &= pushReactions.isEmpty() || pushReactions.contains(blockState.getPistonPushReaction());
        return valid;
    }

    @Nonnull
    @Override
    public BasicIngredientSerializer<?> getSerializer() {
        return TransfigurationRecipes.BLOCK_PROPERTIES_INGREDIENT_SERIALIZER.get();
    }

    public Boolean getBlockEntity() {
        return blockEntity;
    }

    public List<PushReaction> getPushReactions() {
        return pushReactions;
    }

    public Range<Float> getHardness() {
        return hardness;
    }
}
