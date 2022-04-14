package com.minerarcana.transfiguration.recipe.dust;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.builder.FinishedObject;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredientSerializer;
import com.minerarcana.transfiguration.recipe.json.ObjectJson;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class DustFinishedRecipe implements FinishedRecipe {
    private final ResourceLocation id;
    private final TransfigurationType type;
    private final FinishedObject<BasicIngredientSerializer<?>> blockState;
    private final TagKey<Fluid> fluidState;
    private final ItemStack output;


    public DustFinishedRecipe(ResourceLocation id, TransfigurationType transfigurationType,
                              FinishedObject<BasicIngredientSerializer<?>> blockIngredient,
                              TagKey<Fluid> fluidIngredient, ItemStack output) {
        this.id = id;
        this.type = transfigurationType;
        this.blockState = blockIngredient;
        this.fluidState = fluidIngredient;
        this.output = output;
    }

    @Override
    public void serializeRecipeData(@Nonnull JsonObject json) {
        json.addProperty("transfigurationType", Objects.requireNonNull(type.getRegistryName()).toString());
        json.add("block", blockState.getJson());
        if (fluidState != null) {
            json.addProperty("fluid", fluidState.location().toString());
        }
        json.add("output", ObjectJson.writeItemStack(output));
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @Nonnull
    public RecipeSerializer<?> getType() {
        return TransfigurationRecipes.DUST_RECIPE_SERIALIZER.get();
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return null;
    }
}
