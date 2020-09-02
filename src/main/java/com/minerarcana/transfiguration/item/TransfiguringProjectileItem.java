package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class TransfiguringProjectileItem extends Item {
    public TransfiguringProjectileItem(Properties properties) {
        super(properties);
    }

    public ItemStack withTransfigurationType(TransfigurationType type) {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("type", Objects.requireNonNull(type.getRegistryName()).toString());
        ItemStack itemStack = new ItemStack(this);
        itemStack.setTag(tag);
        return itemStack;
    }

    public static TransfigurationType getTransfigurationType(ItemStack itemStack) {
        if (itemStack.getTag() != null) {
            String typeName = itemStack.getTag().getString("type");
            return Transfiguration.transfigurationTypes.getValue(new ResourceLocation(typeName));
        } else {
            return null;
        }
    }

    public static int getColor(ItemStack itemStack, int layer) {
        if (layer == 0) {
            TransfigurationType type = getTransfigurationType(itemStack);
            if (type != null) {
                return type.getPrimaryColor();
            }
        }
        return -1;
    }
}
