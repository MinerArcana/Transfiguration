package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import com.minerarcana.transfiguration.particles.TransfiguringParticleData;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import com.minerarcana.transfiguration.util.Vectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class AOEBlockTagResult extends Result {
    private final ITag<Block> tag;

    public AOEBlockTagResult(ITag<Block> tag) {
        this.tag = tag;
    }

    public void handle(@Nonnull TransfigurationContainer<?> transfigurationContainer, double powerModifier) {
        BlockState blockState = tag.getRandomElement(transfigurationContainer.getWorld().rand).getDefaultState();
        transfigurationContainer.getWorld().setBlockState(transfigurationContainer.getTargetedPos(), blockState);
        BlockPos centerPos = transfigurationContainer.getTargetedPos();
        //Random random = new Random();
        World world = transfigurationContainer.getWorld();
        Random random = world.getRandom();
        if (world instanceof ServerWorld) {
            for (int i = 0; i < 100; i++) {
                Vector3d startPos = Vectors.centered(centerPos);
                Vector3d endPos = Vectors.withRandomOffset(centerPos, random, 9);
                ((ServerWorld) world).spawnParticle(
                        new TransfiguringParticleData(
                                TransfigurationTypes.BLESSED.get(), //TODO WORK OUT A WAY TO GET TYPE HERE
                                endPos,
                                0,
                                30, //Math.min(remainingTicks, 45)
                                random.nextInt(32)
                        ),
                        startPos.x,
                        startPos.y,
                        startPos.z,
                        1,
                        0.0D,
                        0.0D,
                        0.0D,
                        1.20F //0.15F
                );
            }
        }
        if (transfigurationContainer.getTargeted() instanceof BlockState) {
            for (int y = -3; y < 4; y++) {
                for (int x = -3; x < 4; x++) {
                    for (int z = -3; z < 4; z++) {
                        BlockPos targetPos = centerPos.add(x, y, z);
                        if (transfigurationContainer.getWorld().getBlockState(targetPos).getBlock().equals(((BlockState) transfigurationContainer.getTargeted()).getBlock())
                            && random.nextInt(5) == 4) {
                            BlockState randBlockState = tag.getRandomElement(transfigurationContainer.getWorld().rand).getDefaultState();
                            transfigurationContainer.getWorld().setBlockState(targetPos, randBlockState);
                            if (world instanceof ServerWorld) {
                                for (int i = 0; i < 10; i++) {
                                    Vector3d startPos = Vectors.centered(targetPos);
                                    Vector3d endPos = Vectors.withRandomOffset(targetPos, random, 3);
                                    ((ServerWorld) world).spawnParticle(
                                            new TransfiguringParticleData(
                                                    TransfigurationTypes.BLESSED.get(), //TODO WORK OUT A WAY TO GET TYPE HERE
                                                    endPos,
                                                    0,
                                                    15, //Math.min(remainingTicks, 45)
                                                    random.nextInt(32)
                                            ),
                                            startPos.x,
                                            startPos.y,
                                            startPos.z,
                                            1,
                                            0.0D,
                                            0.0D,
                                            0.0D,
                                            0.15F
                                    );
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public ResultInstance create() {
        return new AfterDoneResultInstance(this::handle);
    }

    @Nonnull
    @Override
    public ItemStack getRepresentation() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.BLOCK_TAG_RESULT_SERIALIZER.get();
    }

    public ITag<Block> getTag() {
        return tag;
    }
}
