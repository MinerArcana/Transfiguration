package com.minerarcana.transfiguration.transfiguring;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import com.minerarcana.transfiguration.util.ResourceLocationHelper;
import net.minecraft.item.DyeColor;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;

public class TransfigurationType extends ForgeRegistryEntry<TransfigurationType> {
    private final int primaryColor;

    private ResourceLocation blockRecipeId;
    private IRecipeType<BlockTransfigurationRecipe> blockRecipeType;
    private String translationKey;
    private ITextComponent displayName;

    public TransfigurationType(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public ResourceLocation getBlockRecipeId() {
        if (this.blockRecipeId == null){
            this.blockRecipeId = ResourceLocationHelper.append(this.getRegistryName(), "block");
        }
        return this.blockRecipeId;
    }

    public IRecipeType<BlockTransfigurationRecipe> getBlockRecipeType() {
        if (this.blockRecipeType == null) {
            this.blockRecipeType = IRecipeType.register(this.getBlockRecipeId().toString());
        }
        return this.blockRecipeType;
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
}
