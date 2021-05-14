package com.minerarcana.transfiguration.compat.jei.cateogry;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.compat.jei.TransfigurationJEIPlugin;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockTransfigurationCategory implements IRecipeCategory<BlockTransfigurationRecipe> {
    public static final ResourceLocation UID = Transfiguration.rl("block_transfiguration");

    private final IDrawable background;
    private final IDrawable icon;

    public BlockTransfigurationCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");
        background = guiHelper.drawableBuilder(location, 0, 168, 125, 18)
                .addPadding(0, 20, 0, 0)
                .build();
        icon = guiHelper.createDrawableIngredient(new ItemStack(TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM.get()));
    }

    @Override
    @Nonnull
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    @Nonnull
    public Class<? extends BlockTransfigurationRecipe> getRecipeClass() {
        return BlockTransfigurationRecipe.class;
    }

    @Override
    @Nonnull
    public String getTitle() {
        return "";
    }

    @Override
    @Nonnull
    public ITextComponent getTitleAsTextComponent() {
        return new TranslationTextComponent("screen.transfiguration.jei.category.transfiguring");
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return background;
    }

    @Override
    @Nonnull
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setIngredients(BlockTransfigurationRecipe blockTransfigurationRecipe, IIngredients iIngredients) {
        iIngredients.setInputs(
                TransfigurationJEIPlugin.BLOCK_INGREDIENT_TYPE,
                blockTransfigurationRecipe.getIngredient().getMatching()
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setRecipe(IRecipeLayout iRecipeLayout, BlockTransfigurationRecipe blockTransfigurationRecipe, IIngredients iIngredients) {
        iRecipeLayout.getIngredientsGroup(TransfigurationJEIPlugin.BLOCK_INGREDIENT_TYPE)
                .init(0, true, 0, 8);
    }
}
