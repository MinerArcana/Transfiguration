package com.minerarcana.transfiguration.api;

import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import com.minerarcana.transfiguration.api.util.ResourceLocationHelper;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

public class TransfigurationType extends ForgeRegistryEntry<TransfigurationType> {
    private final int primaryColor;
    private final int secondaryColor;
    private final List<TransfiguringKeyword> keywords;
    private final List<Supplier<TransfigurationType>> fallbacks;

    private ResourceLocation blockRecipeId;
    private ResourceLocation entityRecipeId;
    private RecipeType<ITransfigurationRecipe<BlockState>> blockRecipeType;
    private RecipeType<ITransfigurationRecipe<Entity>> entityRecipeType;
    private String translationKey;
    private Component displayName;

    public TransfigurationType(int primaryColor, int secondaryColor, List<TransfiguringKeyword> keywords, List<Supplier<TransfigurationType>> includes) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.keywords = keywords;
        this.fallbacks = includes;
    }

    public ResourceLocation getBlockRecipeId() {
        if (this.blockRecipeId == null) {
            this.blockRecipeId = ResourceLocationHelper.append(this.getRegistryName(), "block");
        }
        return this.blockRecipeId;
    }

    public RecipeType<ITransfigurationRecipe<BlockState>> getBlockRecipeType() {
        if (this.blockRecipeType == null) {
            this.blockRecipeType = RecipeType.register(this.getBlockRecipeId().toString());
        }
        return this.blockRecipeType;
    }

    public ResourceLocation getEntityRecipeId() {
        if (this.entityRecipeId == null) {
            this.entityRecipeId = ResourceLocationHelper.append(this.getRegistryName(), "entity");
        }
        return this.entityRecipeId;
    }

    public RecipeType<ITransfigurationRecipe<Entity>> getEntityRecipeType() {
        if (this.entityRecipeType == null) {
            this.entityRecipeType = RecipeType.register(this.getEntityRecipeId().toString());
        }
        return this.entityRecipeType;
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
}
