package com.minerarcana.transfiguration.recipe.dust;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.block.SingleBlockIngredient;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
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
        ITag<Fluid> fluidTag = null;
        if (json.has("fluid")) {
            ResourceLocation fluidTagName = ResourceLocation.tryCreate(JSONUtils.getString(json, "fluid"));
            if (fluidTagName != null) {
                fluidTag = FluidTags.getCollection().get(fluidTagName);
            }
        }
        return new DustRecipe(
                recipeId,
                RegistryJson.getTransfigurationType(json),
                json.has("block") ? SerializerJson.getBlockIngredient(json, "block") : SingleBlockIngredient.create(Blocks.AIR),
                fluidTag,
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
                BlockIngredient.fromBuffer(buffer),
                buffer.readBoolean() ? FluidTags.getCollection().getTagByID(buffer.readResourceLocation()) : null,
                buffer.readItemStack()
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    public void write(PacketBuffer buffer, DustRecipe recipe) {
        buffer.writeRegistryId(recipe.getTransfigurationType());
        BlockIngredient.toBuffer(recipe.getBlockState(), buffer);
        buffer.writeBoolean(recipe.getFluid() != null);
        if (recipe.getFluid() != null) {
            buffer.writeResourceLocation(FluidTags.getCollection().getValidatedIdFromTag(recipe.getFluid()));
        }
        buffer.writeItemStack(recipe.getOutput());
    }
}
