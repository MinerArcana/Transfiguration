package com.minerarcana.transfiguration.recipe.block;

import com.minerarcana.transfiguration.recipe.TransfigurationContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTransfigurationContainer extends TransfigurationContainer {
    private final BlockState targeted;

    public BlockTransfigurationContainer(ItemUseContext context) {
        this(context.getPlayer(), context.getWorld(), context.getPos());
    }

    public BlockTransfigurationContainer(@Nullable LivingEntity caster, World world, BlockPos targetedPos) {
        super(caster, world, targetedPos);
        this.targeted = world.getBlockState(targetedPos);
    }

    public BlockState getTargeted() {
        return this.targeted;
    }
}
