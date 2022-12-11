package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Objects;

public class EntityTagResult extends Result {
    private final TagKey<EntityType<?>> tag;

    public EntityTagResult(TagKey<EntityType<?>> tag) {
        this.tag = tag;
    }

    public void handle(@Nonnull TransfigurationContainer<?> transfigurationContainer, double powerModifier) {
        Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags())
                .getTag(tag)
                .getRandomElement(transfigurationContainer.getLevel().random)
                .ifPresent(entityType -> EntityResult.summon(transfigurationContainer, entityType));
    }

    @Nonnull
    @Override
    public ResultInstance create() {
        return new AfterDoneResultInstance(1, this::handle);
    }

    @Override
    @Nonnull
    public ItemStack getRepresentation() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.ENTITY_TAG_RESULT_SERIALIZER.get();
    }

    public TagKey<EntityType<?>> getTag() {
        return tag;
    }
}
