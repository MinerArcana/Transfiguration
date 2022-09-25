package com.minerarcana.transfiguration.api.recipe;

import com.google.common.base.Suppliers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TransfigurationContainer<T> extends EmptyInventory {
    private final Entity caster;
    private final Level world;
    private final BlockPos targetedPos;
    private final T targeted;
    private final Consumer<T> removeTarget;
    private final Supplier<FluidState> fluidStateSupplier;

    public TransfigurationContainer(T targeted, @Nullable Entity caster, Level world, BlockPos targetedPos, Consumer<T> removeTarget) {
        this.targeted = targeted;
        this.caster = caster;
        this.world = world;
        this.targetedPos = targetedPos;
        this.removeTarget = removeTarget;
        this.fluidStateSupplier = Suppliers.memoize(() -> this.getLevel()
                .getFluidState(this.getTargetedPos())
        );
    }

    public Entity getCaster() {
        return caster;
    }

    public Level getLevel() {
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

    public FluidState getFluidState() {
        return this.fluidStateSupplier.get();
    }

    public static TransfigurationContainer<Entity> entity(Entity targetedEntity, @Nullable Entity caster) {
        return new TransfigurationContainer<>(targetedEntity, caster, targetedEntity.getCommandSenderWorld(),
                targetedEntity.blockPosition(), Entity::discard);
    }

    public static TransfigurationContainer<BlockState> block(Level world, BlockPos blockPos, @Nullable Entity caster) {
        return new TransfigurationContainer<>(world.getBlockState(blockPos), caster, world, blockPos,
                blockState -> world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState()));
    }

}

