package com.minerarcana.transfiguration.recipe.ingedient.block;

import com.minerarcana.transfiguration.recipe.serializer.ISerializable;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.NonNullPredicate;

public abstract class BlockIngredient implements NonNullPredicate<BlockState>,
        ISerializable<BlockIngredientSerializer<?>> {

    public static BlockIngredient fromBuffer(PacketBuffer packetBuffer) {
        BlockIngredientSerializer<?> serializer = packetBuffer.readRegistryId();
        return serializer.parse(packetBuffer);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockIngredient> void toBuffer(T blockIngredient, PacketBuffer packetBuffer) {
        BlockIngredientSerializer<T> blockIngredientSerializer = (BlockIngredientSerializer<T>) blockIngredient.getSerializer();
        packetBuffer.writeRegistryId(blockIngredientSerializer);
        blockIngredientSerializer.write(packetBuffer, blockIngredient);
    }
}
