package com.minerarcana.transfiguration.recipe.dust;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredient;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class DustRecipe implements IRecipe<DustRecipeInventory> {
    private final ResourceLocation id;
    private final TransfigurationType type;
    private final Ingredient ingredient;
    private final BlockIngredient blockState;
    private final ITag<Fluid> fluidState;
    private final ItemStack output;

    public DustRecipe(ResourceLocation id, TransfigurationType type, Ingredient ingredient, BlockIngredient location,
                      ITag<Fluid> fluidState, ItemStack output) {
        this.id = id;
        this.type = type;
        this.ingredient = ingredient;
        this.blockState = location;
        this.fluidState = fluidState;
        this.output = output;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean matches(DustRecipeInventory dustRecipeInventory, World world) {
        return false;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack getCraftingResult(DustRecipeInventory inv) {
        return output.copy();
    }

    public void handleItemEntities(List<ItemEntity> itemEntities) {
        for (ItemEntity itemEntity : itemEntities) {
            if (ingredient.test(itemEntity.getItem())) {
                itemEntity.getItem().shrink(1);
                if (itemEntity.getItem().isEmpty()) {
                    itemEntity.remove();
                }
                break;
            }
        }
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return output;
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return TransfigurationRecipes.DUST_RECIPE_SERIALIZER.get();
    }

    @Override
    @Nonnull
    public IRecipeType<?> getType() {
        return TransfigurationRecipes.DUST_RECIPE_TYPE;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    public BlockIngredient getBlockState() {
        return blockState;
    }

    public TransfigurationType getTransfigurationType() {
        return type;
    }

    public ItemStack getOutput() {
        return output;
    }

    public ITag<Fluid> getFluid() {
        return fluidState;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}
