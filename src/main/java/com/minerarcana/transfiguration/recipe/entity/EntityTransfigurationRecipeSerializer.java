package com.minerarcana.transfiguration.recipe.entity;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityIngredientSerializer;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import com.minerarcana.transfiguration.recipe.result.Result;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class EntityTransfigurationRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
        implements IRecipeSerializer<EntityTransfigurationRecipe> {

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public EntityTransfigurationRecipe read(ResourceLocation recipeId, JsonObject json) {
        return new EntityTransfigurationRecipe(recipeId, RegistryJson.getTransfigurationType(json),
                SerializerJson.getEntityIngredient(json), SerializerJson.getResult(json),
                JSONUtils.getInt(json, "ticks", 12));
    }

    @Override
    @Nullable
    @ParametersAreNonnullByDefault
    public EntityTransfigurationRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        TransfigurationType transfigurationType = buffer.readRegistryId();
        EntityIngredientSerializer<?> serializer = buffer.readRegistryId();
        EntityIngredient ingredient = serializer.parse(buffer);
        ResultSerializer<?> resultSerializer = buffer.readRegistryId();
        Result result = resultSerializer.parse(buffer);
        int ticks = buffer.readInt();
        return new EntityTransfigurationRecipe(recipeId, transfigurationType, ingredient, result, ticks);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void write(PacketBuffer buffer, EntityTransfigurationRecipe recipe) {
        buffer.writeRegistryId(recipe.getTransfigurationType());
        buffer.writeRegistryId(recipe.getIngredient().getSerializer());
        writeIngredient(buffer, recipe.getIngredient());
        buffer.writeRegistryId(recipe.getResult().getSerializer());
        writeResult(buffer, recipe.getResult());
        buffer.writeInt(recipe.getTicks());
    }

    @SuppressWarnings("unchecked")
    private <T extends EntityIngredient> void writeIngredient(PacketBuffer packetBuffer, T entityIngredient) {
        EntityIngredientSerializer<T> serializer = (EntityIngredientSerializer<T>) entityIngredient.getSerializer();
        serializer.write(packetBuffer, entityIngredient);
    }

    @SuppressWarnings("unchecked")
    private <T extends Result> void writeResult(PacketBuffer packetBuffer, T result) {
        ResultSerializer<T> serializer = (ResultSerializer<T>) result.getSerializer();
        serializer.write(packetBuffer, result);
    }
}
