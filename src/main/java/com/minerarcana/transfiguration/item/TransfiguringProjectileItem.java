package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TransfiguringProjectileItem extends Item implements ITransfiguring {
    public TransfiguringProjectileItem(Properties properties) {
        super(properties);
    }

    public ItemStack withTransfigurationType(TransfigurationType type) {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", Objects.requireNonNull(type.getRegistryName()).toString());
        ItemStack itemStack = new ItemStack(this);
        itemStack.setTag(tag);
        return itemStack;
    }

    public ItemStack withTypeAndStats(TransfigurationType type, double powerModifier, double timeModifier) {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", Objects.requireNonNull(type.getRegistryName()).toString());
        tag.putDouble("power", powerModifier);
        tag.putDouble("time", timeModifier);
        ItemStack itemStack = new ItemStack(this);
        itemStack.setTag(tag);
        return itemStack;
    }

    public static TransfigurationType getTransfigurationType(ItemStack itemStack) {
        if (itemStack.getTag() != null) {
            String typeName = itemStack.getTag().getString("type");
            return TransfigurationTypes.REGISTRY.get().getValue(new ResourceLocation(typeName));
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

    @Override
    public TransfigurationType getType(ItemStack itemStack) {
        return getTransfigurationType(itemStack);
    }

    @Override
    public double getPowerModifier(ItemStack itemStack) {
        return itemStack.getTag() != null && itemStack.getTag().contains("power")? itemStack.getTag().getDouble("power") : 1.0F;
    }

    @Override
    public double getTimeModifier(ItemStack itemStack) {
        return itemStack.getTag() != null && itemStack.getTag().contains("time") ? itemStack.getTag().getDouble("time") : 1.0F;
    }

    @Override
    public void afterTransfiguration(ItemStack itemStack, @Nonnull LivingEntity livingEntity, InteractionHand hand) {

    }
}
