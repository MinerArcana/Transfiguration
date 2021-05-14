package com.minerarcana.transfiguration.compat.jei;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.compat.jei.cateogry.BlockTransfigurationCategory;
import com.minerarcana.transfiguration.compat.jei.ingredients.BlockIngredientHelper;
import com.minerarcana.transfiguration.compat.jei.ingredients.BlockIngredientRenderer;
import com.minerarcana.transfiguration.compat.jei.ingredients.BlockIngredientType;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@JeiPlugin
public class TransfigurationJEIPlugin implements IModPlugin {
    private static final ResourceLocation UUID = Transfiguration.rl("jei");

    public static BlockIngredientType BLOCK_INGREDIENT_TYPE = new BlockIngredientType();

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return UUID;
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        registration.register(
                BLOCK_INGREDIENT_TYPE,
                BLOCK_INGREDIENT_TYPE.getBlockStates(),
                new BlockIngredientHelper(),
                new BlockIngredientRenderer()
        );
    }

    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new BlockTransfigurationCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(
                getRecipes(Minecraft.getInstance().world, TransfigurationTypes.ACCURSED.get().getBlockRecipeType()),
                BlockTransfigurationCategory.UID
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(ForgeRegistries.ITEMS.getValue(Transfiguration.rl("accursed_catalyst"))),
                BlockTransfigurationCategory.UID
        );
    }

    @SuppressWarnings("unchecked")
    public static <T extends IRecipe<?>> Collection<T> getRecipes(World world, IRecipeType<T> recipeType) {
        if (world != null) {
            Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = ObfuscationReflectionHelper.getPrivateValue(
                    RecipeManager.class, world.getRecipeManager(), "field_199522_d");
            if (recipes != null) {
                Map<ResourceLocation, IRecipe<?>> typedRecipes = recipes.get(recipeType);
                if (typedRecipes != null) {
                    return (Collection<T>) typedRecipes.values();
                }
            }
        }

        return new ArrayList<>();
    }
}
