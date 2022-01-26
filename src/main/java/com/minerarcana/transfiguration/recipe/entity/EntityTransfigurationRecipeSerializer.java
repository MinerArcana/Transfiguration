package com.minerarcana.transfiguration.recipe.entity;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import com.minerarcana.transfiguration.recipe.result.Result;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class EntityTransfigurationRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>>
        implements RecipeSerializer<EntityTransfigurationRecipe> {

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public EntityTransfigurationRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        return new EntityTransfigurationRecipe(
                recipeId,
                RegistryJson.getTransfigurationType(json),
                SerializerJson.getBasicIngredient(json),
                SerializerJson.getResult(json),
                GsonHelper.getAsInt(json, "ticks", 12)
        );
    }

    @Override
    @Nullable
    @ParametersAreNonnullByDefault
    public EntityTransfigurationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        TransfigurationType transfigurationType = buffer.readRegistryId();
        BasicIngredient ingredient = BasicIngredient.fromBuffer(buffer);
        Result result = Result.fromBuffer(buffer);
        int ticks = buffer.readInt();
        return new EntityTransfigurationRecipe(recipeId, transfigurationType, ingredient, result, ticks);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void toNetwork(FriendlyByteBuf buffer, EntityTransfigurationRecipe recipe) {
        buffer.writeRegistryId(recipe.getTransfigurationType());
        BasicIngredient.toBuffer(buffer, recipe.getIngredient());
        Result.toBuffer(buffer, recipe.getResult());
        buffer.writeInt(recipe.getTicks());
    }
}
