package com.minerarcana.transfiguration.compat.jei.cateogry;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockTransfigurationCategory implements IRecipeCategory<BlockTransfigurationRecipe> {

    private final TransfigurationType type;
    private final IDrawable background;
    private final IDrawable icon;

    public BlockTransfigurationCategory(TransfigurationType type, IGuiHelper guiHelper) {
        ResourceLocation location = Transfiguration.rl("textures/jei/background.png");
        background = guiHelper.drawableBuilder(location, 0, 0, 72, 62)
                .build();
        icon = guiHelper.createDrawableIngredient(TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM
                .map(item -> item.withTransfigurationType(type))
                .orElse(ItemStack.EMPTY)
        );
        this.type = type;
    }

    @Override
    @Nonnull
    public ResourceLocation getUid() {
        return type.getBlockRecipeId();
    }

    @Override
    @Nonnull
    public Class<? extends BlockTransfigurationRecipe> getRecipeClass() {
        return BlockTransfigurationRecipe.class;
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecated")
    public String getTitle() {
        return "Block Transfiguring";
    }

    @Override
    @Nonnull
    public ITextComponent getTitleAsTextComponent() {
        return type.getDisplayName();
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
                VanillaTypes.ITEM,
                blockTransfigurationRecipe.getIngredient().getMatchingStacks()
        );

    }

    @Override
    @ParametersAreNonnullByDefault
    public void setRecipe(IRecipeLayout iRecipeLayout, BlockTransfigurationRecipe blockTransfigurationRecipe, IIngredients iIngredients) {
        iRecipeLayout.getItemStacks()
                .init(0, true, 75, 22);
        iRecipeLayout.getItemStacks()
                .set(iIngredients);
    }
}
