package com.minerarcana.transfiguration.recipe.dust;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationBlocks;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import com.minerarcana.transfiguration.recipe.builder.FinishedObject;
import com.minerarcana.transfiguration.recipe.builder.IngredientBuilder;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredientSerializer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

public class DustRecipeBuilder {
    private final TransfigurationType transfigurationType;
    private FinishedObject<BasicIngredientSerializer<?>> ingredient;
    private TagKey<Fluid> fluidIngredient;
    private ItemStack output;

    public DustRecipeBuilder(TransfigurationType transfigurationType) {
        this.transfigurationType = transfigurationType;
        this.ingredient = IngredientBuilder.matches(TransfigurationBlocks.CATALYST_SUBSTRATE.get());
    }

    @SuppressWarnings("unused")
    public DustRecipeBuilder withBlock(FinishedObject<BasicIngredientSerializer<?>> blockIngredient) {
        this.ingredient = blockIngredient;
        return this;
    }

    public DustRecipeBuilder withFluid(TagKey<Fluid> fluid) {
        this.fluidIngredient = fluid;
        return this;
    }

    public DustRecipeBuilder withOutput(ItemStack output) {
        this.output = output;
        return this;
    }

    public void build(Consumer<FinishedRecipe> recipeConsumer) {
        build(recipeConsumer, null);
    }

    public void build(Consumer<FinishedRecipe> recipeConsumer, @Nullable ResourceLocation id) {
        this.validate(id);
        if (id == null) {
            ResourceLocation resultId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.output.getItem()));
            id = new ResourceLocation(resultId.getNamespace(), "dust/" + resultId.getPath().replace("/", "_")
                    + "_from_" + TransfigurationTypes.getKey(this.transfigurationType).getPath().replace("/", "_")
                    + "_" + ingredient.getId().toString().replace(":", "_").replace("/", "_"));
        }
        recipeConsumer.accept(new DustFinishedRecipe(id, transfigurationType, ingredient, fluidIngredient, output));
    }

    protected void validate(ResourceLocation id) {
        if (output == null) {
            throw new IllegalStateException("No 'output' defined for Dust Recipe " + id + "!");
        }

        if (transfigurationType == null) {
            throw new IllegalStateException("No 'transfigurationType' defined for Dust Recipe " + id + "!");
        }
    }

    public static DustRecipeBuilder create(TransfigurationType transfigurationType) {
        return new DustRecipeBuilder(transfigurationType);
    }
}
