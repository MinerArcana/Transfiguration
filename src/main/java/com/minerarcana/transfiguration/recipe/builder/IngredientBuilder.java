package com.minerarcana.transfiguration.recipe.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredientSerializer;
import com.minerarcana.transfiguration.recipe.json.ObjectJson;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.PushReaction;
import org.apache.commons.lang3.Range;

import java.util.List;
import java.util.Objects;

public class IngredientBuilder {
    public static FinishedObject<BasicIngredientSerializer<?>> blockTag(Tag.Named<Block> blockTag) {
        return new FinishedObject<>(
                TransfigurationRecipes.TAG_INGREDIENT_SERIALIZER.get(),
                blockTag::getName,
                jsonObject -> jsonObject.addProperty("block", blockTag.getName().toString())
        );
    }

    public static FinishedObject<BasicIngredientSerializer<?>> matches(Block block) {
        return new FinishedObject<>(TransfigurationRecipes.MATCH_INGREDIENT_SERIALIZER.get(),
                block::getRegistryName,
                jsonObject -> jsonObject.addProperty(
                        "block",
                        Objects.requireNonNull(block.getRegistryName()).toString()
                )
        );
    }

    public static FinishedObject<BasicIngredientSerializer<?>> matches(EntityType<?> entityType) {
        return new FinishedObject<>(
                TransfigurationRecipes.MATCH_INGREDIENT_SERIALIZER.get(),
                entityType::getRegistryName,
                jsonObject -> jsonObject.addProperty(
                        "entity",
                        Objects.requireNonNull(entityType.getRegistryName()).toString()
                )
        );
    }

    @SafeVarargs
    public static FinishedObject<BasicIngredientSerializer<?>> and(FinishedObject<BasicIngredientSerializer<?>>... inputs) {
        return new FinishedObject<>(
                TransfigurationRecipes.AND_INGREDIENT_SERIALIZER.get(),
                () -> Transfiguration.rl("and"),
                jsonObject -> jsonObject.add("values", ObjectJson.writeArray(FinishedObject::getJson, inputs))
        );
    }

    public static FinishedObject<BasicIngredientSerializer<?>> blockProperties(Boolean blockEntity,
                                                                               Range<Float> hardness,
                                                                               List<PushReaction> pushReactionList) {
        return new FinishedObject<>(
                TransfigurationRecipes.BLOCK_PROPERTIES_INGREDIENT_SERIALIZER.get(),
                () -> Transfiguration.rl("block_properties"),
                jsonObject -> {
                    if (blockEntity != null) {
                        jsonObject.addProperty("blockEntity", blockEntity);
                    }
                    if (hardness != null) {
                        JsonObject rangeObject = new JsonObject();
                        if (hardness.getMinimum() > 0) {
                            rangeObject.addProperty("min", hardness.getMinimum());
                        }
                        if (hardness.getMaximum() < Float.MAX_VALUE) {
                            rangeObject.addProperty("max", hardness.getMaximum());
                        }
                    }
                    if (pushReactionList != null && !pushReactionList.isEmpty()) {
                        JsonArray pushReactions = new JsonArray();
                        pushReactionList.forEach(pushReaction -> pushReactions.add(pushReaction.name()));
                    }
                }
        );
    }
}
