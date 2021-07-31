package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;

import javax.annotation.Nonnull;

public class EntityTagResult extends Result {
    private final ITag<EntityType<?>> tag;

    public EntityTagResult(ITag<EntityType<?>> tag) {
        this.tag = tag;
    }

    public void handle(@Nonnull TransfigurationContainer<?> transfigurationContainer, double powerModifier) {
        EntityType<?> entityType = tag.getRandomElement(transfigurationContainer.getWorld().rand);
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

    public ITag<EntityType<?>> getTag() {
        return tag;
    }
}
