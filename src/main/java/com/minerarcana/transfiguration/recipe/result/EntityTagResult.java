package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
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

    @Override
    @Nonnull
    public ActionResultType handle(@Nonnull TransfigurationContainer<?> transfigurationContainer) {
        EntityType<?> entityType = tag.getRandomElement(transfigurationContainer.getWorld().rand);
        return EntityResult.summon(transfigurationContainer, entityType);
    }

    @Override
    @Nonnull
    public ItemStack getOutputRepresentation() {
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
