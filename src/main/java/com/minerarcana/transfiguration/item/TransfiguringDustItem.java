package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.entity.IAged;
import com.minerarcana.transfiguration.recipe.dust.DustRecipe;
import com.minerarcana.transfiguration.recipe.dust.DustRecipeInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Collections;
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
        if (!entity.getPersistentData().contains("PreventRemoteMovement")) {
            entity.getPersistentData().putBoolean("PreventRemoteMovement", true);
        }
        if (entity instanceof IAged && ((IAged) entity).getActualAge() + 1 >= entity.lifespan) {
            World world = entity.getEntityWorld();
            BlockState blockState = world.getBlockState(entity.getPosition());
            FluidState fluidState = world.getFluidState(entity.getPosition());
            DustRecipeInventory dustRecipeInventory = new DustRecipeInventory(
                    blockState,
                    fluidState,
                    this.getType(stack),
                    Collections.emptyList()
            );
            Optional<DustRecipe> dustRecipe = world.getRecipeManager()
                    .getRecipe(TransfigurationRecipes.DUST_RECIPE_TYPE, dustRecipeInventory, world);
            if (dustRecipe.isPresent()) {
                if (!world.isRemote()) {
                    world.addEntity(new ItemEntity(
                            world,
                            entity.getPosX(),
                            entity.getPosY(),
                            entity.getPosZ(),
                            dustRecipe.get()
                                    .getCraftingResult(dustRecipeInventory)
                    ));
                    world.setBlockState(entity.getPosition(), Blocks.AIR.getDefaultState());
                }

                entity.remove();
                return true;
            }
        }
        return false;
    }
}
