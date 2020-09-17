package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.recipe.builder.IngredientBuilder;
import com.minerarcana.transfiguration.recipe.builder.ResultBuilder;
import com.minerarcana.transfiguration.recipe.builder.TransfigurationRecipeBuilder;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;

public class TransfigurationAdditionalData {
    public static void addRecipes(RegistrateRecipeProvider recipeProvider) {
        TransfigurationRecipeBuilder.block(TransfigurationTypes.ACCURSED.get())
                .withIngredient(IngredientBuilder.block(Blocks.DIRT))
                .withResult(ResultBuilder.block(Blocks.COARSE_DIRT))
                .build(recipeProvider);

        TransfigurationRecipeBuilder.block(TransfigurationTypes.ACCURSED.get())
                .withIngredient(IngredientBuilder.block(Blocks.GRASS_BLOCK))
                .withResult(ResultBuilder.block(Blocks.COARSE_DIRT))
                .build(recipeProvider);

        TransfigurationRecipeBuilder.entity(TransfigurationTypes.ACCURSED.get())
                .withIngredient(IngredientBuilder.entityType(EntityType.VILLAGER))
                .withResult(ResultBuilder.entityTypeTag(TransfigurationEntityTypeTags.OUTPUTS_ACCURSED))
                .build(recipeProvider);
    }

    public static void addEntityTypeTags(RegistrateTagsProvider<EntityType<?>> tagsProvider) {
        tagsProvider.getOrCreateBuilder(TransfigurationEntityTypeTags.OUTPUTS_ACCURSED)
                .add(EntityType.WITCH, EntityType.ZOMBIE, EntityType.PILLAGER, EntityType.VINDICATOR,
                        EntityType.ILLUSIONER);
    }
}
