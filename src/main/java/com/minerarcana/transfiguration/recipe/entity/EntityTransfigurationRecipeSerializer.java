package com.minerarcana.transfiguration.recipe.entity;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import com.minerarcana.transfiguration.recipe.result.Result;
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
        return new EntityTransfigurationRecipe(
                recipeId,
                RegistryJson.getTransfigurationType(json),
                SerializerJson.getBasicIngredient(json),
                SerializerJson.getResult(json),
                JSONUtils.getInt(json, "ticks", 12)
        );
    }

    @Override
    @Nullable
    @ParametersAreNonnullByDefault
    public EntityTransfigurationRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        TransfigurationType transfigurationType = buffer.readRegistryId();
        BasicIngredient ingredient = BasicIngredient.fromBuffer(buffer);
        Result result = Result.fromBuffer(buffer);
        int ticks = buffer.readInt();
        return new EntityTransfigurationRecipe(recipeId, transfigurationType, ingredient, result, ticks);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void write(PacketBuffer buffer, EntityTransfigurationRecipe recipe) {
        buffer.writeRegistryId(recipe.getTransfigurationType());
        BasicIngredient.toBuffer(buffer, recipe.getIngredient());
        Result.toBuffer(buffer, recipe.getResult());
        buffer.writeInt(recipe.getTicks());
    }
}
