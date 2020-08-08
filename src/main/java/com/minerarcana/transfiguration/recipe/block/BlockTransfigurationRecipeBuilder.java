package com.minerarcana.transfiguration.recipe.block;

import com.minerarcana.transfiguration.parser.IObjectBuilder;
import com.minerarcana.transfiguration.recipe.RecipeResult;
import com.minerarcana.transfiguration.recipe.ingedient.BlockIngredient;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.util.ResourceLocation;

public class BlockTransfigurationRecipeBuilder implements IObjectBuilder<BlockTransfigurationRecipe> {
    private BlockIngredient ingredient;
    private RecipeResult result;
    private TransfigurationType transfigurationType;

    public BlockTransfigurationRecipeBuilder withIngredient(BlockIngredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public BlockTransfigurationRecipeBuilder withResult(RecipeResult result) {
        this.result = result;
        return this;
    }

    public BlockTransfigurationRecipeBuilder withTransfigurationType(TransfigurationType transfigurationType) {
        this.transfigurationType = transfigurationType;
        return this;
    }

    @Override
    public BlockTransfigurationRecipe build(ResourceLocation resourceLocation) {
        return new BlockTransfigurationRecipe(resourceLocation, transfigurationType, ingredient, result);
    }
}
