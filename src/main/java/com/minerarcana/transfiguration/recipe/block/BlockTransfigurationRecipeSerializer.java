package com.minerarcana.transfiguration.recipe.block;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockTransfigurationRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
        implements IRecipeSerializer<BlockTransfigurationRecipe> {

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public BlockTransfigurationRecipe read(ResourceLocation recipeId, JsonObject json) {
        return new BlockTransfigurationRecipe(recipeId, RegistryJson.getTransfigurationType(json),
                SerializerJson.getBlockIngredient(json), SerializerJson.getRecipeResult(json));
    }

    @Override
    @Nullable
    @ParametersAreNonnullByDefault
    public BlockTransfigurationRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        TransfigurationType transfigurationType = buffer.readRegistryId();
        BlockIngredientSerializer<?> serializer = buffer.readRegistryId();
        BlockIngredient ingredient = serializer.parse(buffer);

        return new BlockTransfigurationRecipe(recipeId, transfigurationType, ingredient, null);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void write(PacketBuffer buffer, BlockTransfigurationRecipe recipe) {
        buffer.writeRegistryId(recipe.getTransfigurationType());
        BlockIngredient blockIngredient = recipe.getIngredient();
        buffer.writeRegistryId(blockIngredient.getSerializer());
    }
}
