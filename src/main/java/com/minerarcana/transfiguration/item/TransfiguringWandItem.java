package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.entity.TransfiguringProjectileEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

public class TransfiguringWandItem extends TransfiguringItem {
    public TransfiguringWandItem(Supplier<TransfigurationType> transfigurationType, Properties properties) {
        super(transfigurationType, properties);
    }

    @Override
    public double getTimeModifier(ItemStack itemStack) {
        return 0.5;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public InteractionResultHolder<ItemStack> use(Level level, Player playerEntity, InteractionHand hand) {
        ItemStack itemStack = playerEntity.getItemInHand(hand);
        level.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
                SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F /
                        (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            TransfiguringProjectileEntity projectile = new TransfiguringProjectileEntity(level, playerEntity);
            projectile.setItem(TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM.get()
                    .withTypeAndStats(
                            this.getType(itemStack),
                            this.getPowerModifier(itemStack),
                            this.getTimeModifier(itemStack)
                    )
            );
            projectile.setOwner(playerEntity);
            projectile.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(),
                    0.0F, 1.5F, 1.0F);
            level.addFreshEntity(projectile);
        }

        playerEntity.awardStat(Stats.ITEM_USED.get(this));
        if (!playerEntity.getAbilities().instabuild) {
            itemStack.hurtAndBreak(1, playerEntity, stack -> playerEntity.broadcastBreakEvent(hand));
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public void afterTransfiguration(ItemStack itemStack, @Nonnull LivingEntity livingEntity, InteractionHand hand) {
        itemStack.hurtAndBreak(1, livingEntity, entity -> entity.broadcastBreakEvent(hand));
    }

    @Override
    public int getEnchantmentValue() {
        return Tiers.GOLD.getEnchantmentValue();
    }
}
