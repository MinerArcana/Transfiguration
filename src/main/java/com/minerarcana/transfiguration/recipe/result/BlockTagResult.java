package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class BlockTagResult extends Result {
    private final TagKey<Block> tag;
    private final Lazy<List<ItemStack>> representations;

    public BlockTagResult(TagKey<Block> tag) {
        this.tag = tag;
        this.representations = Lazy.of(() -> Objects.requireNonNull(ForgeRegistries.BLOCKS.tags())
                .getTag(this.getTag())
                .stream()
                .map(this::getBlockAsItem)
                .toList()
        );
    }

    public void handle(@Nonnull TransfigurationContainer<?> transfigurationContainer, double powerModifier) {
        Objects.requireNonNull(ForgeRegistries.BLOCKS.tags())
                .getTag(tag)
                .getRandomElement(transfigurationContainer.getLevel().random)
                .ifPresent(block -> transfigurationContainer.getLevel()
                        .setBlockAndUpdate(
                                transfigurationContainer.getTargetedPos(),
                                block.defaultBlockState()
                        )
                );
    }

    @Nonnull
    @Override
    public ResultInstance create() {
        return new AfterDoneResultInstance(this::handle);
    }

    @Nonnull
    @Override
    public ItemStack getRepresentation() {
        return ItemStack.EMPTY;
    }

    @Override
    public List<ItemStack> getAllRepresentations() {
        return this.representations.get();
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.BLOCK_TAG_RESULT_SERIALIZER.get();
    }

    public TagKey<Block> getTag() {
        return tag;
    }
}
