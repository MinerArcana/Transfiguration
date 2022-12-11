package com.minerarcana.transfiguration.recipe.ingedient;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public abstract class BasicIngredientSerializer<T extends BasicIngredient> implements ISerializer<T> {
    @Override
    @NotNull
    public ResourceLocation getKey() {
        return TransfigurationRecipes.getKey(this);
    }
}
