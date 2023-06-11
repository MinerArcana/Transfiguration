package com.minerarcana.transfiguration.compat.jei;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.content.TransfigurationItems;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import com.minerarcana.transfiguration.item.TransfiguringProjectileItem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

public abstract class TransfigurationRecipeCategory<T extends ITransfigurationRecipe<U>, U> implements IRecipeCategory<T> {
    private final IDrawable background;
    private final IDrawable icon;

    public TransfigurationRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");
        background = guiHelper.drawableBuilder(location, 0, 168, 125, 18)
                .addPadding(0, 20, 0, 0)
                .build();

        icon = guiHelper.createDrawableIngredient(
                VanillaTypes.ITEM_STACK,
                TransfigurationItems.MAGIC_POWDER.asStack()
        );
    }

    @Override
    @NotNull
    public IDrawable getBackground() {
        return background;
    }

    @Override
    @NotNull
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .addIngredients(recipe.getIngredient().asItemIngredient())
                .setSlotName("input");

       builder.addSlot(RecipeIngredientRole.CATALYST, 50, 1)
                .addItemStack(new ItemStack(TransfigurationTypes.getDust(recipe::getTransfigurationType)))
                .setSlotName("catalyst");

        builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 1)
                .addItemStack(recipe.getResult().getRepresentation())
                .setSlotName("output");
    }
}
