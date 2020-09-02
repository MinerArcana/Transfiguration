package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class TransfiguringDustItem extends TransfiguringItem {
    public TransfiguringDustItem(Supplier<TransfigurationType> type, Properties properties) {
        super(type, properties);
    }

    @Override
    public void afterTransfiguration(ItemStack itemStack, @Nonnull LivingEntity livingEntity, Hand hand) {
        itemStack.shrink(1);
    }
}
