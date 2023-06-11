package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.fluids.IFluidBlock;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class Result implements ISerializable<ResultSerializer<?>> {

    @Nonnull
    public abstract ResultInstance create();

    @Nonnull
    public abstract ItemStack getRepresentation();

    public abstract Ingredient getView();

    public static Result fromBuffer(FriendlyByteBuf buffer) {
        ResultSerializer<?> serializer = buffer.readRegistryId();
        return serializer.parse(buffer);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Result> void toBuffer(FriendlyByteBuf buffer, T result) {
        ResultSerializer<T> resultSerializer = (ResultSerializer<T>) result.getSerializer();
        buffer.writeRegistryId(TransfigurationRecipes.getResultRegistry(), resultSerializer);
        resultSerializer.write(buffer, result);
    }

    protected ItemStack getBlockAsItem(Block block) {
        ItemStack itemStack;
        if (block instanceof IFluidBlock fluidBlock) {
            itemStack = fluidBlock.getFluid()
                    .getBucket()
                    .getDefaultInstance();
            itemStack.setHoverName(block.getName());
        } else if (block instanceof LiquidBlock liquidBlock) {
            itemStack = liquidBlock.getFluid()
                    .getBucket()
                    .getDefaultInstance();
            itemStack.setHoverName(block.getName());
        } else {
            itemStack = new ItemStack(block.asItem());
        }

        return itemStack;
    }
}
