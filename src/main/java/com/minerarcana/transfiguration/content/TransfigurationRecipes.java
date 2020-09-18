package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipeSerializer;
import com.minerarcana.transfiguration.recipe.entity.EntityTransfigurationRecipeSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.block.SingleBlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.block.TagBlockIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.block.TagBlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityTypeEntityIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.entity.TagEntityIngredientSerializer;
import com.minerarcana.transfiguration.recipe.result.BlockStateResultSerializer;
import com.minerarcana.transfiguration.recipe.result.EntityResultSerializer;
import com.minerarcana.transfiguration.recipe.result.EntityTagResultSerializer;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.item.crafting.IRecipeSerializer;
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

    public static void register(IEventBus modEventBus) {
        SERIALIZERS.register(modEventBus);
    }
}
