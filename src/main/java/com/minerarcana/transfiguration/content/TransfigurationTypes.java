package com.minerarcana.transfiguration.content;

import com.google.common.base.Suppliers;
import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.TransfiguringKeyword;
import com.minerarcana.transfiguration.api.util.ResourceLocationHelper;
import com.minerarcana.transfiguration.registrate.TransfigurationTypeBuilder;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class TransfigurationTypes {

    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPE_DEFERRED_REGISTER =
            DeferredRegister.create(
                    Registry.RECIPE_TYPE_REGISTRY,
                    Transfiguration.ID
            );
    private static final NonNullBiFunction<String, BuilderCallback, TransfigurationTypeBuilder<TransfigurationType,
            AbstractRegistrate<?>>> TRANSFIGURATION_TYPE = TransfigurationTypeBuilder.entry(
            Transfiguration::getRegistrate,
            RECIPE_TYPE_DEFERRED_REGISTER
    );

    @SuppressWarnings("UnstableApiUsage")
    public static ResourceKey<Registry<TransfigurationType>> REGISTRY_KEY = Transfiguration.getRegistrate()
            .makeRegistry("transfiguration_types", RegistryBuilder::new);

    private static final Supplier<IForgeRegistry<TransfigurationType>> REGISTRY = Suppliers.memoize(() ->
            RegistryManager.ACTIVE.getRegistry(REGISTRY_KEY)
    );

    public static final RegistryEntry<TransfigurationType> NETHERI = Transfiguration.getRegistrate()
            .object("netheri")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Netheri")
            .primaryColor(DyeColor.RED)
            .secondaryColor(DyeColor.ORANGE)
            .keyword(TransfiguringKeyword.CONTAGIOUS)
            .recipe(TransfigurationRecipeData::netheriRecipes)
            .dust()
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapeless(context.get(), 9)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(getDust(TransfigurationTypes.MUTANDI))
                        .requires(Tags.Items.CROPS_NETHER_WART)
                        .requires(Items.BLAZE_POWDER)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                        .save(provider);

                ShapelessRecipeBuilder.shapeless(context.get(), 24)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(getDust(TransfigurationTypes.MUTANDI))
                        .requires(Tags.Items.RODS_BLAZE)
                        .requires(Items.NETHER_WART_BLOCK)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                        .save(provider, ResourceLocationHelper.append(context.getId(), "_", "alt"));
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
            .primaryColor(DyeColor.PURPLE)
            .secondaryColor(DyeColor.BROWN)
            .keyword(TransfiguringKeyword.SPREADING)
            .recipe(TransfigurationRecipeData::accursedRecipes)
            .dust()
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapeless(context.get(), 9)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(getDust(TransfigurationTypes.NETHERI))
                        .requires(Tags.Items.OBSIDIAN)
                        .requires(Items.ROTTEN_FLESH)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                        .save(provider);

                ShapelessRecipeBuilder.shapeless(context.get(), 32)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(getDust(TransfigurationTypes.NETHERI))
                        .requires(Tags.Items.OBSIDIAN)
                        .requires(Items.ZOMBIE_HEAD)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                        .save(provider, ResourceLocationHelper.append(context.getId(), "_", "alt"));
            })
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> MUTANDI = Transfiguration.getRegistrate()
            .object("mutandi")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Mutandi")
            .primaryColor(DyeColor.GREEN)
            .secondaryColor(DyeColor.RED)
            .recipe(TransfigurationRecipeData::mutandiRecipes)
            .dust()
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapeless(context.get(), 9)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(Items.CACTUS)
                        .requires(Items.NETHER_WART)
                        .requires(ItemTags.TALL_FLOWERS)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                        .save(provider);

                ShapelessRecipeBuilder.shapeless(context.get(), 64)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(Items.SLIME_BLOCK)
                        .requires(Items.HEART_OF_THE_SEA)
                        .requires(Items.CRYING_OBSIDIAN)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                        .save(provider, ResourceLocationHelper.append(context.getId(), "_", "alt"));
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
            .keyword(TransfiguringKeyword.SPREADING)
            .primaryColor(DyeColor.YELLOW)
            .secondaryColor(DyeColor.GREEN)
            .skip(0.2F)
            .recipe(TransfigurationRecipeData::blessedRecipes)
            .dust()
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapeless(context.get(), 9)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(getDust(TransfigurationTypes.MUTANDI))
                        .requires(Tags.Items.GEMS_EMERALD)
                        .requires(Items.GLISTERING_MELON_SLICE)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                        .save(provider);

                ShapelessRecipeBuilder.shapeless(context.get(), 9)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(getDust(TransfigurationTypes.MUTANDI))
                        .requires(Tags.Items.GEMS_EMERALD)
                        .requires(Items.GOLDEN_CARROT)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                        .save(provider, ResourceLocationHelper.append(context.getId(), "_", "alt"));

                ShapelessRecipeBuilder.shapeless(context.get(), 32)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(getDust(TransfigurationTypes.MUTANDI))
                        .requires(Tags.Items.GEMS_EMERALD)
                        .requires(Items.TOTEM_OF_UNDYING)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                        .save(provider, ResourceLocationHelper.append(context.getId(), "_", "alt1"));

                ShapelessRecipeBuilder.shapeless(context.get(), 32)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(getDust(TransfigurationTypes.MUTANDI))
                        .requires(Tags.Items.GEMS_EMERALD)
                        .requires(Items.GOLDEN_APPLE)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                        .save(provider, ResourceLocationHelper.append(context.getId(), "_", "alt2"));
            })
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> DESTABILIZING = Transfiguration.getRegistrate()
            .object("destabilizing")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Destabilizing")
            .primaryColor(DyeColor.WHITE)
            .secondaryColor(DyeColor.GREEN)
            .recipe(TransfigurationRecipeData::destabilizingRecipes)
            .dust()
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapeless(context.get(), 9)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(Items.GRAVEL)
                        .requires(Items.BONE_MEAL)
                        .requires(Tags.Items.ENDER_PEARLS)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                        .save(provider);

                ShapelessRecipeBuilder.shapeless(context.get(), 32)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(Items.TNT)
                        .requires(Items.BONE_MEAL)
                        .requires(Tags.Items.ENDER_PEARLS)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                        .save(provider, ResourceLocationHelper.append(context.getId(), "_", "alt"));
            })
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> DISSOLUTION = Transfiguration.getRegistrate()
            .object("dissolution")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Dissolution")
            .primaryColor(DyeColor.BLUE)
            .secondaryColor(DyeColor.YELLOW)
            .recipe(TransfigurationRecipeData::dissolutionRecipes)
            .dust()
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapeless(context.get(), 9)
                    .requires(TransfigurationItems.MAGIC_POWDER.get(), 7)
                    .requires(Items.WATER_BUCKET)
                    .requires(Items.SAND)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                    .save(provider))
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> FUNGAL = Transfiguration.getRegistrate()
            .object("fungal")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Fungal")
            .primaryColor(DyeColor.BROWN)
            .secondaryColor(DyeColor.RED)
            .keyword(TransfiguringKeyword.CONTAGIOUS, TransfiguringKeyword.SPREADING)
            .recipe(TransfigurationRecipeData::fungalRecipes)
            .dust()
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapeless(context.get(), 9)
                    .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                    .requires(getDust(TransfigurationTypes.MUTANDI))
                    .requires(Items.RED_MUSHROOM)
                    .requires(Items.BROWN_MUSHROOM)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(context.get()))
                    .save(provider))
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> OVERNI = Transfiguration.getRegistrate()
            .object("overni")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Overni")
            .primaryColor(DyeColor.LIME)
            .secondaryColor(DyeColor.BLUE)
            .keyword(TransfiguringKeyword.CONTAGIOUS)
            .recipe(TransfigurationRecipeData::overniRecipes)
            .dust()
            .recipe((context, provider) -> {
                ShapelessRecipeBuilder.shapeless(context.get(), 9)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(Items.SMOOTH_STONE)
                        .requires(Items.GRASS)
                        .requires(Items.LILY_PAD)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(TransfigurationItems.MAGIC_POWDER.get()))
                        .save(provider);

                ShapelessRecipeBuilder.shapeless(context.get(), 9)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(Items.SMOOTH_STONE)
                        .requires(Items.TALL_GRASS)
                        .requires(Items.LILY_PAD)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(TransfigurationItems.MAGIC_POWDER.get()))
                        .save(provider, ResourceLocationHelper.append(context.getId(), "_", "tall_alt"));

                ShapelessRecipeBuilder.shapeless(context.get(), 24)
                        .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                        .requires(Items.SMOOTH_STONE)
                        .requires(Items.SCUTE)
                        .requires(Items.NAUTILUS_SHELL)
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(TransfigurationItems.MAGIC_POWDER.get()))
                        .save(provider, ResourceLocationHelper.append(context.getId(), "_", "alt"));
            })
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();

    public static final RegistryEntry<TransfigurationType> ANIMATION = Transfiguration.getRegistrate()
            .object("animation")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Animation")
            .primaryColor(DyeColor.WHITE)
            .secondaryColor(DyeColor.ORANGE)
            .recipe(TransfigurationRecipeData::animationRecipes)
            .dust()
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapeless(context.get(), 6)
                    .requires(TransfigurationItems.MAGIC_POWDER.get(), 6)
                    .requires(getDust(TransfigurationTypes.MUTANDI))
                    .requires(Tags.Items.EGGS)
                    .requires(Items.JACK_O_LANTERN)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(getDust(TransfigurationTypes.MUTANDI)))
                    .save(provider))
            .build()
            .defaultCatalyst()
            .defaultWand()
            .register();


    public static Item getDust(Supplier<TransfigurationType> transfigurationTypeSupplier) {
        ResourceLocation registryName = getRegistry().getKey(transfigurationTypeSupplier.get());
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

    public static IForgeRegistry<TransfigurationType> getRegistry() {
        return REGISTRY.get();
    }

    @NotNull
    public static ResourceLocation getKey(TransfigurationType transfigurationType) {
        return Objects.requireNonNull(getRegistry().getKey(transfigurationType));
    }

    public static void setup() {
        RECIPE_TYPE_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
