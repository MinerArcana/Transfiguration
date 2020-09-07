package com.minerarcana.transfiguration.recipe.block;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.RecipeResult;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredient;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockTransfigurationRecipe implements IRecipe<BlockTransfigurationContainer> {
    private final ResourceLocation recipeId;
    private final TransfigurationType transfigurationType;
    private final BlockIngredient ingredient;
    private final RecipeResult result;

    public BlockTransfigurationRecipe(ResourceLocation recipeId, TransfigurationType transfigurationType,
                                      BlockIngredient ingredient, RecipeResult result) {
        this.transfigurationType = transfigurationType;
        this.recipeId = recipeId;
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean matches(BlockTransfigurationContainer inv, World world) {
        return ingredient.test(inv.getTargeted());
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack getCraftingResult(BlockTransfigurationContainer inv) {
        return result.getRecipeOutput();
    }

    public ActionResultType transfigure(BlockTransfigurationContainer inv) {
        inv.getWorld().setBlockState(inv.getTargetedPos(), result.getBlockState());
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return result.getRecipeOutput();
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return TransfigurationRecipes.BLOCK_TRANSFIGURATION.get();
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return transfigurationType.getBlockRecipeType();
    }

    public TransfigurationType getTransfigurationType() {
        return transfigurationType;
    }

    public BlockIngredient getIngredient() {
        return ingredient;
    }

    public RecipeResult getResult() {
        return result;
    }
}
