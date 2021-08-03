package com.minerarcana.transfiguration.recipe.dust;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.EmptyInventory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.FluidState;

import java.util.List;

public class DustRecipeInventory extends EmptyInventory {
    private final BlockState inputBlockState;
    private final FluidState inputFluidState;
    private final TransfigurationType inputType;
    private final List<ItemEntity> foundItemEntities;

    public DustRecipeInventory(BlockState inputBlockState, FluidState inputFluidState, TransfigurationType inputType, List<ItemEntity> foundItemEntities) {
        this.inputBlockState = inputBlockState;
        this.inputFluidState = inputFluidState;
        this.inputType = inputType;
        this.foundItemEntities = foundItemEntities;
    }

    public BlockState getInputBlockState() {
        return inputBlockState;
    }

    public FluidState getInputFluidState() {
        return inputFluidState;
    }

    public TransfigurationType getInputType() {
        return inputType;
    }

    public List<ItemEntity> getFoundItemEntities() {
        return foundItemEntities;
    }
}
