package com.minerarcana.transfiguration.recipe.block;

import com.minerarcana.transfiguration.recipe.EmptyInventory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTransfigurationContainer extends EmptyInventory {
    private final LivingEntity caster;
    private final BlockState targeted;
    private final World world;
    private final BlockPos targetedPos;

    public BlockTransfigurationContainer(ItemUseContext context) {
        this(context.getPlayer(), context.getWorld(), context.getPos());
    }

    public BlockTransfigurationContainer(LivingEntity caster, World world, BlockPos targetedPos) {
        this.caster = caster;
        this.targeted = world.getBlockState(targetedPos);
        this.world = world;
        this.targetedPos = targetedPos;
    }

    public LivingEntity getCaster() {
        return caster;
    }

    public BlockState getTargeted() {
        return this.targeted;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getTargetedPos() {
        return targetedPos;
    }
}
