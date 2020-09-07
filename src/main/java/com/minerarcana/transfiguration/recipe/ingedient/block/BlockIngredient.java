package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import net.minecraft.block.BlockState;
import net.minecraftforge.common.util.NonNullPredicate;

public abstract class BlockIngredient implements NonNullPredicate<BlockState>,
        ISerializable<BlockIngredientSerializer<?>> {

}
