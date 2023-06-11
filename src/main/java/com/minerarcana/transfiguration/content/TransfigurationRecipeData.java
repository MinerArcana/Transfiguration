package com.minerarcana.transfiguration.content;

import com.google.common.collect.Lists;
import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.recipe.builder.IngredientBuilder;
import com.minerarcana.transfiguration.recipe.builder.ResultBuilder;
import com.minerarcana.transfiguration.recipe.builder.TransfigurationRecipeBuilder;
import com.minerarcana.transfiguration.recipe.builder.WeightedResultBuilder;
import com.minerarcana.transfiguration.recipe.nbt.CompoundTagBuilder;
import com.minerarcana.transfiguration.recipe.nbt.NBTCopier;
import com.minerarcana.transfiguration.recipe.predicate.FluidStatePredicate;
import com.minerarcana.transfiguration.recipe.predicate.InStructurePredicate;
import com.minerarcana.transfiguration.recipe.predicate.OnEndExitPortalPredicate;
import com.minerarcana.transfiguration.recipe.predicate.PositionPredicate;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.Tags;
import org.apache.commons.lang3.Range;

public class TransfigurationRecipeData {

    public static <T extends TransfigurationType> void accursedRecipes(DataGenContext<TransfigurationType, T> context,
                                                                       RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.DIRT))
                .withResult(ResultBuilder.block(Blocks.COARSE_DIRT))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.GRASS_BLOCK))
                .withResult(ResultBuilder.block(Blocks.COARSE_DIRT))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.VILLAGER))
                .withResult(ResultBuilder.entityTypeTag(TransfigurationEntityTypeTags.OUTPUTS_ACCURSED))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.PIGLIN))
                .withResult(WeightedResultBuilder.of(Transfiguration.rl("piglin_accursed"))
                        .addResult(10, ResultBuilder.entityTypeTag(TransfigurationEntityTypeTags.OUTPUTS_ACCURSED))
                        .addResult(1, ResultBuilder.entityType(EntityType.ZOMBIFIED_PIGLIN))
                        .build()
                )
                .build(provider);
    }

    public static <T extends TransfigurationType> void blessedRecipes(DataGenContext<TransfigurationType, T> context,
                                                                      RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.ZOMBIE_VILLAGER))
                .withResult(ResultBuilder.entityType(EntityType.VILLAGER))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.ZOMBIE))
                .withResult(ResultBuilder.entityType(EntityType.VILLAGER))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.WITCH))
                .withResult(ResultBuilder.entityType(EntityType.VILLAGER))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.PILLAGER))
                .withResult(ResultBuilder.entityType(EntityType.VILLAGER))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.ILLUSIONER))
                .withResult(ResultBuilder.entityType(EntityType.VILLAGER))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.VINDICATOR))
                .withResult(ResultBuilder.entityType(EntityType.VILLAGER))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.ZOMBIFIED_PIGLIN))
                .withResult(ResultBuilder.entityType(EntityType.PIGLIN))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.STONE))
                .withResult(ResultBuilder.blockTag(Tags.Blocks.ORES))
                .build(provider);
    }

    public static void destabilizingRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                            RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.blockProperties(
                        false,
                        Range.between(0F, Float.MAX_VALUE),
                        Lists.newArrayList(
                                PushReaction.NORMAL,
                                PushReaction.PUSH_ONLY,
                                PushReaction.DESTROY,
                                PushReaction.IGNORE
                        )
                ))
                .withResult(ResultBuilder.fallingBlock())
                .build(provider);
    }

    public static void dissolutionRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                          RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.STONE))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_DISSOLUTION))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.SANDSTONE))
                .withResult(ResultBuilder.block(Blocks.SAND))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.WHITE_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.WHITE_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.ORANGE_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.ORANGE_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.MAGENTA_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.MAGENTA_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.LIGHT_BLUE_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.LIGHT_BLUE_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.YELLOW_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.YELLOW_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.LIME_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.LIME_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.PINK_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.PINK_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.GRAY_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.GRAY_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.LIGHT_GRAY_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.LIGHT_GRAY_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.CYAN_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.CYAN_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.PURPLE_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.PURPLE_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.BLUE_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.BLUE_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.BROWN_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.BROWN_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.GREEN_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.GREEN_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.RED_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.RED_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.BLACK_CONCRETE))
                .withResult(ResultBuilder.block(Blocks.BLACK_CONCRETE_POWDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.TERRACOTTA))
                .withResult(ResultBuilder.block(Blocks.CLAY))
                .build(provider);
    }

    public static void fungalRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                     RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.GRASS_BLOCK))
                .withResult(ResultBuilder.block(Blocks.MYCELIUM))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.COW))
                .withResult(ResultBuilder.entityType(EntityType.MOOSHROOM))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.blockTag(BlockTags.SAPLINGS))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_FUNGAL_MUSHROOM))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.TALL_GRASS))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_FUNGAL_MUSHROOM))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.blockTag(BlockTags.FLOWERS))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_FUNGAL_MUSHROOM))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.blockTag(BlockTags.LOGS))
                .withResult(ResultBuilder.block(Blocks.MUSHROOM_STEM))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.blockTag(BlockTags.LEAVES))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_FUNGI_MUSHROOM_LEAVES))
                .build(provider);
    }

    public static void mutandiRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                      RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.createBlock(context)
                .withPredicate(new FluidStatePredicate(Fluids.EMPTY))
                .withIngredient(IngredientBuilder.blockTag(TransfigurationBlockTags.INPUTS_MUTANDI))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_MUTANDI))
                .build(provider, Transfiguration.rl("outputs_mutandi_from_mutandi_transfiguration_inputs_mutandi_empty"));

        TransfigurationRecipeBuilder.createBlock(context)
                .withPredicate(new FluidStatePredicate(Fluids.WATER))
                .withIngredient(IngredientBuilder.blockTag(TransfigurationBlockTags.INPUTS_MUTANDI))
                .withResult(ResultBuilder.blockTagWithProperty(TransfigurationBlockTags.OUTPUTS_MUTANDI, BlockStateProperties.WATERLOGGED, true))
                .build(provider, Transfiguration.rl("outputs_mutandi_from_mutandi_transfiguration_inputs_mutandi_water"));
    }

    public static void netheriRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                      RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.PIG))
                .withResult(ResultBuilder.entityType(EntityType.HOGLIN))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.HORSE))
                .withResult(ResultBuilder.entityType(EntityType.STRIDER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.SAND))
                .withResult(ResultBuilder.block(Blocks.SOUL_SAND))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.COBBLESTONE))
                .withResult(ResultBuilder.block(Blocks.NETHERRACK))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.FIRE))
                .withResult(ResultBuilder.block(Blocks.SOUL_FIRE))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.STONE_BRICKS))
                .withResult(ResultBuilder.block(Blocks.NETHER_BRICKS))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.GRASS_BLOCK))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_NETHERI_GRASS))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.DIRT))
                .withResult(ResultBuilder.block(Blocks.SOUL_SOIL))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.COARSE_DIRT))
                .withResult(ResultBuilder.block(Blocks.SOUL_SOIL))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.GRASS))
                .withResult(ResultBuilder.block(Blocks.NETHER_SPROUTS))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.CAMPFIRE))
                .withResult(ResultBuilder.block(Blocks.SOUL_CAMPFIRE))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.blockTag(TransfigurationBlockTags.INPUTS_NETHERI_MUSHROOM))
                .withViewIngredient(Ingredient.of(TransfigurationItemTags.INPUTS_NETHERI_MUSHROOM))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_NETHERI_MUSHROOM))
                .withViewResult(Ingredient.of(TransfigurationItemTags.OUTPUTS_NETHERI_MUSHROOM))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.SLIME))
                .withResult(ResultBuilder.entityType(EntityType.MAGMA_CUBE))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.STONE))
                .withResult(ResultBuilder.block(Blocks.BASALT))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.CRACKED_STONE_BRICKS))
                .withResult(ResultBuilder.block(Blocks.CRACKED_NETHER_BRICKS))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withPredicate(new PositionPredicate(Direction.Axis.Y, -20, Integer.MAX_VALUE))
                .withIngredient(IngredientBuilder.matches(Blocks.DEEPSLATE))
                .withResult(WeightedResultBuilder.of(Transfiguration.rl("deepslate_out"))
                        .addResult(3, ResultBuilder.block(Blocks.MAGMA_BLOCK))
                        .addResult(1, ResultBuilder.block(Blocks.LAVA))
                        .build()
                )
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.POINTED_DRIPSTONE))
                .withResult(ResultBuilder.block(Blocks.GLOWSTONE))
                .build(provider);

    }

    public static void overniRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                     RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.HOGLIN))
                .withResult(ResultBuilder.entityType(EntityType.PIG))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.STRIDER))
                .withResult(ResultBuilder.entityType(EntityType.HORSE))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.SOUL_SAND))
                .withResult(ResultBuilder.block(Blocks.SAND))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.NETHERRACK))
                .withResult(ResultBuilder.block(Blocks.COBBLESTONE))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.SOUL_FIRE))
                .withResult(ResultBuilder.block(Blocks.FIRE))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.NETHER_BRICKS))
                .withResult(ResultBuilder.block(Blocks.STONE_BRICKS))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.CRACKED_NETHER_BRICKS))
                .withResult(ResultBuilder.block(Blocks.CRACKED_STONE_BRICKS))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_NETHERI_GRASS))
                .withResult(ResultBuilder.block(Blocks.GRASS_BLOCK))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.SOUL_SOIL))
                .withResult(WeightedResultBuilder.of(Transfiguration.rl("overworld_soils"))
                        .addResult(3, ResultBuilder.block(Blocks.DIRT))
                        .addResult(1, ResultBuilder.block(Blocks.COARSE_DIRT))
                        .build()
                )
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.NETHER_SPROUTS))
                .withResult(ResultBuilder.block(Blocks.GRASS_BLOCK))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.SOUL_CAMPFIRE))
                .withResult(ResultBuilder.block(Blocks.CAMPFIRE))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.blockTag(TransfigurationBlockTags.OUTPUTS_NETHERI_MUSHROOM))
                .withViewIngredient(Ingredient.of(TransfigurationItemTags.OUTPUTS_NETHERI_MUSHROOM))
                .withResult(ResultBuilder.blockTag(TransfigurationBlockTags.INPUTS_NETHERI_MUSHROOM))
                .withViewResult(Ingredient.of(TransfigurationItemTags.INPUTS_NETHERI_MUSHROOM))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.MAGMA_CUBE))
                .withResult(ResultBuilder.entityType(EntityType.SLIME))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.BASALT))
                .withResult(ResultBuilder.block(Blocks.STONE))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context)
                .withIngredient(IngredientBuilder.matches(EntityType.MOOSHROOM))
                .withResult(ResultBuilder.entityType(EntityType.COW))
                .build(provider);
    }

    public static void animationRecipes(DataGenContext<TransfigurationType, TransfigurationType> context,
                                        RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.STONE))
                .withResult(ResultBuilder.block(Blocks.INFESTED_STONE))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.CHISELED_STONE_BRICKS))
                .withResult(ResultBuilder.block(Blocks.INFESTED_CHISELED_STONE_BRICKS))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.COBBLESTONE))
                .withResult(ResultBuilder.block(Blocks.INFESTED_COBBLESTONE))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.CRACKED_STONE_BRICKS))
                .withResult(ResultBuilder.block(Blocks.INFESTED_CRACKED_STONE_BRICKS))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.MOSSY_STONE_BRICKS))
                .withResult(ResultBuilder.block(Blocks.INFESTED_MOSSY_STONE_BRICKS))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context)
                .withIngredient(IngredientBuilder.matches(Blocks.STONE_BRICKS))
                .withResult(ResultBuilder.block(Blocks.INFESTED_STONE_BRICKS))
                .build(provider);
    }

    public static void xenoiRecipes(DataGenContext<TransfigurationType, TransfigurationType> context, RegistrateRecipeProvider provider) {
        TransfigurationRecipeBuilder.createEntity(context.get())
                .withIngredient(IngredientBuilder.entityTag(TransfigurationEntityTypeTags.VILLAGER))
                .withResult(ResultBuilder.entityType(EntityType.ENDERMAN))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context.get())
                .withIngredient(IngredientBuilder.matches(EntityType.CHICKEN))
                .withResult(ResultBuilder.entityType(EntityType.PHANTOM))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context.get())
                .withIngredient(IngredientBuilder.matches(EntityType.WITHER_SKELETON))
                .withResult(ResultBuilder.entityType(EntityType.END_CRYSTAL))
                .withViewResult(Ingredient.of(Items.END_CRYSTAL))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context.get())
                .withIngredient(IngredientBuilder.matches(EntityType.STRIDER))
                .withResult(ResultBuilder.entityType(EntityType.SHULKER))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context.get())
                .withIngredient(IngredientBuilder.matches(Blocks.PURPUR_BLOCK))
                .withResult(ResultBuilder.chance(0.5F, ResultBuilder.block(Blocks.SHULKER_BOX)))
                .build(provider);

        TransfigurationRecipeBuilder.createEntity(context.get())
                .withPredicate(new InStructurePredicate(BuiltinStructures.END_CITY))
                .withIngredient(IngredientBuilder.matches(EntityType.ITEM_FRAME))
                .withViewIngredient(Ingredient.of(Items.ITEM_FRAME))
                .withResult(ResultBuilder.entityType(
                        EntityType.ITEM_FRAME,
                        CompoundTagBuilder.start()
                                .with("Item", new ItemStack(Items.ELYTRA))
                                .build(),
                        NBTCopier.builder()
                                .copy("Facing")
                                .copy("Invisible")
                                .copy("Fixed")
                                .build()
                ))
                .withViewResult(Ingredient.of(Items.ELYTRA))
                .build(provider);

        TransfigurationRecipeBuilder.createBlock(context.get())
                .withPredicate(new OnEndExitPortalPredicate())
                .withIngredient(IngredientBuilder.matches(Blocks.BEACON))
                .withResult(ResultBuilder.block(Blocks.DRAGON_EGG))
                .build(provider);
    }
}
