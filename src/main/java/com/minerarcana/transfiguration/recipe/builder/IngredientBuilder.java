package com.minerarcana.transfiguration.recipe.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredientSerializer;
import com.minerarcana.transfiguration.recipe.json.ObjectJson;
import com.minerarcana.transfiguration.util.RegistryHelpers;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.Range;

import java.util.List;

public class IngredientBuilder {
    public static FinishedObject<BasicIngredientSerializer<?>> blockTag(TagKey<Block> blockTag) {
        return new FinishedObject<>(
                TransfigurationRecipes.TAG_INGREDIENT_SERIALIZER.get(),
                blockTag::location,
                jsonObject -> jsonObject.addProperty("block", blockTag.location().toString())
        );
    }

    public static FinishedObject<BasicIngredientSerializer<?>> matches(Block block) {
        return new FinishedObject<>(
                TransfigurationRecipes.MATCH_INGREDIENT_SERIALIZER.get(),
                RegistryHelpers.supplyRegistryName(ForgeRegistries.BLOCKS, block),
                jsonObject -> jsonObject.addProperty(
                        "block",
                        RegistryHelpers.getRegistryName(ForgeRegistries.BLOCKS, block).toString()
                )
        );
    }

    public static FinishedObject<BasicIngredientSerializer<?>> matches(EntityType<?> entityType) {
        return matches(entityType, null);
    }

    public static FinishedObject<BasicIngredientSerializer<?>> matches(EntityType<?> entityType, EntityPredicate predicate) {
        return new FinishedObject<>(
                TransfigurationRecipes.MATCH_INGREDIENT_SERIALIZER.get(),
                RegistryHelpers.supplyRegistryName(ForgeRegistries.ENTITY_TYPES, entityType),
                jsonObject -> {
                    jsonObject.addProperty(
                            "entity",
                            RegistryHelpers.getRegistryName(ForgeRegistries.ENTITY_TYPES, entityType).toString()
                    );
                    if (predicate != null) {
                        jsonObject.add("predicate", predicate.serializeToJson());
                    }
                }
        );
    }

    public static FinishedObject<BasicIngredientSerializer<?>> entityTag(TagKey<EntityType<?>> entityTypeTagKey) {
        return new FinishedObject<>(
                TransfigurationRecipes.TAG_INGREDIENT_SERIALIZER.get(),
                entityTypeTagKey::location,
                jsonObject -> jsonObject.addProperty("entity", entityTypeTagKey.location().toString())
        );
    }

    @SafeVarargs
    @SuppressWarnings("unused")
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
