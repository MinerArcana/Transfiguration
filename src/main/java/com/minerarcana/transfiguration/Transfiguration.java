package com.minerarcana.transfiguration;

import com.google.common.collect.Maps;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.content.TransfigurationItems;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.entity.TransfiguringProjectile;
import com.minerarcana.transfiguration.item.TransfiguringItemGroup;
import com.minerarcana.transfiguration.parser.FunctionObjectParser;
import com.minerarcana.transfiguration.parser.IObjectParser;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import com.tterrag.registrate.Registrate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;

@Mod(Transfiguration.ID)
public class Transfiguration {
    public static final String ID = "transfiguration";

    public static final Map<String, TransfigurationType> TRANSFIGURATION_TYPES = Maps.newHashMap();

    public static final IObjectParser<TransfigurationType> TRANSFIGURATION_TYPE_PARSER = new FunctionObjectParser<>(
            TRANSFIGURATION_TYPES::get, transfigurationType -> transfigurationType.getBlockRecipeId().toString());

    public static final TransfigurationType NETHERI = new TransfigurationType("netheri");

    private static final Lazy<Registrate> REGISTRATE = Lazy.of(() -> Registrate.create(ID));

    public Transfiguration() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TransfigurationRecipes.register(modEventBus);
        TransfigurationItems.setup();
        TransfigurationEntities.setup();

        modEventBus.addListener(this::handleClient);
        TRANSFIGURATION_TYPES.put(Transfiguration.rl("netheri").toString(), NETHERI);

        Transfiguration.getRegistrate()
                .itemGroup(TransfiguringItemGroup::new, "Transfiguration");
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
