package com.minerarcana.transfiguration.recipe.block;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.parser.BlockIngredientObjectParser;
import com.minerarcana.transfiguration.parser.ObjectParser;
import com.minerarcana.transfiguration.parser.RecipeResultObjectParser;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockTransfigurationRecipeSerializer extends net.minecraftforge.registries.ForgeRegistryEntry<net.minecraft.item.crafting.IRecipeSerializer<?>> implements net.minecraft.item.crafting.IRecipeSerializer<BlockTransfigurationRecipe> {
    private static final ObjectParser<BlockTransfigurationRecipeBuilder, BlockTransfigurationRecipe> PARSER =
            new ObjectParser<>(BlockTransfigurationRecipeBuilder::new)
                    .withField("ingredient", BlockTransfigurationRecipeBuilder::withIngredient,
                            BlockTransfigurationRecipe::getIngredient, BlockIngredientObjectParser.INSTANCE)
                    .withField("transfigurationType", BlockTransfigurationRecipeBuilder::withTransfigurationType,
                            BlockTransfigurationRecipe::getTransfigurationType, Transfiguration.TRANSFIGURATION_TYPE_PARSER)
                    .withField("result", BlockTransfigurationRecipeBuilder::withResult,
                            BlockTransfigurationRecipe::getResult, RecipeResultObjectParser.INSTANCE);

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public BlockTransfigurationRecipe read(ResourceLocation recipeId, JsonObject json) {
        return PARSER.fromJson(recipeId, json);
    }

    @Override
    @Nullable
    @ParametersAreNonnullByDefault
    public BlockTransfigurationRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        return null;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void write(PacketBuffer buffer, BlockTransfigurationRecipe recipe) {

    }
}
