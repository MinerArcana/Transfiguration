package com.minerarcana.transfiguration.compat.jei;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import com.mojang.datafixers.util.Pair;
import com.tterrag.registrate.util.entry.RegistryEntry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@JeiPlugin
public class TransfigurationJEIPlugin implements IModPlugin {
    private static final ResourceLocation UUID = Transfiguration.rl("jei");
    private final Map<TransfigurationType, Pair<BlockTransfigurationRecipeCategory, EntityTransfigurationRecipeCategory>> recipeTypes;

    public TransfigurationJEIPlugin() {
        this.recipeTypes = new HashMap<>();
    }

    @Override
    @NotNull
    public ResourceLocation getPluginUid() {
        return UUID;
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        for (RegistryEntry<TransfigurationType> transfigurationType : getAll()) {
            Pair<BlockTransfigurationRecipeCategory, EntityTransfigurationRecipeCategory> pair = Pair.of(
                    new BlockTransfigurationRecipeCategory(guiHelper, transfigurationType.get()),
                    new EntityTransfigurationRecipeCategory(guiHelper, transfigurationType.get())
            );
            recipeTypes.put(transfigurationType.get(), pair);
            registration.addRecipeCategories(pair.getFirst(), pair.getSecond());
        }

    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        recipeTypes.forEach((type, categories) -> registration.addRecipeCatalyst(
                new ItemStack(TransfigurationTypes.getDust(type)),
                categories.getFirst().getRecipeType(),
                categories.getSecond().getRecipeType()
        ));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        if (Minecraft.getInstance().level != null) {
            RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
            recipeTypes.forEach((type, categories) -> {
                registration.addRecipes(
                        categories.getFirst().getRecipeType(),
                        recipeManager.getAllRecipesFor(type.getBlockRecipeType())
                );
                registration.addRecipes(
                        categories.getSecond().getRecipeType(),
                        recipeManager.getAllRecipesFor(type.getEntityRecipeType())
                );
            });
        }
    }

    public static Iterable<RegistryEntry<TransfigurationType>> getAll() {
        return Transfiguration.getRegistrate().getAll(TransfigurationTypes.REGISTRY_KEY);
    }
}
