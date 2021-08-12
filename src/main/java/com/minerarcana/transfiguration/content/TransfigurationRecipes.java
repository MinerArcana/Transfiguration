package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipeSerializer;
import com.minerarcana.transfiguration.recipe.dust.DustRecipe;
import com.minerarcana.transfiguration.recipe.dust.DustRecipeSerializer;
import com.minerarcana.transfiguration.recipe.entity.EntityTransfigurationRecipeSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.block.*;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityTypeEntityIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.entity.TagEntityIngredientSerializer;
import com.minerarcana.transfiguration.recipe.result.*;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TransfigurationRecipes {
    private static final DeferredRegister<IRecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(
            ForgeRegistries.RECIPE_SERIALIZERS, Transfiguration.ID);

    public static final RegistryObject<BlockTransfigurationRecipeSerializer> BLOCK_TRANSFIGURATION = SERIALIZERS.register(
            "block_transfiguration", BlockTransfigurationRecipeSerializer::new
    );

    public static final RegistryEntry<EntityTransfigurationRecipeSerializer> ENTITY_TRANSFIGURATION =
            Transfiguration.getRegistrate()
                    .object("entity_transfiguration")
                    .simple(IRecipeSerializer.class, EntityTransfigurationRecipeSerializer::new);

    public static final RegistryEntry<BlockIngredientSerializer<TagBlockIngredient>> TAG_BLOCK_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("tag")
                    .simple(BlockIngredientSerializer.class, TagBlockIngredientSerializer::new);

    public static final RegistryEntry<SingleBlockIngredientSerializer> SINGLE_BLOCK_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("block")
                    .simple(BlockIngredientSerializer.class, SingleBlockIngredientSerializer::new);

    public static final RegistryEntry<NotBlockIngredientSerializer> NOT_BLOCK_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("not")
                    .simple(BlockIngredientSerializer.class, NotBlockIngredientSerializer::new);

    public static final RegistryEntry<BlankIngredientSerializer<TrueIngredient>> TRUE_BLOCK_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("true")
                    .simple(BlockIngredientSerializer.class, () -> new BlankIngredientSerializer<>(TrueIngredient::new));

    public static final RegistryEntry<EntityTypeEntityIngredientSerializer> ENTITY_TYPE_ENTITY_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("entity_type")
                    .simple(EntityIngredientSerializer.class, EntityTypeEntityIngredientSerializer::new);

    public static final RegistryEntry<TagEntityIngredientSerializer> TAG_ENTITY_INGREDIENT_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("tag")
                    .simple(EntityIngredientSerializer.class, TagEntityIngredientSerializer::new);

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

    public static final RegistryEntry<IRecipeSerializer<DustRecipe>> DUST_RECIPE_SERIALIZER =
            Transfiguration.getRegistrate()
                    .object("dust")
                    .simple(IRecipeSerializer.class, DustRecipeSerializer::new);

    public static final IRecipeType<DustRecipe> DUST_RECIPE_TYPE = IRecipeType.register(Transfiguration.rl("dust").toString());

    public static void register(IEventBus modEventBus) {
        SERIALIZERS.register(modEventBus);
    }
}
