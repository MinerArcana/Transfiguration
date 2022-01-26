package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class TransfigurationItems {

    public static final ItemEntry<Item> MAGIC_POWDER = Transfiguration.getRegistrate()
            .object("magic_powder")
            .item(Item::new)
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapeless(context.get())
                        .requires(Tags.Items.DUSTS_REDSTONE)
                        .requires(TransfigurationItemTags.CORALS)
                        .requires(Tags.Items.GEMS_LAPIS)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(Tags.Items.DUSTS_REDSTONE))
                        .save(provider, Transfiguration.rl("magic_powder_basic"));

                ShapelessRecipeBuilder.shapeless(context.get(), 4)
                        .requires(Items.CRIMSON_ROOTS)
                        .requires(Items.WARPED_ROOTS)
                        .requires(Tags.Items.DUSTS_GLOWSTONE)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(Tags.Items.DUSTS_REDSTONE))
                        .save(provider, Transfiguration.rl("magic_powder_nether"));

                ShapelessRecipeBuilder.shapeless(context.get(), 16)
                        .requires(Tags.Items.GEMS_PRISMARINE)
                        .requires(Tags.Items.ENDER_PEARLS)
                        .requires(Tags.Items.CROPS_NETHER_WART)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(Tags.Items.ENDER_PEARLS))
                        .save(provider, Transfiguration.rl("magic_powder_otherworldly"));
            })
            .register();

    public static void setup() {

    }
}
