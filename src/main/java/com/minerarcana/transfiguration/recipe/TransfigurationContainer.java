package com.minerarcana.transfiguration.recipe;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TransfigurationContainer extends EmptyInventory {
    private final LivingEntity caster;
    private final World world;
    private final BlockPos targetedPos;

    public TransfigurationContainer(@Nullable LivingEntity caster, World world, BlockPos targetedPos) {
        this.caster = caster;
        this.world = world;
        this.targetedPos = targetedPos;
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
}

