package com.minerarcana.transfiguration.recipe.ingedient;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FrogspawnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.stream.Stream;

public class TagIngredient extends BasicIngredient {
    private final TagKey<Block> blockTag;
    private final TagKey<EntityType<?>> entityTag;

    private final Lazy<Ingredient> ingredient;

    public TagIngredient(TagKey<Block> blockTag, TagKey<EntityType<?>> entityTag) {
        this.blockTag = blockTag;
        this.entityTag = entityTag;
        this.ingredient = Lazy.of(() -> Ingredient.of(Stream.concat(
                Objects.requireNonNull(ForgeRegistries.BLOCKS.tags())
                        .getTag(blockTag)
                        .stream(),
                Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags())
                        .getTag(entityTag)
                        .stream()
                        .map(ForgeSpawnEggItem::fromEntityType)
                        .filter(Objects::nonNull)
        ).toArray(ItemLike[]::new)));
    }

    @Override
    public boolean test(@Nonnull Entity entity) {
        return entityTag != null && entity.getType().is(entityTag);
    }

    @Override
    public boolean test(@Nonnull BlockState blockState) {
        return blockTag != null && blockState.is(blockTag);
    }

    @Nonnull
    @Override
    public BasicIngredientSerializer<?> getSerializer() {
        return TransfigurationRecipes.TAG_INGREDIENT_SERIALIZER.get();
    }

    public TagKey<Block> getBlockTag() {
        return blockTag;
    }

    public TagKey<EntityType<?>> getEntityTag() {
        return entityTag;
    }

    @Override
    public Ingredient asItemIngredient() {
        return this.ingredient.get();
    }
}
