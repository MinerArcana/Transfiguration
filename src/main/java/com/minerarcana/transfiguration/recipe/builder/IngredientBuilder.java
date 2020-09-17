package com.minerarcana.transfiguration.recipe.builder;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityIngredientSerializer;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.ITag;

import java.util.Objects;

public class IngredientBuilder {
    public static FinishedObject<BlockIngredientSerializer<?>> blockTag(ITag.INamedTag<Block> blockTag) {
        return new FinishedObject<>(TransfigurationRecipes.TAG_BLOCK_INGREDIENT_SERIALIZER.get(), blockTag::getName,
                jsonObject -> jsonObject.addProperty("tag", blockTag.getName().toString()));
    }

    public static FinishedObject<BlockIngredientSerializer<?>> block(Block block) {
        return new FinishedObject<>(TransfigurationRecipes.SINGLE_BLOCK_INGREDIENT_SERIALIZER.get(),
                block::getRegistryName, jsonObject -> jsonObject.addProperty("block", Objects.requireNonNull(
                block.getRegistryName()).toString()));
    }

    public static FinishedObject<EntityIngredientSerializer<?>> entityType(EntityType<?> entityType) {
        return new FinishedObject<>(TransfigurationRecipes.ENTITY_TYPE_ENTITY_INGREDIENT_SERIALIZER.get(),
                entityType::getRegistryName, jsonObject -> jsonObject.addProperty("entity", Objects.requireNonNull(
                entityType.getRegistryName()).toString()));
    }
}
