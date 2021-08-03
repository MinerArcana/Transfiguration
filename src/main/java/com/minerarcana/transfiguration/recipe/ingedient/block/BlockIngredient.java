package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.NonNullPredicate;

public abstract class BlockIngredient implements NonNullPredicate<BlockState>,
        ISerializable<BlockIngredientSerializer<?>> {

    public static BlockIngredient fromBuffer(PacketBuffer packetBuffer) {
        BlockIngredientSerializer<?> serializer = packetBuffer.readRegistryId();
        return  serializer.parse(packetBuffer);
    }

    public static void toBuffer(BlockIngredient blockIngredient, PacketBuffer packetBuffer) {
        BlockIngredientSerializer<?> blockIngredientSerializer = blockIngredient.getSerializer();
        packetBuffer.writeRegistryId(blockIngredientSerializer);
    }
}
