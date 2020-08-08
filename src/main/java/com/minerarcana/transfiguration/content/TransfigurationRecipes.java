package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipeSerializer;
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

    public static void register(IEventBus modEventBus) {
        SERIALIZERS.register(modEventBus);
    }
}
