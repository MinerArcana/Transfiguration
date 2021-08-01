package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.api.TransfigurationType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;

public interface ITransfiguring {
    TransfigurationType getType(ItemStack itemStack);

    default double getPowerModifier(ItemStack itemStack) {
        return 1.0F;
    }

    default double getTimeModifier(ItemStack itemStack) {
        return 1.0F;
    }

    void afterTransfiguration(ItemStack itemStack, @Nonnull LivingEntity livingEntity, Hand hand);
}
