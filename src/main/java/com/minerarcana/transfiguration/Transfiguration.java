package com.minerarcana.transfiguration;

import com.minerarcana.transfiguration.compat.cctweaked.CCTweaked;
import com.minerarcana.transfiguration.content.*;
import com.minerarcana.transfiguration.item.TransfiguringItemGroup;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Transfiguration.ID)
public class Transfiguration {
    public static final String ID = "transfiguration";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);
    private static final Lazy<Registrate> REGISTRATE = Lazy.of(() -> Registrate.create(ID)
            .creativeModeTab(TransfiguringItemGroup::new, "Transfiguration")
            .addDataGenerator(ProviderType.ENTITY_TAGS, TransfigurationAdditionalData::addEntityTypeTags)
            .addDataGenerator(ProviderType.BLOCK_TAGS, TransfigurationAdditionalData::addBlockTags)
            .addDataGenerator(ProviderType.LANG, TransfigurationAdditionalData::addLang)
            .addDataGenerator(ProviderType.ITEM_TAGS, TransfigurationAdditionalData::addItemTags)
    );

    public Transfiguration() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TransfigurationBlocks.setup();
        TransfigurationRecipes.register(modEventBus);
        TransfigurationPredicates.setup();
        TransfigurationTypes.setup();
        TransfigurationEntities.setup();
        TransfigurationItems.setup();
        TransfigurationAttributes.setup(modEventBus);
        TransfigurationParticles.setup();

        CCTweaked.setup();
    }

    public static Registrate getRegistrate() {
        return REGISTRATE.get();
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(ID, path);
    }
}
