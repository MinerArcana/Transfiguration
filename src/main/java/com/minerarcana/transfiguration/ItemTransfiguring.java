package com.minerarcana.transfiguration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamacronymcoders.base.client.models.generator.IHasGeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.GeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.IGeneratedModel;
import com.teamacronymcoders.base.client.models.generator.generatedmodel.ModelType;
import com.teamacronymcoders.base.items.IHasItemColor;
import com.teamacronymcoders.base.items.IRightClickEntity;
import com.teamacronymcoders.base.items.ItemBase;
import com.teamacronymcoders.base.recipesystem.RecipeContainer;
import com.teamacronymcoders.base.recipesystem.handler.IRecipeHandler;
import com.teamacronymcoders.base.recipesystem.type.RecipeType;
import com.teamacronymcoders.base.util.Coloring;
import com.teamacronymcoders.base.util.files.templates.TemplateFile;
import com.teamacronymcoders.base.util.files.templates.TemplateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ItemTransfiguring extends ItemBase implements IRightClickEntity, IHasItemColor, IHasGeneratedModel {
    private final RecipeType recipeType;
    private final Consumer<Tuple<ItemStack, EntityPlayer>> handleDamage;
    private final Coloring color;
    private final boolean hasOverlay;
    private IRecipeHandler recipeHandler;

    public ItemTransfiguring(String name, RecipeType recipeType, String color, boolean hasOverlay, Consumer<Tuple<ItemStack, EntityPlayer>> handleDamage) {
        super(name);
        this.handleDamage = handleDamage;
        this.recipeType = recipeType;
        this.color = Coloring.fromHex(color);
        this.hasOverlay = hasOverlay;
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        boolean didRecipe = this.getRecipeHandler().handleRecipe(new RecipeContainer(held, held, world, pos), player);
        if (didRecipe) {
            handleDamage.accept(new Tuple<>(held, player));
        }
        return didRecipe ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
    }

    @Override
    public boolean rightClickEntity(ItemStack itemStack, Entity target, EntityPlayer entityPlayer, World world, EnumHand hand) {
        boolean didRecipe = this.getRecipeHandler().handleRecipe(new RecipeContainer(target, itemStack, world, target.getPosition()), entityPlayer);
        if (didRecipe) {
            handleDamage.accept(new Tuple<>(itemStack, entityPlayer));
        }
        return didRecipe;
    }

    private IRecipeHandler getRecipeHandler() {
        if (recipeHandler == null) {
            recipeHandler = recipeType.createHandler();
            recipeType.getRecipeHandlers().add(recipeHandler);
        }

        return recipeHandler;
    }

    @Override
    public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex) {
        return tintIndex == (hasOverlay ? 1 : 0) ? color.getIntColor() : -1;
    }

    @Override
    public List<IGeneratedModel> getGeneratedModels() {
        TemplateFile templateFile;
        Map<String, String> replacements = Maps.newHashMap();

        String name = this.name.substring(this.name.indexOf("_") + 1);

        if (hasOverlay) {
            templateFile = TemplateManager.getTemplateFile("item_model_overlaid");
            replacements.put("texture", "transfiguration:items/" + name);
            replacements.put("texture_overlay", "transfiguration:items/" + name + "_overlay");
        } else {
            templateFile = TemplateManager.getTemplateFile("item_model");
            replacements.put("texture", "transfiguration:items/" + name);
        }

        templateFile.replaceContents(replacements);

        return Lists.newArrayList(new GeneratedModel(this.name, ModelType.ITEM_MODEL, templateFile.getFileContents()));
    }
}
