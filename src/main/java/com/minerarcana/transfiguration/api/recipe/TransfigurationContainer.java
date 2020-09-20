package com.minerarcana.transfiguration.api.recipe;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class TransfigurationContainer<T> extends EmptyInventory {
    private final LivingEntity caster;
    private final World world;
    private final BlockPos targetedPos;
    private final Direction onSide;
    private final T targeted;
    private final Consumer<T> removeTarget;

    public TransfigurationContainer(T targeted, @Nullable LivingEntity caster, World world, BlockPos targetedPos,
                                    Direction onSide, Consumer<T> removeTarget) {
        this.targeted = targeted;
        this.caster = caster;
        this.world = world;
        this.targetedPos = targetedPos;
        this.removeTarget = removeTarget;
        this.onSide = onSide;
    }

    public LivingEntity getCaster() {
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

    public Direction getOnSide() {
        return onSide;
    }

    public static TransfigurationContainer<Entity> entity(Entity targetedEntity, @Nullable LivingEntity caster) {
        return new TransfigurationContainer<>(targetedEntity, caster, targetedEntity.getEntityWorld(),
                targetedEntity.getPosition(), Direction.DOWN, Entity::remove);
    }

    public static TransfigurationContainer<BlockState> block(World world, BlockPos blockPos, Direction onSide, @Nullable LivingEntity caster) {
        return new TransfigurationContainer<>(world.getBlockState(blockPos), caster, world, blockPos, onSide,
                blockState -> world.setBlockState(blockPos, Blocks.AIR.getDefaultState()));
    }

}

