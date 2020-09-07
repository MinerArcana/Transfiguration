package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class SingleBlockIngredient extends BlockIngredient {
    private final Block block;

    private SingleBlockIngredient(Block block) {
        this.block = block;
    }

    @Override
    public boolean test(BlockState blockState) {
        return blockState.getBlock() == block;
    }

    public Block getBlock() {
        return block;
    }

    @Nonnull
    @Override
    public BlockIngredientSerializer<? extends BlockIngredient> getSerializer() {
        return TransfigurationRecipes.SINGLE_BLOCK_INGREDIENT_SERIALIZER.get();
    }

    public static SingleBlockIngredient create(@Nonnull Block block) {
        return new SingleBlockIngredient(block);
    }
}
