package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.recipe.builder.IngredientBuilder;
import com.minerarcana.transfiguration.recipe.builder.ResultBuilder;
import com.minerarcana.transfiguration.recipe.builder.TransfigurationRecipeBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraftforge.common.Tags;

public class TransfigurationRecipeData {
    public static <T extends TransfigurationType> void accursedRecipes(DataGenContext<TransfigurationType, T> context,
                                                                       RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.DIRT))
                .withResult(ResultBuilder.block(Blocks.COARSE_DIRT))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.GRASS_BLOCK))
                .withResult(ResultBuilder.block(Blocks.COARSE_DIRT))
                .build(provider);

        TransfigurationRecipeBuilder.entity(context)
                .withIngredient(IngredientBuilder.entityType(EntityType.VILLAGER))
                .withResult(ResultBuilder.entityTypeTag(TransfigurationEntityTypeTags.OUTPUTS_ACCURSED))
                .build(provider);
    }

    public static <T extends TransfigurationType> void blessedRecipes(DataGenContext<TransfigurationType, T> context,
                                                                      RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.entity(context)
                .withIngredient(IngredientBuilder.entityType(EntityType.ZOMBIE_VILLAGER))
                .withResult(ResultBuilder.entityType(EntityType.VILLAGER))
                .build(provider);

        TransfigurationRecipeBuilder.entity(context)
                .withIngredient(IngredientBuilder.entityType(EntityType.WITCH))
                .withResult(ResultBuilder.entityType(EntityType.VILLAGER))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.STONE))
                .withResult(ResultBuilder.blockTag(Tags.Blocks.ORES))
                .build(provider);
    }

    public static void destabilizingRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                            RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.trueBlock())
                .withResult(ResultBuilder.fallingBlock())
                .build(provider);
    }
}
