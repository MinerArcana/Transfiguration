package com.minerarcana.transfiguration.compat.jei;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import com.minerarcana.transfiguration.content.TransfigurationText;
import com.minerarcana.transfiguration.recipe.entity.EntityTransfigurationRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityTransfigurationRecipeCategory extends TransfigurationRecipeCategory<ITransfigurationRecipe<Entity>, Entity> {
    private final RecipeType<ITransfigurationRecipe<Entity>> recipeType;

    public EntityTransfigurationRecipeCategory(IGuiHelper guiHelper, TransfigurationType transfigurationType) {
        super(guiHelper, transfigurationType);
        this.recipeType = new RecipeType<>(
                new ResourceLocation(transfigurationType.getEntityRecipeType().toString()),
                EntityTransfigurationRecipe.class
        );
    }

    @Override
    @NotNull
    public RecipeType<ITransfigurationRecipe<Entity>> getRecipeType() {
        return recipeType;
    }

    @Override
    @NotNull
    public Component getTitle() {
        return TransfigurationText.ENTITY_TRANSFIGURATION;
    }
}
