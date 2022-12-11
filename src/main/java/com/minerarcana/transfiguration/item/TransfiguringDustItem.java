package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.particles.TransfiguringParticleData;
import com.minerarcana.transfiguration.recipe.dust.DustRecipe;
import com.minerarcana.transfiguration.recipe.dust.DustRecipeInventory;
import com.minerarcana.transfiguration.util.Vectors;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public class TransfiguringDustItem extends TransfiguringItem {
    public TransfiguringDustItem(Supplier<TransfigurationType> type, Properties properties) {
        super(type, properties);
    }

    @Override
    public void afterTransfiguration(ItemStack itemStack, @Nonnull LivingEntity livingEntity, InteractionHand hand) {
        itemStack.shrink(1);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        CompoundTag persistent = entity.getPersistentData();
        if (!persistent.contains("PreventRemoteMovement")) {
            persistent.putBoolean("PreventRemoteMovement", true);
        }
        int age = entity.getAge();
        if (age > 50) {
            Level world = entity.getCommandSenderWorld();
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
                            if (!world.isClientSide()) {
                                RandomSource random = world.getRandom();
                                ItemStack inputStack = entity.getItem();
                                float chance = inputStack.getMaxStackSize() / (float) inputStack.getCount();
                                if (random.nextFloat() < chance) {
                                    BlockPos blockPos = entity.blockPosition();
                                    BlockState blockState = world.getBlockState(blockPos);
                                    FluidState fluidState = world.getFluidState(blockPos);
                                    DustRecipeInventory dustRecipeInventory = new DustRecipeInventory(
                                            blockState,
                                            fluidState,
                                            this.getType(entity.getItem())
                                    );
                                    world.addFreshEntity(new ItemEntity(
                                            world,
                                            entity.getX(),
                                            entity.getY(),
                                            entity.getZ(),
                                            recipe.get()
                                                    .assemble(dustRecipeInventory)
                                    ));
                                    world.setBlockAndUpdate(entity.blockPosition(), Blocks.AIR.defaultBlockState());
                                }
                            }

                            entity.remove(Entity.RemovalReason.KILLED);
                            return true;
                        } else {
                            int numberOfParticles = (int) Math.ceil(4 * progress);
                            for (int x = 0; x < numberOfParticles; x++) {
                                if (world instanceof ServerLevel) {
                                    BlockPos blockPos = entity.blockPosition();
                                    Vec3 startPos = Vectors.withRandomOffset(blockPos, world.getRandom(), 3);
                                    ((ServerLevel) world).sendParticles(
                                            new TransfiguringParticleData(
                                                    recipe.get().getTransfigurationType(),
                                                    new Vec3(
                                                            blockPos.getX() + 0.5,
                                                            blockPos.getY() + 0.2,
                                                            blockPos.getZ() + 0.5
                                                    ),
                                                    8,
                                                    Math.min((entity.lifespan / 2) - age, 40),
                                                    world.getRandom().nextInt(32)
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
        return false;
    }

    private Optional<DustRecipe> getDustRecipeFor(ItemEntity entity) {
        Level world = entity.getCommandSenderWorld();
        BlockPos blockPos = entity.blockPosition();
        BlockState blockState = world.getBlockState(blockPos);
        FluidState fluidState = world.getFluidState(blockPos);
        DustRecipeInventory dustRecipeInventory = new DustRecipeInventory(
                blockState,
                fluidState,
                this.getType(entity.getItem())
        );
        return world.getRecipeManager()
                .getRecipeFor(TransfigurationRecipes.DUST_RECIPE_TYPE.get(), dustRecipeInventory, world);
    }

    private Optional<DustRecipe> getDustRecipeFor(Level world, String recipeName) {
        return Optional.ofNullable(ResourceLocation.tryParse(recipeName))
                .flatMap(name -> world.getRecipeManager()
                        .byKey(name)
                        .filter(DustRecipe.class::isInstance)
                        .map(DustRecipe.class::cast)
                );
    }
}
