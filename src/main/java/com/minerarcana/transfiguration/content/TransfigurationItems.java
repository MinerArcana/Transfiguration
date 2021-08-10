package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

public class TransfigurationItems {

    public static final ItemEntry<Item> MAGIC_POWDER = Transfiguration.getRegistrate()
            .object("magic_powder")
            .item(Item::new)
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapelessRecipe(context.get())
                        .addIngredient(Tags.Items.DUSTS_REDSTONE)
                        .addIngredient(TransfigurationItemTags.CORALS)
                        .addIngredient(Tags.Items.GEMS_LAPIS)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(Tags.Items.DUSTS_REDSTONE))
                        .build(provider, Transfiguration.rl("magic_powder_basic"));

                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 4)
                        .addIngredient(Items.CRIMSON_ROOTS)
                        .addIngredient(Items.WARPED_ROOTS)
                        .addIngredient(Tags.Items.DUSTS_GLOWSTONE)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(Tags.Items.DUSTS_REDSTONE))
                        .build(provider, Transfiguration.rl("magic_powder_nether"));

                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 16)
                        .addIngredient(Tags.Items.GEMS_PRISMARINE)
                        .addIngredient(Tags.Items.ENDER_PEARLS)
                        .addIngredient(Tags.Items.CROPS_NETHER_WART)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(Tags.Items.ENDER_PEARLS))
                        .build(provider, Transfiguration.rl("magic_powder_otherworldly"));
            })
            .register();

    public static void setup() {

    }
}
