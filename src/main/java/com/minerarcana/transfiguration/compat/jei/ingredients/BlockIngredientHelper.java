package com.minerarcana.transfiguration.compat.jei.ingredients;

import mezz.jei.api.ingredients.IIngredientHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockIngredientHelper implements IIngredientHelper<BlockState> {
    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public BlockState getMatch(Iterable<BlockState> iterable, BlockState blockState) {
        for (BlockState state : iterable) {
            if (blockState == state) {
                return blockState;
            }
        }
        return null;
    }

    @Override
    @Nonnull
    public String getDisplayName(BlockState blockState) {
        ITextComponent displayNameTextComponent = new TranslationTextComponent(blockState.getBlock().getTranslationKey());
        return displayNameTextComponent.getString();
    }

    @Override
    @Nonnull
    public String getUniqueId(@Nonnull BlockState blockState) {
        return blockState.toString();
    }

    @Override
    @Nonnull
    public String getModId(BlockState blockState) {
        ResourceLocation registryName = blockState.getBlock().getRegistryName();
        return registryName == null ? "null" : registryName.getNamespace();
    }

    @Override
    @Nonnull
    public String getResourceId(@Nonnull BlockState blockState) {

        Block block = blockState.getBlock();
        ResourceLocation registryName = block.getRegistryName();
        if (registryName == null) {
            String stackInfo = getErrorInfo(blockState);
            throw new IllegalStateException("block.getRegistryName() returned null for: " + stackInfo);
        }

        return registryName.getPath();
    }

    @Override
    @Nonnull
    public BlockState copyIngredient(@Nonnull BlockState blockState) {
        return blockState;
    }

    @Override
    @Nonnull
    public String getErrorInfo(@Nullable BlockState blockState) {
        return blockState != null ? blockState.toString() : "null";
    }
}
