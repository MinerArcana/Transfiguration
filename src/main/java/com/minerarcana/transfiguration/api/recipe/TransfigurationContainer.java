package com.minerarcana.transfiguration.api.recipe;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class TransfigurationContainer<T> extends EmptyInventory {
    private final Entity caster;
    private final World world;
    private final BlockPos targetedPos;
    private final T targeted;
    private final Consumer<T> removeTarget;

    public TransfigurationContainer(T targeted, @Nullable Entity caster, World world, BlockPos targetedPos, Consumer<T> removeTarget) {
        this.targeted = targeted;
        this.caster = caster;
        this.world = world;
        this.targetedPos = targetedPos;
        this.removeTarget = removeTarget;
    }

    public Entity getCaster() {
        return caster;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getTargetedPos() {
        return targetedPos;
    }

    public T getTargeted() {
        return targeted;
    }

    public void removeTarget() {
        this.removeTarget.accept(this.getTargeted());
    }

    public static TransfigurationContainer<Entity> entity(Entity targetedEntity, @Nullable Entity caster) {
        return new TransfigurationContainer<>(targetedEntity, caster, targetedEntity.getEntityWorld(),
                targetedEntity.getPosition(), Entity::remove);
    }

    public static TransfigurationContainer<BlockState> block(World world, BlockPos blockPos, @Nullable Entity caster) {
        return new TransfigurationContainer<>(world.getBlockState(blockPos), caster, world, blockPos,
                blockState -> world.setBlockState(blockPos, Blocks.AIR.getDefaultState()));
    }

}

