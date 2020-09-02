package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;

public interface ITransfiguring {
    TransfigurationType getType();

    void afterTransfiguration(ItemStack itemStack, @Nonnull LivingEntity livingEntity, Hand hand);
}
