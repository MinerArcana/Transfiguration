package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class EntityTagResult extends Result {
    private final Tag<EntityType<?>> tag;

    public EntityTagResult(Tag<EntityType<?>> tag) {
        this.tag = tag;
    }

    public void handle(@Nonnull TransfigurationContainer<?> transfigurationContainer, double powerModifier) {
        EntityType<?> entityType = tag.getRandomElement(transfigurationContainer.getLevel().random);
        EntityResult.summon(transfigurationContainer, entityType);
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

    public Tag<EntityType<?>> getTag() {
        return tag;
    }
}
