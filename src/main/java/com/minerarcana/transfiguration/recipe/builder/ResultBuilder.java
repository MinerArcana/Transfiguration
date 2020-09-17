package com.minerarcana.transfiguration.recipe.builder;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.ITag;

import java.util.Objects;

public class ResultBuilder {
    public static FinishedObject<ResultSerializer<?>> block(Block block) {
        return new FinishedObject<>(TransfigurationRecipes.BLOCK_STATE_RESULT_SERIALIZER.get(),
                block::getRegistryName, jsonObject -> jsonObject.addProperty("block",
                Objects.requireNonNull(block.getRegistryName()).toString()));
    }

    public static FinishedObject<ResultSerializer<?>> entityType(EntityType<?> entityType) {
        return new FinishedObject<>(TransfigurationRecipes.ENTITY_RESULT_SERIALIZER.get(),
                entityType::getRegistryName, jsonObject -> jsonObject.addProperty("entity",
                Objects.requireNonNull(entityType.getRegistryName()).toString()));
    }

    public static FinishedObject<ResultSerializer<?>> entityTypeTag(ITag.INamedTag<EntityType<?>> entityTypeTag) {
        return new FinishedObject<>(TransfigurationRecipes.ENTITY_TAG_RESULT_SERIALIZER.get(),
                entityTypeTag::getName, jsonObject -> jsonObject.addProperty("tag", entityTypeTag.getName()
                .toString()));
    }
}
