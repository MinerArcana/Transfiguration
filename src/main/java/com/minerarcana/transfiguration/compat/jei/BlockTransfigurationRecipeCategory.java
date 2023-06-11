package com.minerarcana.transfiguration.compat.jei;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import com.minerarcana.transfiguration.content.TransfigurationText;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockTransfigurationRecipeCategory extends TransfigurationRecipeCategory<ITransfigurationRecipe<BlockState>, BlockState> {
    private final RecipeType<ITransfigurationRecipe<BlockState>> recipeType;

    public BlockTransfigurationRecipeCategory(IGuiHelper guiHelper, TransfigurationType transfigurationType) {
        super(guiHelper);
        this.recipeType = new RecipeType<>(
                new ResourceLocation(transfigurationType.getBlockRecipeType().toString()),
                BlockTransfigurationRecipe.class
        );
    }

    @Override
    @NotNull
    public RecipeType<ITransfigurationRecipe<BlockState>> getRecipeType() {
        return recipeType;
    }

    @Override
    @NotNull
    public Component getTitle() {
        return TransfigurationText.BLOCK_TRANSFIGURATION;
    }
}
