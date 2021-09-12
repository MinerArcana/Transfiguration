package com.minerarcana.transfiguration.recipe.dust;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredient;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

public class DustRecipe implements IRecipe<DustRecipeInventory> {
    private final ResourceLocation id;
    private final TransfigurationType type;
    private final BasicIngredient ingredient;
    private final Lazy<ITag<Fluid>> fluid;
    private final Predicate<FluidState> fluidPredicate;
    private final ItemStack output;

    public DustRecipe(ResourceLocation id, TransfigurationType type, BasicIngredient ingredient,
                      ResourceLocation fluid, ItemStack output) {
        this.id = id;
        this.type = type;
        this.ingredient = ingredient;
        this.fluid = Lazy.of(() -> FluidTags.getCollection().get(fluid));
        this.fluidPredicate = fluidState -> {
            ITag<Fluid> fluidTag = this.getFluid();
            if (fluidTag == null) {
                return fluidState.isEmpty();
            } else {
                return fluidState.isTagged(fluidTag);
            }
        };
        this.output = output;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean matches(DustRecipeInventory dustRecipeInventory, World world) {
        return type == dustRecipeInventory.getInputType() &&
                fluidPredicate.test(dustRecipeInventory.getInputFluidState()) &&
                ingredient.test(dustRecipeInventory.getInputBlockState());
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack getCraftingResult(DustRecipeInventory inv) {
        return output.copy();
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
    public ITag<Fluid> getFluid() {
        return fluid.get();
    }
}
