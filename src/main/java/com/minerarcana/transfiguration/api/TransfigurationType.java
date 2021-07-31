package com.minerarcana.transfiguration.api;

import com.minerarcana.transfiguration.api.recipe.ITransfigurationRecipe;
import com.minerarcana.transfiguration.api.util.ResourceLocationHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

public class TransfigurationType extends ForgeRegistryEntry<TransfigurationType> {
    private final int primaryColor;
    private final List<Supplier<TransfigurationType>> includes;

    private ResourceLocation blockRecipeId;
    private ResourceLocation entityRecipeId;
    private IRecipeType<ITransfigurationRecipe<BlockState>> blockRecipeType;
    private IRecipeType<ITransfigurationRecipe<Entity>> entityRecipeType;
    private String translationKey;
    private ITextComponent displayName;

    public TransfigurationType(int primaryColor, List<Supplier<TransfigurationType>> includes) {
        this.primaryColor = primaryColor;
        this.includes = includes;
    }

    public ResourceLocation getBlockRecipeId() {
        if (this.blockRecipeId == null) {
            this.blockRecipeId = ResourceLocationHelper.append(this.getRegistryName(), "block");
        }
        return this.blockRecipeId;
    }

    public IRecipeType<ITransfigurationRecipe<BlockState>> getBlockRecipeType() {
        if (this.blockRecipeType == null) {
            this.blockRecipeType = IRecipeType.register(this.getBlockRecipeId().toString());
        }
        return this.blockRecipeType;
    }

    public ResourceLocation getEntityRecipeId() {
        if (this.entityRecipeId == null) {
            this.entityRecipeId = ResourceLocationHelper.append(this.getRegistryName(), "entity");
        }
        return this.entityRecipeId;
    }

    public IRecipeType<ITransfigurationRecipe<Entity>> getEntityRecipeType() {
        if (this.entityRecipeType == null) {
            this.entityRecipeType = IRecipeType.register(this.getEntityRecipeId().toString());
        }
        return this.entityRecipeType;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    @Nonnull
    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeTranslationKey("transfiguration_type", this.getRegistryName());
        }
        return this.translationKey;
    }

    @Nonnull
    public ITextComponent getDisplayName() {
        if (this.displayName == null) {
            this.displayName = new TranslationTextComponent(this.getTranslationKey());
        }
        return this.displayName;
    }

    public List<Supplier<TransfigurationType>> getIncludedTypes() {
        return includes;
    }
}
