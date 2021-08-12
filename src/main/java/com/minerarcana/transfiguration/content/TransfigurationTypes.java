package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.util.ResourceLocationHelper;
import com.minerarcana.transfiguration.registrate.TransfigurationTypeBuilder;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class TransfigurationTypes {
    private static final NonNullBiFunction<String, BuilderCallback, TransfigurationTypeBuilder<TransfigurationType,
            AbstractRegistrate<?>>> TRANSFIGURATION_TYPE = TransfigurationTypeBuilder.entry(Transfiguration::getRegistrate);

    public static final RegistryEntry<TransfigurationType> NETHERI = Transfiguration.getRegistrate()
            .object("netheri")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Netheri")
            .primaryColor(DyeColor.RED.getColorValue())
            .dust()
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 9)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(getDust(TransfigurationTypes.MUTANDI))
                        .addIngredient(Tags.Items.CROPS_NETHER_WART)
                        .addIngredient(Items.BLAZE_POWDER)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider);

                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 24)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(getDust(TransfigurationTypes.MUTANDI))
                        .addIngredient(Tags.Items.RODS_BLAZE)
                        .addIngredient(Items.NETHER_WART_BLOCK)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider, ResourceLocationHelper.append(context.getId(), "_", "alt"));
            })
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> ACCURSED = Transfiguration.getRegistrate()
            .object("accursed")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Accursed")
            .fallback(NETHERI)
            .primaryColor(DyeColor.PURPLE.getColorValue())
            .recipe(TransfigurationRecipeData::accursedRecipes)
            .dust()
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 9)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(getDust(TransfigurationTypes.NETHERI))
                        .addIngredient(Tags.Items.OBSIDIAN)
                        .addIngredient(Items.ROTTEN_FLESH)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider);

                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 32)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(getDust(TransfigurationTypes.NETHERI))
                        .addIngredient(Tags.Items.OBSIDIAN)
                        .addIngredient(Items.ZOMBIE_HEAD)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider, ResourceLocationHelper.append(context.getId(), "_", "alt"));
            })
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> MUTANDI = Transfiguration.getRegistrate()
            .object("mutandi")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Mutandi")
            .primaryColor(DyeColor.GREEN.getColorValue())
            .dust()
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 9)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(Items.CACTUS)
                        .addIngredient(Items.NETHER_WART)
                        .addIngredient(ItemTags.TALL_FLOWERS)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider);

                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 64)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(Items.SLIME_BLOCK)
                        .addIngredient(Items.HEART_OF_THE_SEA)
                        .addIngredient(Items.CRYING_OBSIDIAN)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider, ResourceLocationHelper.append(context.getId(), "_", "alt"));
            })
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> BLESSED = Transfiguration.getRegistrate()
            .object("blessed")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Blessed")
            .fallback(MUTANDI)
            .primaryColor(DyeColor.YELLOW.getColorValue())
            .recipe(TransfigurationRecipeData::blessedRecipes)
            .dust()
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 9)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(getDust(TransfigurationTypes.MUTANDI))
                        .addIngredient(Tags.Items.GEMS_EMERALD)
                        .addIngredient(Items.GLISTERING_MELON_SLICE)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider);

                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 9)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(getDust(TransfigurationTypes.MUTANDI))
                        .addIngredient(Tags.Items.GEMS_EMERALD)
                        .addIngredient(Items.GOLDEN_CARROT)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider, ResourceLocationHelper.append(context.getId(), "_", "alt"));

                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 32)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(getDust(TransfigurationTypes.MUTANDI))
                        .addIngredient(Tags.Items.GEMS_EMERALD)
                        .addIngredient(Items.TOTEM_OF_UNDYING)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider, ResourceLocationHelper.append(context.getId(), "_", "alt1"));

                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 32)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(getDust(TransfigurationTypes.MUTANDI))
                        .addIngredient(Tags.Items.GEMS_EMERALD)
                        .addIngredient(Items.GOLDEN_APPLE)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider, ResourceLocationHelper.append(context.getId(), "_", "alt2"));
            })
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> DESTABILIZING = Transfiguration.getRegistrate()
            .object("destabilizing")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Destabilizing")
            .primaryColor(DyeColor.WHITE.getColorValue())
            .recipe(TransfigurationRecipeData::destabilizingRecipes)
            .dust()
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 9)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(Items.GRAVEL)
                        .addIngredient(Items.BONE_MEAL)
                        .addIngredient(Tags.Items.ENDER_PEARLS)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider);

                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 32)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(Items.TNT)
                        .addIngredient(Items.BONE_MEAL)
                        .addIngredient(Tags.Items.ENDER_PEARLS)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider, ResourceLocationHelper.append(context.getId(), "_", "alt"));
            })
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> DISSOLUTION = Transfiguration.getRegistrate()
            .object("dissolution")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Dissolution")
            .primaryColor(DyeColor.BLUE.getColorValue())
            .dust()
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapelessRecipe(context.get(), 9)
                    .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 7)
                    .addIngredient(Items.WATER_BUCKET)
                    .addIngredient(Items.SAND)
                    .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                    .build(provider))
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> FUNGAL = Transfiguration.getRegistrate()
            .object("fungal")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Fungal")
            .primaryColor(DyeColor.BROWN.getColorValue())
            .dust()
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapelessRecipe(context.get(), 9)
                    .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                    .addIngredient(getDust(TransfigurationTypes.MUTANDI))
                    .addIngredient(Items.RED_MUSHROOM)
                    .addIngredient(Items.BROWN_MUSHROOM)
                    .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                    .build(provider))
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> OVERNI = Transfiguration.getRegistrate()
            .object("overni")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Overni")
            .primaryColor(DyeColor.LIME.getColorValue())
            .dust()
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 9)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(Items.SMOOTH_STONE)
                        .addIngredient(Items.TALL_GRASS)
                        .addIngredient(Items.LILY_PAD)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider);

                ShapelessRecipeBuilder.shapelessRecipe(context.get(), 24)
                        .addIngredient(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .addIngredient(Items.SMOOTH_STONE)
                        .addIngredient(Items.SCUTE)
                        .addIngredient(Items.NAUTILUS_SHELL)
                        .addCriterion("has_item", RegistrateRecipeProvider.hasItem(context.get()))
                        .build(provider, ResourceLocationHelper.append(context.getId(), "_", "alt"));
            })
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static void setup() {

    }

    public static Item getDust(Supplier<TransfigurationType> transfigurationTypeSupplier) {
        ResourceLocation registryName = transfigurationTypeSupplier.get().getRegistryName();
        if (registryName == null) {
            throw new IllegalStateException("Registry Name was null");
        } else {
            Item dustItem = ForgeRegistries.ITEMS.getValue(
                    new ResourceLocation(registryName.getNamespace(), registryName.getPath() + "_dust")
            );
            if (dustItem == null) {
                throw new IllegalStateException("Failed to find Dust for Type: " + registryName);
            } else {
                return dustItem;
            }
        }
    }
}
