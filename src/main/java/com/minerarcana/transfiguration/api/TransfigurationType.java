package com.minerarcana.transfiguration.api;

import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryManager;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

public class TransfigurationType {
    public static ResourceLocation REGISTRY_NAME = new ResourceLocation("transfiguration", "transfiguration_types");
    private final int primaryColor;
    private final int secondaryColor;
    private final float skip;
    private final List<TransfiguringKeyword> keywords;
    private final List<Supplier<TransfigurationType>> fallbacks;
    private final Supplier<RecipeType<ITransfigurationRecipe<BlockState>>> blockRecipeType;
    private final Supplier<RecipeType<ITransfigurationRecipe<Entity>>> entityRecipeType;
    private String translationKey;
    private Component displayName;

    public TransfigurationType(int primaryColor, int secondaryColor, float skip, List<TransfiguringKeyword> keywords,
                               List<Supplier<TransfigurationType>> includes,
                               Supplier<RecipeType<ITransfigurationRecipe<BlockState>>> blockRecipeType,
                               Supplier<RecipeType<ITransfigurationRecipe<Entity>>> entityRecipeType
    ) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.skip = skip;
        this.keywords = keywords;
        this.fallbacks = includes;
        this.entityRecipeType = entityRecipeType;
        this.blockRecipeType = blockRecipeType;
    }

    public RecipeType<ITransfigurationRecipe<BlockState>> getBlockRecipeType() {
        return blockRecipeType.get();
    }

    public RecipeType<ITransfigurationRecipe<Entity>> getEntityRecipeType() {
        return this.entityRecipeType.get();
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public int getSecondaryColor() {
        return secondaryColor;
    }

    @Nonnull
    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId(
                    "transfiguration_type",
                    RegistryManager.ACTIVE.getRegistry(REGISTRY_NAME).getKey(this)
            );
        }
        return this.translationKey;
    }

    @Nonnull
    public Component getDisplayName() {
        if (this.displayName == null) {
            this.displayName = Component.translatable(this.getTranslationKey());
        }
        return this.displayName;
    }

    public List<Supplier<TransfigurationType>> getFallbacks() {
        return fallbacks;
    }

    public boolean hasKeyword(TransfiguringKeyword keyword) {
        return keywords.contains(keyword);
    }

    public float getSkip() {
        return this.skip;
    }
}
