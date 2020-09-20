package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.item.ITransfiguring;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TransfiguringProjectileEntity extends ProjectileItemEntity {
    public TransfiguringProjectileEntity(EntityType<? extends ProjectileItemEntity> type, World world) {
        super(type, world);
    }

    public TransfiguringProjectileEntity(World worldIn, LivingEntity throwerIn) {
        super(TransfigurationEntities.TRANSFIGURING_PROJECTILE.get(), throwerIn, worldIn);
    }

    public TransfiguringProjectileEntity(World worldIn, double x, double y, double z) {
        super(TransfigurationEntities.TRANSFIGURING_PROJECTILE.get(), x, y, z, worldIn);
    }

    @Override
    @Nonnull
    protected Item getDefaultItem() {
        return TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM.get();
    }

    @Override
    protected void func_230299_a_(@Nonnull BlockRayTraceResult blockRayTraceResult) {
        TransfigurationType type = this.getTransfigurationType();
        if (type != null) {
            BlockState blockstate = this.world.getBlockState(blockRayTraceResult.getPos());
            blockstate.onProjectileCollision(this.world, blockstate, blockRayTraceResult, this);
            TransfigurationContainer<BlockState> container = TransfigurationContainer.block(world,
                    blockRayTraceResult.getPos(), blockRayTraceResult.getFace(), this.getCaster());
            world.getRecipeManager().getRecipe(type.getBlockRecipeType(), container, world)
                    .ifPresent(recipe -> recipe.transfigure(container, 1.0F));
        }
    }

    @Override
    protected void onEntityHit(@Nonnull EntityRayTraceResult entityRayTraceResult) {
        TransfigurationType type = this.getTransfigurationType();
        if (type != null) {
            TransfigurationContainer<Entity> container = TransfigurationContainer.entity(entityRayTraceResult.getEntity(),
                    this.getCaster());
            world.getRecipeManager().getRecipe(type.getEntityRecipeType(), container, world)
                    .ifPresent(recipe -> recipe.transfigure(container, 1.0));
        }
    }

    private TransfigurationType getTransfigurationType() {
        ItemStack itemStack = this.getItem();
        if (itemStack.getItem() instanceof ITransfiguring) {
            return ((ITransfiguring) itemStack.getItem()).getType(itemStack);
        }
        return null;
    }

    @Nullable
    public LivingEntity getCaster() {
        return this.func_234616_v_() instanceof LivingEntity ? (LivingEntity) this.func_234616_v_() : null;
    }

    @Override
    @Nonnull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
