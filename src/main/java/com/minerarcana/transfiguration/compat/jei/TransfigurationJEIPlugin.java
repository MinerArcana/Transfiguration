package com.minerarcana.transfiguration.compat.jei;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import com.minerarcana.transfiguration.compat.jei.cateogry.BlockTransfigurationCategory;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.RegistryManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

@JeiPlugin
public class TransfigurationJEIPlugin implements IModPlugin {
    private static final ResourceLocation UUID = Transfiguration.rl("jei");

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return UUID;
    }

    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registration) {
        for (TransfigurationType type : RegistryManager.ACTIVE.getRegistry(TransfigurationType.class).getValues()) {
            registration.addRecipeCategories(new BlockTransfigurationCategory(type, registration.getJeiHelpers().getGuiHelper()));
        }
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        for (TransfigurationType type : RegistryManager.ACTIVE.getRegistry(TransfigurationType.class).getValues()) {
            registration.addRecipes(
                    getRecipes(Minecraft.getInstance().world, type.getBlockRecipeType()),
                    type.getBlockRecipeId()
            );
        }
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
        for (TransfigurationType type : RegistryManager.ACTIVE.getRegistry(TransfigurationType.class).getValues()) {
            registration.addRecipeCatalyst(
                    new ItemStack(TransfigurationTypes.getItem(() -> type, "catalyst")),
                    type.getBlockRecipeId()
            );
        }
    }

    public static <T extends ITransfigurationRecipe<U>, U> Collection<T> getRecipes(World world, IRecipeType<T> recipeType) {
        if (world != null) {
            return world.getRecipeManager()
                    .getRecipesForType(recipeType);
        }
        return new ArrayList<>();
    }
}
