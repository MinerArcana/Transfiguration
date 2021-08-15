package com.minerarcana.transfiguration;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.compat.cctweaked.CCTweaked;
import com.minerarcana.transfiguration.content.*;
import com.minerarcana.transfiguration.item.TransfiguringItemGroup;
import com.minerarcana.transfiguration.recipe.ingedient.block.BlockIngredientSerializer;
import com.minerarcana.transfiguration.recipe.ingedient.entity.EntityIngredientSerializer;
import com.minerarcana.transfiguration.recipe.result.ResultSerializer;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;

@Mod(Transfiguration.ID)
public class Transfiguration {
    public static final String ID = "transfiguration";

    public static IForgeRegistry<TransfigurationType> transfigurationTypes;
    public static IForgeRegistry<BlockIngredientSerializer<?>> blockIngredientSerializers;
    public static IForgeRegistry<EntityIngredientSerializer<?>> entityIngredientSerializers;
    public static IForgeRegistry<ResultSerializer<?>> resultSerializers;

    private static final Lazy<Registrate> REGISTRATE = Lazy.of(() -> Registrate.create(ID)
            .itemGroup(TransfiguringItemGroup::new, "Transfiguration")
            .addDataGenerator(ProviderType.ENTITY_TAGS, TransfigurationAdditionalData::addEntityTypeTags)
            .addDataGenerator(ProviderType.BLOCK_TAGS, TransfigurationAdditionalData::addBlockTags)
            .addDataGenerator(ProviderType.LANG, TransfigurationAdditionalData::addLang)
            .addDataGenerator(ProviderType.ITEM_TAGS, TransfigurationAdditionalData::addItemTags)
    );

    @SuppressWarnings("unchecked")
    public Transfiguration() {
        transfigurationTypes = new RegistryBuilder<TransfigurationType>()
                .setName(rl("transfiguration_types"))
                .setType(TransfigurationType.class)
                .create();

        makeRegistry("block_ingredient_serializers", BlockIngredientSerializer.class);
        makeRegistry("entity_ingredient_serializers", EntityIngredientSerializer.class);
        makeRegistry("result_serializers", ResultSerializer.class);
        blockIngredientSerializers = RegistryManager.ACTIVE.getRegistry(BlockIngredientSerializer.class);
        entityIngredientSerializers = RegistryManager.ACTIVE.getRegistry(EntityIngredientSerializer.class);
        resultSerializers = RegistryManager.ACTIVE.getRegistry(ResultSerializer.class);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TransfigurationBlocks.setup();
        TransfigurationRecipes.register(modEventBus);
        TransfigurationTypes.setup();
        TransfigurationEntities.setup();
        TransfigurationItems.setup();

        CCTweaked.setup();
    }

    public static Registrate getRegistrate() {
        return REGISTRATE.get();
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(ID, path);
    }

    private static <T extends IForgeRegistryEntry<T>> void makeRegistry(String name, Class<T> type) {
        new RegistryBuilder<T>()
                .setName(rl(name))
                .setType(type)
                .create();
    }
}
