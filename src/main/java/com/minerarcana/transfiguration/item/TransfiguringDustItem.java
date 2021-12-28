package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.entity.IAged;
import com.minerarcana.transfiguration.particles.TransfiguringParticleData;
import com.minerarcana.transfiguration.recipe.dust.DustRecipe;
import com.minerarcana.transfiguration.recipe.dust.DustRecipeInventory;
import com.minerarcana.transfiguration.util.Vectors;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Supplier;

public class TransfiguringDustItem extends TransfiguringItem {
    public TransfiguringDustItem(Supplier<TransfigurationType> type, Properties properties) {
        super(type, properties);
    }

    @Override
    public void afterTransfiguration(ItemStack itemStack, @Nonnull LivingEntity livingEntity, Hand hand) {
        itemStack.shrink(1);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        CompoundNBT persistent = entity.getPersistentData();
        if (!persistent.contains("PreventRemoteMovement")) {
            persistent.putBoolean("PreventRemoteMovement", true);
        }
        if (entity instanceof IAged) {
            int age = ((IAged) entity).getActualAge();
            if (age > 50) {
                World world = entity.getEntityWorld();
                if (!persistent.contains("Recipe")) {
                    persistent.putString("Recipe", this.getDustRecipeFor(entity)
                            .map(DustRecipe::getId)
                            .map(ResourceLocation::toString)
                            .orElse("")
                    );
                } else {
                    Optional<DustRecipe> recipe = this.getDustRecipeFor(
                            world,
                            persistent.getString("Recipe")
                    );
                    if (recipe.isPresent()) {
                        if (age % 5 == 0) {
                            recipe = this.getDustRecipeFor(entity);
                            persistent.putString("Recipe", recipe.map(DustRecipe::getId)
                                    .map(ResourceLocation::toString)
                                    .orElse("")
                            );
                        }
                        if (recipe.isPresent()) {
                            float progress = (float) age / (entity.lifespan / 2f);
                            if (progress > 0.95) {
                                if (!world.isRemote()) {
                                    ItemStack inputStack = entity.getItem();
                                    float chance = inputStack.getMaxStackSize() / (float) inputStack.getCount();
                                    if (random.nextFloat() < chance) {
                                        BlockPos blockPos = entity.getPosition();
                                        BlockState blockState = world.getBlockState(blockPos);
                                        FluidState fluidState = world.getFluidState(blockPos);
                                        DustRecipeInventory dustRecipeInventory = new DustRecipeInventory(
                                                blockState,
                                                fluidState,
                                                this.getType(entity.getItem())
                                        );
                                        world.addEntity(new ItemEntity(
                                                world,
                                                entity.getPosX(),
                                                entity.getPosY(),
                                                entity.getPosZ(),
                                                recipe.get()
                                                        .getCraftingResult(dustRecipeInventory)
                                        ));
                                        world.setBlockState(entity.getPosition(), Blocks.AIR.getDefaultState());
                                    }
                                }

                                entity.remove();
                                return true;
                            } else {
                                int numberOfParticles = (int) Math.ceil(4 * progress);
                                for (int x = 0; x < numberOfParticles; x++) {
                                    if (world instanceof ServerWorld) {
                                        BlockPos blockPos = entity.getPosition();
                                        Vector3d startPos = Vectors.withRandomOffset(blockPos, world.getRandom(), 3);
                                        ((ServerWorld) world).spawnParticle(
                                                new TransfiguringParticleData(
                                                        recipe.get().getTransfigurationType(),
                                                        new Vector3d(
                                                                blockPos.getX() + 0.5,
                                                                blockPos.getY() + 0.2,
                                                                blockPos.getZ() + 0.5
                                                        ),
                                                        8,
                                                        Math.min((entity.lifespan / 2) - age, 40),
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
                    } else {
                        if (age % 50 == 0) {
                            persistent.putString("Recipe", this.getDustRecipeFor(entity)
                                    .map(DustRecipe::getId)
                                    .map(ResourceLocation::toString)
                                    .orElse("")
                            );
                        }
                    }
                }
            }
        }
        return false;
    }

    private Optional<DustRecipe> getDustRecipeFor(ItemEntity entity) {
        World world = entity.getEntityWorld();
        BlockPos blockPos = entity.getPosition();
        BlockState blockState = world.getBlockState(blockPos);
        FluidState fluidState = world.getFluidState(blockPos);
        DustRecipeInventory dustRecipeInventory = new DustRecipeInventory(
                blockState,
                fluidState,
                this.getType(entity.getItem())
        );
        return world.getRecipeManager()
                .getRecipe(TransfigurationRecipes.DUST_RECIPE_TYPE, dustRecipeInventory, world);
    }

    private Optional<DustRecipe> getDustRecipeFor(World world, String recipeName) {
        return Optional.ofNullable(ResourceLocation.tryCreate(recipeName))
                .flatMap(name -> world.getRecipeManager()
                        .getRecipe(name)
                        .filter(DustRecipe.class::isInstance)
                        .map(DustRecipe.class::cast)
                );
    }
}
