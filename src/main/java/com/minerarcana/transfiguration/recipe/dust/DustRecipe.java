package com.minerarcana.transfiguration.recipe.dust;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

public class DustRecipe implements Recipe<DustRecipeInventory> {
    private final ResourceLocation id;
    private final TransfigurationType type;
    private final BasicIngredient ingredient;
    private final TagKey<Fluid> fluid;
    private final Predicate<FluidState> fluidPredicate;
    private final ItemStack output;

    public DustRecipe(ResourceLocation id, TransfigurationType type, BasicIngredient ingredient,
                      TagKey<Fluid> fluid, ItemStack output) {
        this.id = id;
        this.type = type;
        this.ingredient = ingredient;
        this.fluid = fluid;
        this.fluidPredicate = fluidState -> {
            TagKey<Fluid> fluidTag = this.getFluid();
            if (fluidTag == null) {
                return fluidState.isEmpty();
            } else {
                return fluidState.is(fluidTag);
            }
        };
        this.output = output;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean matches(DustRecipeInventory dustRecipeInventory, Level world) {
        return type == dustRecipeInventory.getInputType() &&
                fluidPredicate.test(dustRecipeInventory.getInputFluidState()) &&
                ingredient.test(dustRecipeInventory.getInputBlockState());
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack assemble(DustRecipeInventory inv) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    @Nonnull
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @Nonnull
    public RecipeSerializer<?> getSerializer() {
        return TransfigurationRecipes.DUST_RECIPE_SERIALIZER.get();
    }

    @Override
    @Nonnull
    public RecipeType<?> getType() {
        return TransfigurationRecipes.DUST_RECIPE_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public BasicIngredient getIngredient() {
        return ingredient;
    }

    public TransfigurationType getTransfigurationType() {
        return type;
    }

    public ItemStack getOutput() {
        return output;
    }

    @Nullable
    public TagKey<Fluid> getFluid() {
        return fluid;
    }
}
