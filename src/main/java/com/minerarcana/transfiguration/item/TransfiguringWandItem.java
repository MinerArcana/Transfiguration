package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.entity.TransfiguringProjectile;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class TransfiguringWandItem extends TransfiguringProjectileItem {
    private final TransfigurationType transfigurationType;

    public TransfiguringWandItem(TransfigurationType transfigurationType, Properties properties) {
        super(properties);
        this.transfigurationType = transfigurationType;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemstack = playerEntity.getHeldItem(hand);
        world.playSound(null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(),
                SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F /
                        (random.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            TransfiguringProjectile projectile = new TransfiguringProjectile(world, playerEntity);
            projectile.setItem(itemstack);
            projectile.func_234612_a_(playerEntity, playerEntity.rotationPitch, playerEntity.rotationYaw,
                    0.0F, 1.5F, 1.0F);
            world.addEntity(projectile);
        }

        playerEntity.addStat(Stats.ITEM_USED.get(this));
        if (!playerEntity.abilities.isCreativeMode) {
            itemstack.damageItem(1, playerEntity, itemStack -> playerEntity.sendBreakAnimation(hand));
        }

        return ActionResult.func_233538_a_(itemstack, world.isRemote());
    }
}
