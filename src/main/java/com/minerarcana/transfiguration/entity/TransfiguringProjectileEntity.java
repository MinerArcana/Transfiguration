package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationAttributes;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.item.ITransfiguring;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.entity.EntityTransfigurationRecipe;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

public class TransfiguringProjectileEntity extends ThrowableItemProjectile {
    public TransfiguringProjectileEntity(EntityType<? extends ThrowableItemProjectile> type, Level world) {
        super(type, world);
    }

    public TransfiguringProjectileEntity(Level worldIn, LivingEntity throwerIn) {
        super(TransfigurationEntities.TRANSFIGURING_PROJECTILE.get(), throwerIn, worldIn);
    }

    public TransfiguringProjectileEntity(Level worldIn, double x, double y, double z) {
        super(TransfigurationEntities.TRANSFIGURING_PROJECTILE.get(), x, y, z, worldIn);
    }

    @Override
    @Nonnull
    protected Item getDefaultItem() {
        return TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM.get();
    }

    @Override
    protected void onHitBlock(@Nonnull BlockHitResult blockRayTraceResult) {
        TransfigurationType type = this.getTransfigurationType();
        BlockState blockstate = this.level.getBlockState(blockRayTraceResult.getBlockPos());
        blockstate.onProjectileHit(this.level, blockstate, blockRayTraceResult, this);
        BlockTransfigurationRecipe.tryTransfigure(
                type,
                blockRayTraceResult,
                this.getCommandSenderWorld(),
                this.getOwner(),
                this.getPowerModifier(),
                this.getTimeModifier()
        );
        this.discard();
    }

    @Override
    protected void onHitEntity(@Nonnull EntityHitResult entityRayTraceResult) {
        TransfigurationType type = this.getTransfigurationType();
        EntityTransfigurationRecipe.tryTransfigure(
                type,
                entityRayTraceResult.getEntity(),
                this.getOwner(),
                this.getPowerModifier(),
                this.getTimeModifier()
        );
    }

    private TransfigurationType getTransfigurationType() {
        ItemStack itemStack = this.getItem();
        if (itemStack.getItem() instanceof ITransfiguring) {
            return ((ITransfiguring) itemStack.getItem()).getType(itemStack);
        }
        return null;
    }

    private double getPowerModifier() {
        ItemStack itemStack = this.getItem();
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (livingEntity.getAttributes().hasAttribute(TransfigurationAttributes.POWER_MODIFIER.get())) {
                return livingEntity.getAttributeValue(TransfigurationAttributes.POWER_MODIFIER.get());
            }
        }
        if (itemStack.getItem() instanceof ITransfiguring) {
            return ((ITransfiguring) itemStack.getItem()).getPowerModifier(itemStack);
        }
        return 1.0F;
    }

    private double getTimeModifier() {
        ItemStack itemStack = this.getItem();
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (livingEntity.getAttributes().hasAttribute(TransfigurationAttributes.TIME_MODIFIER.get())) {
                return livingEntity.getAttributeValue(TransfigurationAttributes.TIME_MODIFIER.get());
            }
        }
        if (itemStack.getItem() instanceof ITransfiguring) {
            return ((ITransfiguring) itemStack.getItem()).getTimeModifier(itemStack);
        }
        return 1F;
    }

    @Override
    @Nonnull
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
