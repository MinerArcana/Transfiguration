package com.minerarcana.transfiguration.recipe.builder;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.json.ObjectJson;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

public class ResultBuilder {
    public static FinishedObject<ResultSerializer<?>> block(Block block) {
        return new FinishedObject<>(TransfigurationRecipes.BLOCK_STATE_RESULT_SERIALIZER.get(),
                block::getRegistryName, jsonObject -> jsonObject.addProperty("block",
                Objects.requireNonNull(block.getRegistryName()).toString()));
    }

    public static FinishedObject<ResultSerializer<?>> blockTag(Tag.Named<Block> tag) {
        return new FinishedObject<>(TransfigurationRecipes.BLOCK_TAG_RESULT_SERIALIZER.get(),
                tag::getName, jsonObject -> jsonObject.addProperty("tag",
                tag.getName().toString()));
    }

    public static FinishedObject<ResultSerializer<?>> entityType(EntityType<?> entityType) {
        return new FinishedObject<>(TransfigurationRecipes.ENTITY_RESULT_SERIALIZER.get(),
                entityType::getRegistryName, jsonObject -> jsonObject.addProperty("entity",
                Objects.requireNonNull(entityType.getRegistryName()).toString()));
    }

    public static FinishedObject<ResultSerializer<?>> entityTypeTag(Tag.Named<EntityType<?>> entityTypeTag) {
        return new FinishedObject<>(TransfigurationRecipes.ENTITY_TAG_RESULT_SERIALIZER.get(),
                entityTypeTag::getName, jsonObject -> jsonObject.addProperty("tag", entityTypeTag.getName()
                .toString()));
    }

    public static FinishedObject<ResultSerializer<?>> itemStack(ItemStack itemStack) {
        return new FinishedObject<>(
                TransfigurationRecipes.ITEM_RESULT_SERIALIZER.get(),
                itemStack.getItem()::getRegistryName,
                jsonObject -> jsonObject.add("itemStack", ObjectJson.writeItemStack(itemStack))
        );
    }

    public static FinishedObject<ResultSerializer<?>> chance(float chance, FinishedObject<ResultSerializer<?>> result) {
        return new FinishedObject<>(
                TransfigurationRecipes.CHANCE_RESULT_SERIALIZER.get(),
                result::getId,
                jsonObject -> {
                    jsonObject.addProperty("chance", chance);
                    jsonObject.add("result", result.getJson());
                }
        );
    }

    public static FinishedObject<ResultSerializer<?>> fallingBlock() {
        return new FinishedObject<>(
                TransfigurationRecipes.FALLING_RESULT_SERIALIZER.get(),
                TransfigurationRecipes.FALLING_RESULT_SERIALIZER::getId,
                jsonObject -> {
                }
        );
    }
}
