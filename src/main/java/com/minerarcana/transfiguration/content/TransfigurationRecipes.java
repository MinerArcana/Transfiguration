package com.minerarcana.transfiguration.content;

import com.google.common.base.Suppliers;
import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipeSerializer;
import com.minerarcana.transfiguration.recipe.dust.DustRecipe;
import com.minerarcana.transfiguration.recipe.dust.DustRecipeSerializer;
import com.minerarcana.transfiguration.recipe.entity.EntityTransfigurationRecipeSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.BasicIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.IngredientListIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.MatchIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.TagIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockPropertiesIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.logic.AndIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.logic.NotIngredient;
import com.minerarcana.transfiguration.recipe.result.*;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class TransfigurationRecipes {
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(
            ForgeRegistries.RECIPE_SERIALIZERS,
            Transfiguration.ID
    );

    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPE = DeferredRegister.create(
            Registry.RECIPE_TYPE_REGISTRY,
            Transfiguration.ID
    );

    @SuppressWarnings("UnstableApiUsage")
    public static ResourceKey<Registry<BasicIngredientSerializer<?>>> INGREDIENT_REGISTRY_KEY = Transfiguration.getRegistrate()
            .makeRegistry("ingredient_serializers", RegistryBuilder::new);

    private static final Supplier<IForgeRegistry<BasicIngredientSerializer<?>>> INGREDIENT_REGISTRY = Suppliers.memoize(() ->
            RegistryManager.ACTIVE.getRegistry(INGREDIENT_REGISTRY_KEY)
    );


    @SuppressWarnings("UnstableApiUsage")
    public static ResourceKey<Registry<ResultSerializer<?>>> RESULT_REGISTRY_KEY = Transfiguration.getRegistrate()
            .makeRegistry("result_serializers", RegistryBuilder::new);

    private static final Supplier<ForgeRegistry<ResultSerializer<?>>> RESULT_REGISTRY = Suppliers.memoize(() ->
            RegistryManager.ACTIVE.getRegistry(RESULT_REGISTRY_KEY)
    );

    public static final RegistryObject<BlockTransfigurationRecipeSerializer> BLOCK_TRANSFIGURATION = SERIALIZERS.register(
            "block_transfiguration", BlockTransfigurationRecipeSerializer::new
    );

    public static final RegistryEntry<EntityTransfigurationRecipeSerializer> ENTITY_TRANSFIGURATION =
            Transfiguration.getRegistrate()
                    .object("entity_transfiguration")
                    .simple(Registry.RECIPE_SERIALIZER_REGISTRY, EntityTransfigurationRecipeSerializer::new);

    public static final RegistryEntry<MatchIngredientSerializer> MATCH_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("match")
                    .simple(INGREDIENT_REGISTRY_KEY, MatchIngredientSerializer::new);

    public static final RegistryEntry<IngredientListIngredientSerializer<NotIngredient>> NOT_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("not")
                    .simple(INGREDIENT_REGISTRY_KEY, () -> new IngredientListIngredientSerializer<>(
                            NotIngredient::new,
                            NotIngredient::getIngredients
                    ));

    public static final RegistryEntry<TagIngredientSerializer> TAG_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("tag")
                    .simple(INGREDIENT_REGISTRY_KEY, TagIngredientSerializer::new);

    public static final RegistryEntry<IngredientListIngredientSerializer<AndIngredient>> AND_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("and")
                    .simple(INGREDIENT_REGISTRY_KEY, () -> new IngredientListIngredientSerializer<>(
                            AndIngredient::new,
                            AndIngredient::getBasicIngredientList
                    ));

    public static final RegistryEntry<BlockPropertiesIngredientSerializer> BLOCK_PROPERTIES_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("block_properties")
                    .simple(INGREDIENT_REGISTRY_KEY, BlockPropertiesIngredientSerializer::new);

    public static final RegistryEntry<BlockStateResultSerializer> BLOCK_STATE_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("blockstate")
                    .simple(RESULT_REGISTRY_KEY, BlockStateResultSerializer::new);

    public static final RegistryEntry<EntityResultSerializer> ENTITY_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("entity")
                    .simple(RESULT_REGISTRY_KEY, EntityResultSerializer::new);

    public static final RegistryEntry<EntityTagResultSerializer> ENTITY_TAG_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("entity_tag")
                    .simple(RESULT_REGISTRY_KEY, EntityTagResultSerializer::new);

    public static final RegistryEntry<BlockTagResultSerializer> BLOCK_TAG_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("block_tag")
                    .simple(RESULT_REGISTRY_KEY, BlockTagResultSerializer::new);

    public static final RegistryEntry<BlockTagWithPropertyResultSerializer> BLOCK_TAG_WITH_PROPERTY_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("block_tag_with_property")
                    .simple(RESULT_REGISTRY_KEY, BlockTagWithPropertyResultSerializer::new);

    public static final RegistryEntry<ItemResultSerializer> ITEM_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("item")
                    .simple(RESULT_REGISTRY_KEY, ItemResultSerializer::new);

    public static final RegistryEntry<ChanceResultSerializer> CHANCE_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("chance")
                    .simple(RESULT_REGISTRY_KEY, ChanceResultSerializer::new);

    public static final RegistryEntry<BlankResultSerializer<FallingBlockResult>> FALLING_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("falling_block")
                    .simple(RESULT_REGISTRY_KEY, () -> new BlankResultSerializer<>(FallingBlockResult::new));

    public static final RegistryEntry<WeightedResultSerializer> WEIGHTED_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("weighted")
                    .simple(RESULT_REGISTRY_KEY, WeightedResultSerializer::new);

    public static final RegistryEntry<RecipeSerializer<DustRecipe>> DUST_RECIPE_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("dust")
                    .simple(Registry.RECIPE_SERIALIZER_REGISTRY, DustRecipeSerializer::new);

    public static final RegistryObject<RecipeType<DustRecipe>> DUST_RECIPE_TYPE = RECIPE_TYPE.register("dust", () -> new RecipeType<>() {
        public String toString() {
            return "transfiguration:dust";
        }
    });

    public static IForgeRegistry<BasicIngredientSerializer<?>> getIngredientRegistry() {
        return INGREDIENT_REGISTRY.get();
    }

    @NotNull
    public static ResourceLocation getKey(BasicIngredientSerializer<?> ingredientSerializer) {
        return Objects.requireNonNull(getIngredientRegistry().getKey(ingredientSerializer));
    }

    public static IForgeRegistry<ResultSerializer<?>> getResultRegistry() {
        return RESULT_REGISTRY.get();
    }

    @NotNull
    public static ResourceLocation getKey(ResultSerializer<?> resultSerializer) {
        return Objects.requireNonNull(getResultRegistry().getKey(resultSerializer));
    }

    public static void register(IEventBus modEventBus) {
        SERIALIZERS.register(modEventBus);
        RECIPE_TYPE.register(modEventBus);
    }
}
