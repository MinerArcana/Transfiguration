package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.Transfiguration;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.recipesystem.RecipeContainer;
import com.teamacronymcoders.base.recipesystem.RecipeType;
import com.teamacronymcoders.base.recipesystem.handler.IRecipeHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemMagicPowder extends ItemBase {
    private RecipeType recipeType;
    private IRecipeHandler recipeHandler;

    public ItemMagicPowder(String name) {
        super(name += "_powder");
        this.recipeType = new RecipeType(new ResourceLocation(Transfiguration.ID, name));
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (recipeHandler == null) {
            recipeHandler = recipeType.createHandler();
            recipeType.getRecipeHandlers().add(recipeHandler);
        }

        ItemStack held = player.getHeldItem(hand);
        return recipeHandler.tickRecipe(new RecipeContainer(held, held, world, pos), player) ?
                EnumActionResult.SUCCESS : EnumActionResult.PASS;
    }

    public RecipeType getRecipeType() {
        return this.recipeType;
    }
}
