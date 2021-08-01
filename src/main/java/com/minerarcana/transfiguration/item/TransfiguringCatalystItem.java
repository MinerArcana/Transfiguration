package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.api.TransfigurationType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class TransfiguringCatalystItem extends TransfiguringItem {
    public TransfiguringCatalystItem(Supplier<TransfigurationType> type, Properties properties) {
        super(type, properties);
    }

    @Override
    public double getTimeModifier(ItemStack itemStack) {
        return 0.75;
    }

    @Override
    public void afterTransfiguration(ItemStack itemStack, @Nonnull LivingEntity livingEntity, Hand hand) {
        itemStack.damageItem(1, livingEntity, entity -> entity.sendBreakAnimation(hand));
    }

    @Override
    public int getItemEnchantability() {
        return ItemTier.IRON.getEnchantability();
    }
}
