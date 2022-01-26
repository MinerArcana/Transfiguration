package com.minerarcana.transfiguration.content;

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
import com.minerarcana.transfiguration.recipe.result.BlankResultSerializer;
import com.minerarcana.transfiguration.recipe.result.BlockStateResultSerializer;
import com.minerarcana.transfiguration.recipe.result.BlockTagResultSerializer;
import com.minerarcana.transfiguration.recipe.result.ChanceResultSerializer;
import com.minerarcana.transfiguration.recipe.result.EntityResultSerializer;
import com.minerarcana.transfiguration.recipe.result.EntityTagResultSerializer;
import com.minerarcana.transfiguration.recipe.result.FallingBlockResult;
import com.minerarcana.transfiguration.recipe.result.ItemResultSerializer;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TransfigurationRecipes {
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(
            ForgeRegistries.RECIPE_SERIALIZERS, Transfiguration.ID);

    public static final RegistryObject<BlockTransfigurationRecipeSerializer> BLOCK_TRANSFIGURATION = SERIALIZERS.register(
            "block_transfiguration", BlockTransfigurationRecipeSerializer::new
    );

    public static final RegistryEntry<EntityTransfigurationRecipeSerializer> ENTITY_TRANSFIGURATION =
            Transfiguration.getRegistrate()
                    .object("entity_transfiguration")
                    .simple(RecipeSerializer.class, EntityTransfigurationRecipeSerializer::new);

    public static final RegistryEntry<MatchIngredientSerializer> MATCH_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("match")
                    .simple(BasicIngredientSerializer.class, MatchIngredientSerializer::new);

    public static final RegistryEntry<IngredientListIngredientSerializer<NotIngredient>> NOT_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("not")
                    .simple(BasicIngredientSerializer.class, () -> new IngredientListIngredientSerializer<>(
                            NotIngredient::new,
                            NotIngredient::getIngredients
                    ));

    public static final RegistryEntry<TagIngredientSerializer> TAG_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("tag")
                    .simple(BasicIngredientSerializer.class, TagIngredientSerializer::new);

    public static final RegistryEntry<IngredientListIngredientSerializer<AndIngredient>> AND_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("and")
                    .simple(BasicIngredientSerializer.class, () -> new IngredientListIngredientSerializer<>(
                            AndIngredient::new,
                            AndIngredient::getBasicIngredientList
                    ));

    public static final RegistryEntry<BlockPropertiesIngredientSerializer> BLOCK_PROPERTIES_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("block_properties")
                    .simple(BasicIngredientSerializer.class, BlockPropertiesIngredientSerializer::new);

    public static final RegistryEntry<BlockStateResultSerializer> BLOCK_STATE_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("blockstate")
                    .simple(ResultSerializer.class, BlockStateResultSerializer::new);

    public static final RegistryEntry<EntityResultSerializer> ENTITY_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("entity")
                    .simple(ResultSerializer.class, EntityResultSerializer::new);

    public static final RegistryEntry<EntityTagResultSerializer> ENTITY_TAG_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("entity_tag")
                    .simple(ResultSerializer.class, EntityTagResultSerializer::new);

    public static final RegistryEntry<BlockTagResultSerializer> BLOCK_TAG_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("block_tag")
                    .simple(ResultSerializer.class, BlockTagResultSerializer::new);

    public static final RegistryEntry<ItemResultSerializer> ITEM_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("item")
                    .simple(ResultSerializer.class, ItemResultSerializer::new);

    public static final RegistryEntry<ChanceResultSerializer> CHANCE_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("chance")
                    .simple(ResultSerializer.class, ChanceResultSerializer::new);

    public static final RegistryEntry<BlankResultSerializer<FallingBlockResult>> FALLING_RESULT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("falling_block")
                    .simple(ResultSerializer.class, () -> new BlankResultSerializer<>(FallingBlockResult::new));

    public static final RegistryEntry<RecipeSerializer<DustRecipe>> DUST_RECIPE_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("dust")
                    .simple(RecipeSerializer.class, DustRecipeSerializer::new);

    public static final RecipeType<DustRecipe> DUST_RECIPE_TYPE = RecipeType.register(Transfiguration.rl("dust").toString());

    public static void register(IEventBus modEventBus) {
        SERIALIZERS.register(modEventBus);
    }
}
