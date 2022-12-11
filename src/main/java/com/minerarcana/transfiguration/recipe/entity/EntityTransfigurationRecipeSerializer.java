package com.minerarcana.transfiguration.recipe.entity;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import com.minerarcana.transfiguration.recipe.predicate.TransfigurationPredicate;
import com.minerarcana.transfiguration.recipe.result.Result;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class EntityTransfigurationRecipeSerializer implements RecipeSerializer<EntityTransfigurationRecipe> {

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public EntityTransfigurationRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        return new EntityTransfigurationRecipe(
                recipeId,
                RegistryJson.getTransfigurationType(json),
                SerializerJson.getBasicIngredient(json),
                SerializerJson.getResult(json),
                TransfigurationPredicate.fromJson(json),
                GsonHelper.getAsInt(json, "ticks", 12),
                GsonHelper.getAsFloat(json, "skip", 0F)
        );
    }

    @Override
    @Nullable
    @ParametersAreNonnullByDefault
    public EntityTransfigurationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        return new EntityTransfigurationRecipe(
                recipeId,
                buffer.readRegistryId(),
                BasicIngredient.fromBuffer(buffer),
                Result.fromBuffer(buffer),
                TransfigurationPredicate.fromBuffer(buffer),
                buffer.readInt(),
                buffer.readFloat()
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    public void toNetwork(FriendlyByteBuf buffer, EntityTransfigurationRecipe recipe) {
        buffer.writeRegistryId(TransfigurationTypes.getRegistry(), recipe.getTransfigurationType());
        BasicIngredient.toBuffer(buffer, recipe.getIngredient());
        Result.toBuffer(buffer, recipe.getResult());
        TransfigurationPredicate.toBuffer(buffer, recipe.getPredicates());
        buffer.writeInt(recipe.getTicks());
        buffer.writeFloat(recipe.getSkip());
    }
}
