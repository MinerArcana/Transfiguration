package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationItems;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.dust.DustRecipe;
import com.minerarcana.transfiguration.recipe.dust.DustRecipeInventory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
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
        if (entity.getAge() >= entity.lifespan) {
            World world = entity.getEntityWorld();
            List<ItemEntity> itemEntityList = world.getEntitiesWithinAABB(
                    ItemEntity.class,
                    entity.getBoundingBox().grow(0.5D, 0.0D, 0.5D),
                    (otherEntity) -> otherEntity != entity && otherEntity.isAlive() &&
                            TransfigurationItems.CATALYST_SUBSTRATE.isIn(otherEntity.getItem())
            );
            if (!itemEntityList.isEmpty()) {
                BlockState blockState = world.getBlockState(entity.getPosition());
                FluidState fluidState = world.getFluidState(entity.getPosition());
                Optional<DustRecipe> dustRecipe = world.getRecipeManager()
                        .getRecipe(TransfigurationRecipes.DUST_RECIPE_TYPE, new DustRecipeInventory(
                                blockState,
                                fluidState,
                                this.getType(stack),
                                itemEntityList
                        ), world);
                if (dustRecipe.isPresent()) {
                    dustRecipe.get().handleItemEntities(itemEntityList);
                    entity.remove();
                    return true;
                }

            }

        }
        return false;
    }
}
