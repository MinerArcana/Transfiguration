package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.recipe.builder.IngredientBuilder;
import com.minerarcana.transfiguration.recipe.builder.ResultBuilder;
import com.minerarcana.transfiguration.recipe.builder.TransfigurationRecipeBuilder;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;

public class TransfigurationAdditionalData {

    public static void addEntityTypeTags(RegistrateTagsProvider<EntityType<?>> tagsProvider) {
        tagsProvider.getOrCreateBuilder(TransfigurationEntityTypeTags.OUTPUTS_ACCURSED)
                .add(EntityType.WITCH, EntityType.ZOMBIE, EntityType.PILLAGER, EntityType.VINDICATOR,
                        EntityType.ILLUSIONER);
    }

    public static void addBlockTags(RegistrateTagsProvider<Block> tagsProvider) {
        tagsProvider.getOrCreateBuilder(TransfigurationBlockTags.OUTPUTS_DISSOLUTION)
                .add(Blocks.SAND, Blocks.GRAVEL, Blocks.AIR);

        tagsProvider.getOrCreateBuilder(TransfigurationBlockTags.INPUTS_MUTANDI)
                .addTag(BlockTags.SAPLINGS)
                .addTag(BlockTags.FLOWERS)
                .addTag(BlockTags.CROPS);


        tagsProvider.getOrCreateBuilder(TransfigurationBlockTags.OUTPUTS_MUTANDI)
                .addTag(BlockTags.SAPLINGS)
                .addTag(BlockTags.FLOWERS)
                .addTag(BlockTags.CROPS);
    }

    public static void addRecipes(RegistrateRecipeProvider recipeProvider) {
        //region Accursed
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
        //endregion

        //region Blessed
        TransfigurationRecipeBuilder.entity(TransfigurationTypes.BLESSED.get())
                .withIngredient(IngredientBuilder.entityType(EntityType.ZOMBIE_VILLAGER))
                .withResult(ResultBuilder.entityType(EntityType.VILLAGER))
                .build(recipeProvider);

        TransfigurationRecipeBuilder.block(TransfigurationTypes.BLESSED.get())
                .withIngredient(IngredientBuilder.block(Blocks.STONE))
                .withResult(ResultBuilder.blockTag(Tags.Blocks.ORES))
                .build(recipeProvider);
        //endregion

        //region Dissolution
        TransfigurationRecipeBuilder.block(TransfigurationTypes.DISSOLUTION.get())
                .withIngredient(IngredientBuilder.block(Blocks.STONE))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_DISSOLUTION))
                .build(recipeProvider);
        //endregion

        //region Fungal
        TransfigurationRecipeBuilder.block(TransfigurationTypes.FUNGAL.get())
                .withIngredient(IngredientBuilder.block(Blocks.GRASS_BLOCK))
                .withResult(ResultBuilder.block(Blocks.MYCELIUM))
                .build(recipeProvider);

        TransfigurationRecipeBuilder.entity(TransfigurationTypes.FUNGAL.get())
                .withIngredient(IngredientBuilder.entityType(EntityType.COW))
                .withResult(ResultBuilder.entityType(EntityType.MOOSHROOM))
                .build(recipeProvider);
        //endregion

        //region Mutandi
        TransfigurationRecipeBuilder.block(TransfigurationTypes.MUTANDI.get())
                .withIngredient(IngredientBuilder.blockTag(TransfigurationBlockTags.INPUTS_MUTANDI))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_MUTANDI))
                .build(recipeProvider);
        //endregion

        //region Netheri
        TransfigurationRecipeBuilder.entity(TransfigurationTypes.NETHERI.get())
                .withIngredient(IngredientBuilder.entityType(EntityType.PIG))
                .withResult(ResultBuilder.entityType(EntityType.HOGLIN))
                .build(recipeProvider);

        TransfigurationRecipeBuilder.entity(TransfigurationTypes.NETHERI.get())
                .withIngredient(IngredientBuilder.entityType(EntityType.HORSE))
                .withResult(ResultBuilder.entityType(EntityType.STRIDER))
                .build(recipeProvider);

        TransfigurationRecipeBuilder.block(TransfigurationTypes.NETHERI.get())
                .withIngredient(IngredientBuilder.block(Blocks.SAND))
                .withResult(ResultBuilder.block(Blocks.SOUL_SAND))
                .build(recipeProvider);

        TransfigurationRecipeBuilder.block(TransfigurationTypes.NETHERI.get())
                .withIngredient(IngredientBuilder.block(Blocks.COBBLESTONE))
                .withResult(ResultBuilder.block(Blocks.NETHERRACK))
                .build(recipeProvider);

        TransfigurationRecipeBuilder.block(TransfigurationTypes.NETHERI.get())
                .withIngredient(IngredientBuilder.block(Blocks.FIRE))
                .withResult(ResultBuilder.block(Blocks.SOUL_FIRE))
                .build(recipeProvider);
        //endregion

        //region Overni
        TransfigurationRecipeBuilder.block(TransfigurationTypes.OVERNI.get())
                .withIngredient(IngredientBuilder.block(Blocks.SOUL_SAND))
                .withResult(ResultBuilder.block(Blocks.SAND))
                .build(recipeProvider);

        TransfigurationRecipeBuilder.block(TransfigurationTypes.OVERNI.get())
                .withIngredient(IngredientBuilder.block(Blocks.NETHERRACK))
                .withResult(ResultBuilder.block(Blocks.COBBLESTONE))
                .build(recipeProvider);

        TransfigurationRecipeBuilder.entity(TransfigurationTypes.NETHERI.get())
                .withIngredient(IngredientBuilder.entityType(EntityType.HOGLIN))
                .withResult(ResultBuilder.entityType(EntityType.PIG))
                .build(recipeProvider);

        TransfigurationRecipeBuilder.entity(TransfigurationTypes.NETHERI.get())
                .withIngredient(IngredientBuilder.entityType(EntityType.STRIDER))
                .withResult(ResultBuilder.entityType(EntityType.HORSE))
                .build(recipeProvider);
        //endregion
    }
}
