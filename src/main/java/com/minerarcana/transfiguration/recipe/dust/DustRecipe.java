package com.minerarcana.transfiguration.recipe.dust;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredient;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

public class DustRecipe implements IRecipe<DustRecipeInventory> {
    private final ResourceLocation id;
    private final TransfigurationType type;
    private final BlockIngredient blockState;
    private final ITag<Fluid> fluid;
    private final Predicate<FluidState> fluidPredicate;
    private final ItemStack output;

    public DustRecipe(ResourceLocation id, TransfigurationType type, BlockIngredient location,
                      ITag<Fluid> fluid, ItemStack output) {
        this.id = id;
        this.type = type;
        this.blockState = location;
        this.fluid = fluid;
        this.fluidPredicate = fluidState -> {
            if (this.fluid == null) {
                return fluidState.isEmpty();
            } else {
                return fluidState.isTagged(this.fluid);
            }
        };
        this.output = output;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean matches(DustRecipeInventory dustRecipeInventory, World world) {
        return fluidPredicate.test(dustRecipeInventory.getInputFluidState()) &&
                blockState.test(dustRecipeInventory.getInputBlockState());
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
        return fluid;
    }
}
