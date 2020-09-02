package com.minerarcana.transfiguration;

import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import com.minerarcana.transfiguration.item.TransfiguringItemGroup;
import com.minerarcana.transfiguration.parser.FunctionObjectParser;
import com.minerarcana.transfiguration.parser.IObjectParser;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import com.tterrag.registrate.Registrate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.Objects;

@Mod(Transfiguration.ID)
public class Transfiguration {
    public static final String ID = "transfiguration";

    public static IForgeRegistry<TransfigurationType> transfigurationTypes;

    public static final IObjectParser<TransfigurationType> TRANSFIGURATION_TYPE_PARSER = new FunctionObjectParser<>(
            transfigurationType -> transfigurationTypes.getValue(new ResourceLocation(transfigurationType)),
            transfigurationType -> Objects.requireNonNull(transfigurationType.getRegistryName()).toString()
    );

    private static final Lazy<Registrate> REGISTRATE = Lazy.of(() -> Registrate.create(ID)
            .itemGroup(TransfiguringItemGroup::new, "Transfiguration"));

    public Transfiguration() {

        transfigurationTypes = new RegistryBuilder<TransfigurationType>()
                .setName(rl("transfiguration_types"))
                .setType(TransfigurationType.class)
                .create();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TransfigurationRecipes.register(modEventBus);
        TransfigurationTypes.setup();
        TransfigurationEntities.setup();

        modEventBus.addListener(this::handleClient);
    }

    public static Registrate getRegistrate() {
        return REGISTRATE.get();
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(ID, path);
    }

    public void handleClient(FMLClientSetupEvent clientSetupEvent) {
        EntityRendererManager rendererManager = Minecraft.getInstance().getRenderManager();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        rendererManager.register(TransfigurationEntities.TRANSFIGURING_PROJECTILE.get(),
                new SpriteRenderer<>(rendererManager, itemRenderer));
    }
}
