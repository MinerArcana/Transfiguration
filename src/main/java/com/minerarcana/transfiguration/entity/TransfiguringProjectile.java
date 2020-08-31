package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationContainer;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class TransfiguringProjectile extends ProjectileItemEntity {
    public TransfiguringProjectile(EntityType<? extends ProjectileItemEntity> type, World world) {
        super(type, world);
    }

    public TransfiguringProjectile(World worldIn, LivingEntity throwerIn) {
        super(TransfigurationEntities.TRANSFIGURING_PROJECTILE.get(), throwerIn, worldIn);
    }

    public TransfiguringProjectile(World worldIn, double x, double y, double z) {
        super(TransfigurationEntities.TRANSFIGURING_PROJECTILE.get(), x, y, z, worldIn);
    }

    @Override
    @Nonnull
    protected Item getDefaultItem() {
        return TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM.get();
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult blockRayTraceResult) {
        BlockState blockstate = this.world.getBlockState(blockRayTraceResult.getPos());
        blockstate.onProjectileCollision(this.world, blockstate, blockRayTraceResult, this);
        BlockTransfigurationContainer container = new BlockTransfigurationContainer(null, world,
                blockRayTraceResult.getPos());

        world.getRecipeManager().getRecipe(Transfiguration.NETHERI.getBlockRecipeType(), container, world)
                .ifPresent(recipe -> recipe.transfigure(container));
    }


}
