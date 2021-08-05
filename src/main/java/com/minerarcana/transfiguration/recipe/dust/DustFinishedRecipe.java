package com.minerarcana.transfiguration.recipe.dust;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.builder.FinishedObject;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.json.ObjectJson;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class DustFinishedRecipe implements IFinishedRecipe {
    private final ResourceLocation id;
    private final TransfigurationType type;
    private final FinishedObject<BlockIngredientSerializer<?>> blockState;
    private final ITag.INamedTag<Fluid> fluidState;
    private final ItemStack output;


    public DustFinishedRecipe(ResourceLocation id, TransfigurationType transfigurationType,
                              FinishedObject<BlockIngredientSerializer<?>> blockIngredient,
                              ITag.INamedTag<Fluid> fluidIngredient, ItemStack output) {
        this.id = id;
        this.type = transfigurationType;
        this.blockState = blockIngredient;
        this.fluidState = fluidIngredient;
        this.output = output;
    }

    @Override
    public void serialize(@Nonnull JsonObject json) {
        json.addProperty("transfigurationType", Objects.requireNonNull(type.getRegistryName()).toString());
        json.add("block", blockState.getJson());
        if (fluidState != null) {
            json.addProperty("fluid", fluidState.getName().toString());
        }
        json.add("output", ObjectJson.writeItemStack(output));
    }

    @Override
    @Nonnull
    public ResourceLocation getID() {
        return id;
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return TransfigurationRecipes.DUST_RECIPE_SERIALIZER.get();
    }

    @Nullable
    @Override
    public JsonObject getAdvancementJson() {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementID() {
        return null;
    }
}
