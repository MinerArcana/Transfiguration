package com.minerarcana.transfiguration.recipe.dust;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.MatchIngredient;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import net.minecraft.block.Blocks;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class DustRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<DustRecipe> {
    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public DustRecipe read(ResourceLocation recipeId, JsonObject json) {
        return new DustRecipe(
                recipeId,
                RegistryJson.getTransfigurationType(json),
                json.has("block") ? SerializerJson.getBasicIngredient(json, "block") : new MatchIngredient(Blocks.AIR),
                json.has("fluid") ? ResourceLocation.tryCreate(JSONUtils.getString(json, "fluid")) : null,
                CraftingHelper.getItemStack(json.getAsJsonObject("output"), true)
        );
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public DustRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        return new DustRecipe(
                recipeId,
                buffer.readRegistryId(),
                BasicIngredient.fromBuffer(buffer),
                buffer.readBoolean() ? buffer.readResourceLocation() : null,
                buffer.readItemStack()
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    public void write(PacketBuffer buffer, DustRecipe recipe) {
        buffer.writeRegistryId(recipe.getTransfigurationType());
        BasicIngredient.toBuffer(buffer, recipe.getIngredient());
        buffer.writeBoolean(recipe.getFluid() != null);
        if (recipe.getFluid() != null) {
            buffer.writeResourceLocation(FluidTags.getCollection().getValidatedIdFromTag(recipe.getFluid()));
        }
        buffer.writeItemStack(recipe.getOutput());
    }
}
