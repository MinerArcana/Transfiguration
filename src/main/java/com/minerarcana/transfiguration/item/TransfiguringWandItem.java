package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.entity.TransfiguringProjectileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        world.playSound(null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(),
                SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F /
                        (random.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            TransfiguringProjectileEntity projectile = new TransfiguringProjectileEntity(world, playerEntity);
            projectile.setItem(TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM.get()
                    .withTypeAndStats(
                            this.getType(itemStack),
                            this.getPowerModifier(itemStack),
                            this.getTimeModifier(itemStack)
                    )
            );
            projectile.setShooter(playerEntity);
            projectile.func_234612_a_(playerEntity, playerEntity.rotationPitch, playerEntity.rotationYaw,
                    0.0F, 1.5F, 1.0F);
            world.addEntity(projectile);
        }

        playerEntity.addStat(Stats.ITEM_USED.get(this));
        if (!playerEntity.abilities.isCreativeMode) {
            itemStack.damageItem(1, playerEntity, stack -> playerEntity.sendBreakAnimation(hand));
        }

        return ActionResult.func_233538_a_(itemStack, world.isRemote());
    }

    @Override
    public void afterTransfiguration(ItemStack itemStack, @Nonnull LivingEntity livingEntity, Hand hand) {
        itemStack.damageItem(1, livingEntity, entity -> entity.sendBreakAnimation(hand));
    }

    @Override
    public int getItemEnchantability() {
        return ItemTier.GOLD.getEnchantability();
    }
}
