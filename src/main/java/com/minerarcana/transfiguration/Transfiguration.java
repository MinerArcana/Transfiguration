package com.minerarcana.transfiguration;

import com.google.common.collect.Maps;
import com.minerarcana.transfiguration.content.TransfigurationItems;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.parser.FunctionObjectParser;
import com.minerarcana.transfiguration.parser.IObjectParser;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;

@Mod(Transfiguration.ID)
public class Transfiguration {
    public static final String ID = "transfiguration";

    public static final Map<String, TransfigurationType> TRANSFIGURATION_TYPES = Maps.newHashMap();

    public static final IObjectParser<TransfigurationType> TRANSFIGURATION_TYPE_PARSER = new FunctionObjectParser<>(
        TRANSFIGURATION_TYPES::get, transfigurationType -> transfigurationType.getBlockRecipeId().toString());

    public static final TransfigurationType NETHERI = new TransfigurationType("netheri");

    public Transfiguration() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TransfigurationRecipes.register(modEventBus);
        TransfigurationItems.register(modEventBus);

        TRANSFIGURATION_TYPES.put(Transfiguration.rl("netheri").toString(), NETHERI);
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(ID, path);
    }
}
