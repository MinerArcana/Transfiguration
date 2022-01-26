package com.minerarcana.transfiguration.recipe.dust;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.EmptyInventory;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class DustRecipeInventory extends EmptyInventory {
    private final BlockState inputBlockState;
    private final FluidState inputFluidState;
    private final TransfigurationType inputType;

    public DustRecipeInventory(BlockState inputBlockState, FluidState inputFluidState, TransfigurationType inputType) {
        this.inputBlockState = inputBlockState;
        this.inputFluidState = inputFluidState;
        this.inputType = inputType;
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
}
