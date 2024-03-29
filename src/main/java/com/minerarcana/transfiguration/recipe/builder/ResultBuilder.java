package com.minerarcana.transfiguration.recipe.builder;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.json.ObjectJson;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Objects;

public class ResultBuilder {
    public static FinishedObject<ResultSerializer<?>> block(Block block) {
        return new FinishedObject<>(TransfigurationRecipes.BLOCK_STATE_RESULT_SERIALIZER.get(),
                block::getRegistryName, jsonObject -> jsonObject.addProperty("block",
                Objects.requireNonNull(block.getRegistryName()).toString()));
    }

    public static FinishedObject<ResultSerializer<?>> blockTag(TagKey<Block> tag) {
        return new FinishedObject<>(
                TransfigurationRecipes.BLOCK_TAG_RESULT_SERIALIZER.get(),
                tag::location,
                jsonObject -> jsonObject.addProperty("tag", tag.location().toString())
        );
    }

    public static <T extends Comparable<T>> FinishedObject<ResultSerializer<?>> blockTagWithProperty(TagKey<Block> tag, Property<T> property, T value) {
        return new FinishedObject<>(
                TransfigurationRecipes.BLOCK_TAG_WITH_PROPERTY_RESULT_SERIALIZER.get(),
                tag::location,
                jsonObject -> {
                    jsonObject.addProperty("tag", tag.location().toString());
                    JsonObject properties = new JsonObject();
                    properties.addProperty(property.getName(), property.getName(value));
                }
        );
    }


    public static FinishedObject<ResultSerializer<?>> entityType(EntityType<?> entityType) {
        return new FinishedObject<>(TransfigurationRecipes.ENTITY_RESULT_SERIALIZER.get(),
                entityType::getRegistryName, jsonObject -> jsonObject.addProperty("entity",
                Objects.requireNonNull(entityType.getRegistryName()).toString()));
    }

    public static FinishedObject<ResultSerializer<?>> entityTypeTag(TagKey<EntityType<?>> entityTypeTag) {
        return new FinishedObject<>(
                TransfigurationRecipes.ENTITY_TAG_RESULT_SERIALIZER.get(),
                entityTypeTag::location,
                jsonObject -> jsonObject.addProperty("tag", entityTypeTag.location().toString())
        );
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
