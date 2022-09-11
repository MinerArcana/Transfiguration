package com.minerarcana.transfiguration.api;

import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

public class TransfigurationType extends ForgeRegistryEntry<TransfigurationType> {
    private final int primaryColor;
    private final int secondaryColor;
    private final float skip;
    private final List<TransfiguringKeyword> keywords;
    private final List<Supplier<TransfigurationType>> fallbacks;
    private final RegistryObject<RecipeType<ITransfigurationRecipe<BlockState>>> blockRecipeType;
    private final RegistryObject<RecipeType<ITransfigurationRecipe<Entity>>> entityRecipeType;
    private String translationKey;
    private Component displayName;

    public TransfigurationType(int primaryColor, int secondaryColor, float skip, List<TransfiguringKeyword> keywords,
                               List<Supplier<TransfigurationType>> includes,
                               RegistryObject<RecipeType<ITransfigurationRecipe<BlockState>>> blockRecipeType,
                               RegistryObject<RecipeType<ITransfigurationRecipe<Entity>>> entityRecipeType
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
            this.translationKey = Util.makeDescriptionId("transfiguration_type", this.getRegistryName());
        }
        return this.translationKey;
    }

    @Nonnull
    public Component getDisplayName() {
        if (this.displayName == null) {
            this.displayName = new TranslatableComponent(this.getTranslationKey());
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
