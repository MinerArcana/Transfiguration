package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipeSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.block.SingleBlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.block.TagBlockIngredient;
import com.minerarcana.transfiguration.recipe.ingedient.block.TagBlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityTypeEntityIngredientSerializer;
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

    public static void register(IEventBus modEventBus) {
        SERIALIZERS.register(modEventBus);
    }
}
