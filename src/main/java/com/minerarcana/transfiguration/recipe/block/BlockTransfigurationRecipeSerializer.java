package com.minerarcana.transfiguration.recipe.block;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import com.minerarcana.transfiguration.recipe.result.Result;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import com.minerarcana.transfiguration.api.TransfigurationType;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
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
                SerializerJson.getBlockIngredient(json), SerializerJson.getResult(json),
                JSONUtils.getInt(json, "ticks", 12));
    }

    @Override
    @Nullable
    @ParametersAreNonnullByDefault
    public BlockTransfigurationRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        TransfigurationType transfigurationType = buffer.readRegistryId();
        BlockIngredientSerializer<?> serializer = buffer.readRegistryId();
        BlockIngredient ingredient = serializer.parse(buffer);
        ResultSerializer<?> resultSerializer = buffer.readRegistryId();
        Result result = resultSerializer.parse(buffer);
        int ticks = buffer.readInt();
        return new BlockTransfigurationRecipe(recipeId, transfigurationType, ingredient, result, ticks);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void write(PacketBuffer buffer, BlockTransfigurationRecipe recipe) {
        buffer.writeRegistryId(recipe.getTransfigurationType());
        writeIngredient(buffer, recipe.getIngredient());
        writeResult(buffer, recipe.getResult());
        buffer.writeInt(recipe.getTicks());
    }

    @SuppressWarnings("unchecked")
    private <T extends BlockIngredient> void writeIngredient(PacketBuffer packetBuffer, T blockIngredient) {
        BlockIngredientSerializer<T> serializer = (BlockIngredientSerializer<T>) blockIngredient.getSerializer();
        serializer.write(packetBuffer, blockIngredient);
    }

    @SuppressWarnings("unchecked")
    private <T extends Result> void writeResult(PacketBuffer packetBuffer, T result) {
        ResultSerializer<T> serializer = (ResultSerializer<T>) result.getSerializer();
        serializer.write(packetBuffer, result);
    }
}
