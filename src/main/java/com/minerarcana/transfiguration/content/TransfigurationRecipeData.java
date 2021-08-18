package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.recipe.builder.IngredientBuilder;
import com.minerarcana.transfiguration.recipe.builder.ResultBuilder;
import com.minerarcana.transfiguration.recipe.builder.TransfigurationRecipeBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.BlockTags;
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

    public static void dissolutionRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                          RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.STONE))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_DISSOLUTION))
                .build(provider);
    }

    public static void fungalRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                     RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.GRASS_BLOCK))
                .withResult(ResultBuilder.block(Blocks.MYCELIUM))
                .build(provider);

        TransfigurationRecipeBuilder.entity(context)
                .withIngredient(IngredientBuilder.entityType(EntityType.COW))
                .withResult(ResultBuilder.entityType(EntityType.MOOSHROOM))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.blockTag(BlockTags.SAPLINGS))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_FUNGAL_MUSHROOM))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.blockTag(BlockTags.FLOWERS))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_FUNGAL_MUSHROOM))
                .build(provider);
    }

    public static void mutandiRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                      RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.blockTag(TransfigurationBlockTags.INPUTS_MUTANDI))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_MUTANDI))
                .build(provider);
    }

    public static void netheriRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                      RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.entity(context)
                .withIngredient(IngredientBuilder.entityType(EntityType.PIG))
                .withResult(ResultBuilder.entityType(EntityType.HOGLIN))
                .build(provider);

        TransfigurationRecipeBuilder.entity(context)
                .withIngredient(IngredientBuilder.entityType(EntityType.HORSE))
                .withResult(ResultBuilder.entityType(EntityType.STRIDER))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.SAND))
                .withResult(ResultBuilder.block(Blocks.SOUL_SAND))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.COBBLESTONE))
                .withResult(ResultBuilder.block(Blocks.NETHERRACK))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.FIRE))
                .withResult(ResultBuilder.block(Blocks.SOUL_FIRE))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.STONE_BRICKS))
                .withResult(ResultBuilder.block(Blocks.NETHER_BRICKS))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.GRASS_BLOCK))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_NETHERI_GRASS))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.DIRT))
                .withResult(ResultBuilder.block(Blocks.SOUL_SOIL))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.GRASS))
                .withResult(ResultBuilder.block(Blocks.NETHER_SPROUTS))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.CAMPFIRE))
                .withResult(ResultBuilder.block(Blocks.SOUL_CAMPFIRE))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.blockTag(TransfigurationBlockTags.INPUTS_NETHERI_MUSHROOM))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_NETHERI_MUSHROOM))
                .build(provider);

        TransfigurationRecipeBuilder.entity(context)
                .withIngredient(IngredientBuilder.entityType(EntityType.SLIME))
                .withResult(ResultBuilder.entityType(EntityType.MAGMA_CUBE))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.STONE))
                .withResult(ResultBuilder.block(Blocks.BASALT))
                .build(provider);
    }

    public static void overniRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                     RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.entity(context)
                .withIngredient(IngredientBuilder.entityType(EntityType.HOGLIN))
                .withResult(ResultBuilder.entityType(EntityType.PIG))
                .build(provider);

        TransfigurationRecipeBuilder.entity(context)
                .withIngredient(IngredientBuilder.entityType(EntityType.STRIDER))
                .withResult(ResultBuilder.entityType(EntityType.HORSE))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.SOUL_SAND))
                .withResult(ResultBuilder.block(Blocks.SAND))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.NETHERRACK))
                .withResult(ResultBuilder.block(Blocks.COBBLESTONE))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.SOUL_FIRE))
                .withResult(ResultBuilder.block(Blocks.FIRE))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.NETHER_BRICKS))
                .withResult(ResultBuilder.block(Blocks.STONE_BRICKS))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_NETHERI_GRASS))
                .withResult(ResultBuilder.block(Blocks.GRASS_BLOCK))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.SOUL_SOIL))
                .withResult(ResultBuilder.block(Blocks.DIRT))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.NETHER_SPROUTS))
                .withResult(ResultBuilder.block(Blocks.GRASS_BLOCK))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.SOUL_CAMPFIRE))
                .withResult(ResultBuilder.block(Blocks.CAMPFIRE))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_NETHERI_MUSHROOM))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.INPUTS_NETHERI_MUSHROOM))
                .build(provider);

        TransfigurationRecipeBuilder.entity(context)
                .withIngredient(IngredientBuilder.entityType(EntityType.MAGMA_CUBE))
                .withResult(ResultBuilder.entityType(EntityType.SLIME))
                .build(provider);

        TransfigurationRecipeBuilder.block(context)
                .withIngredient(IngredientBuilder.block(Blocks.BASALT))
                .withResult(ResultBuilder.block(Blocks.STONE))
                .build(provider);
    }
}
