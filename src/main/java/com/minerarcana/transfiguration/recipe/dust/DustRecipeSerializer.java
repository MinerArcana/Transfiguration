package com.minerarcana.transfiguration.recipe.dust;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.MatchIngredient;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class DustRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<DustRecipe> {
    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public DustRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        return new DustRecipe(
                recipeId,
                RegistryJson.getTransfigurationType(json),
                json.has("block") ? SerializerJson.getBasicIngredient(json, "block") : new MatchIngredient(Blocks.AIR),
                json.has("fluid") ? ResourceLocation.tryParse(GsonHelper.getAsString(json, "fluid")) : null,
                CraftingHelper.getItemStack(json.getAsJsonObject("output"), true)
        );
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public DustRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        return new DustRecipe(
                recipeId,
                buffer.readRegistryId(),
                BasicIngredient.fromBuffer(buffer),
                buffer.readBoolean() ? buffer.readResourceLocation() : null,
                buffer.readItem()
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    public void toNetwork(FriendlyByteBuf buffer, DustRecipe recipe) {
        buffer.writeRegistryId(recipe.getTransfigurationType());
        BasicIngredient.toBuffer(buffer, recipe.getIngredient());
        buffer.writeBoolean(recipe.getFluid() != null);
        if (recipe.getFluid() != null) {
            buffer.writeResourceLocation(FluidTags.getAllTags().getIdOrThrow(recipe.getFluid()));
        }
        buffer.writeItem(recipe.getOutput());
    }
}
