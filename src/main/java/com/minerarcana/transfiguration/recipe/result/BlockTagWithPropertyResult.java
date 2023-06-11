package com.minerarcana.transfiguration.recipe.result;

import com.google.common.base.Suppliers;
import com.google.gson.JsonPrimitive;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import com.mojang.serialization.JsonOps;
import net.minecraft.Util;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

public class BlockTagWithPropertyResult extends Result {
    private final TagKey<Block> tag;
    private final Map<String, String> properties;
    private final Supplier<List<BlockState>> blockStates;
    private final Lazy<Ingredient> view;

    public BlockTagWithPropertyResult(TagKey<Block> tag, Map<String, String> properties) {
        this.tag = tag;
        this.properties = properties;
        this.blockStates = Suppliers.memoize(this::setupBlockStates);
        this.view = Lazy.of(() -> Ingredient.of(Objects.requireNonNull(ForgeRegistries.BLOCKS.tags())
                .getTag(this.getTag())
                .stream()
                .map(this::getBlockAsItem)
                .toArray(ItemStack[]::new)
        ));
    }

    private List<BlockState> setupBlockStates() {
        ITag<Block> blockTag = Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).getTag(tag);
        List<BlockState> blockStatesToSetup = new ArrayList<>();
        for (Block block : blockTag) {
            StateDefinition<Block, BlockState> stateStateDefinition = block.getStateDefinition();
            Iterator<Map.Entry<String, String>> iterator = properties.entrySet().iterator();
            BlockState blockState = block.defaultBlockState();
            while (iterator.hasNext() && blockState != null) {
                Map.Entry<String, String> entry = iterator.next();
                Property<?> property = stateStateDefinition.getProperty(entry.getKey());
                if (property != null) {
                    if (property.getValue(entry.getValue()).isPresent()) {
                        blockState = property.parseValue(JsonOps.INSTANCE, blockState, new JsonPrimitive(entry.getValue()))
                                .get()
                                .orThrow();
                    } else {
                        blockState = null;
                    }
                } else {
                    blockState = null;
                }
            }
            if (blockState != null) {
                blockStatesToSetup.add(blockState);
            }
        }
        return blockStatesToSetup;
    }

    public void handle(@Nonnull TransfigurationContainer<?> transfigurationContainer, double powerModifier) {
        Util.getRandomSafe(this.blockStates.get(), transfigurationContainer.getLevel().getRandom())
                .ifPresent(blockState -> transfigurationContainer.getLevel()
                        .setBlockAndUpdate(
                                transfigurationContainer.getTargetedPos(),
                                blockState
                        )
                );
    }

    @Nonnull
    @Override
    public ResultInstance create() {
        return new AfterDoneResultInstance(this::handle);
    }

    @NotNull
    @Override
    public ItemStack getRepresentation() {
        return ItemStack.EMPTY;
    }

    @Override
    public Ingredient getView() {
        return this.view.get();
    }

    @NotNull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.BLOCK_TAG_WITH_PROPERTY_RESULT_SERIALIZER.get();
    }

    public TagKey<Block> getTag() {
        return tag;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
