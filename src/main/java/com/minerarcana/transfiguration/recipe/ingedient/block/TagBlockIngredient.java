package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TagBlockIngredient extends BlockIngredient {
    private final ITag<Block> tag;

    public TagBlockIngredient(ITag<Block> tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(BlockState blockState) {
        return tag.contains(blockState.getBlock());
    }

    @Nonnull
    public ResourceLocation getName() {
        return Objects.requireNonNull(TagCollectionManager.getManager().getBlockTags().getDirectIdFromTag(tag));
    }

    @Override
    @Nonnull
    public BlockIngredientSerializer<?> getSerializer() {
        return TransfigurationRecipes.TAG_BLOCK_INGREDIENT_SERIALIZER.get();
    }

    public static TagBlockIngredient create(String name) {
        return create(new ResourceLocation(name));
    }

    public static TagBlockIngredient create(ResourceLocation resourceLocation) {
        return new TagBlockIngredient(TagCollectionManager.getManager().getBlockTags().getTagByID(resourceLocation));
    }
}