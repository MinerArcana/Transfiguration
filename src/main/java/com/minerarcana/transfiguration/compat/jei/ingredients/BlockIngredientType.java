package com.minerarcana.transfiguration.compat.jei.ingredients;

import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.block.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BlockIngredientType implements IIngredientType<BlockState> {
    @Override
    @Nonnull
    public Class<? extends BlockState> getIngredientClass() {
        return BlockState.class;
    }

    public Collection<BlockState> getBlockStates() {
        return ForgeRegistries.BLOCKS.getValues()
                .parallelStream()
                .map(block -> block.getStateContainer().getValidStates())
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
