package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.api.TransfigurationType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;

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
    public void afterTransfiguration(ItemStack itemStack, @Nonnull LivingEntity livingEntity, InteractionHand hand) {
        itemStack.hurtAndBreak(1, livingEntity, entity -> entity.broadcastBreakEvent(hand));
    }

    @Override
    public int getEnchantmentValue() {
        return Tiers.IRON.getEnchantmentValue();
    }
}
