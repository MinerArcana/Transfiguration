package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;

public class ItemResult extends Result {
    private final ItemStack output;

    public ItemResult(ItemStack output) {
        this.output = output;
    }

    @Nonnull
    @Override
    public ResultInstance create() {
        return new AfterDoneResultInstance(0, this::summonItemStack);
    }

    public void summonItemStack(TransfigurationContainer<?> container, double powerModifier) {
        BlockPos blockPos = container.getTargetedPos();
        container.getLevel().addFreshEntity(new ItemEntity(
                container.getLevel(),
                blockPos.getX() + 0.5,
                blockPos.getY() + 0.5,
                blockPos.getZ() + 0.5,
                output.copy()
        ));
    }

    @Nonnull
    @Override
    public ItemStack getRepresentation() {
        return output;
    }

    @Override
    public Ingredient getView() {
        return Ingredient.of(this.output);
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.ITEM_RESULT_SERIALIZER.get();
    }
}
