package com.minerarcana.transfiguration;

import com.google.common.collect.Lists;
import com.minerarcana.transfiguration.item.ItemMagicPowder;
import com.minerarcana.transfiguration.item.ItemWand;
import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.event.BaseRegistryEvent;
import com.teamacronymcoders.base.recipesystem.RecipeType;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@EventBusSubscriber
@Mod(modid = Transfiguration.ID, name = Transfiguration.NAME, version = Transfiguration.VERSION)
public class Transfiguration extends BaseModFoundation<Transfiguration> {
    public static final String ID = "transfiguration";
    public static final String NAME = "Transfiguration";
    public static final String VERSION = "@VERSION@";

    public static final List<RecipeType> recipeTypes = Lists.newArrayList();

    public Transfiguration() {
        super(ID, NAME, VERSION, CreativeTabs.MISC);
    }

    @EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @EventHandler
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @EventHandler
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void registerItems(ItemRegistry registry) {
        super.registerItems(registry);
        registry.register(new ItemWand());

        String[] powders = new String[]{"accursed", "lucky"};
        for (String powder : powders) {
            ItemMagicPowder itemMagicPowder = new ItemMagicPowder(powder);
            registry.register(itemMagicPowder);
            recipeTypes.add(itemMagicPowder.getRecipeType());
        }
    }

    @Override
    public boolean hasExternalResources() {
        return true;
    }

    @Override
    public Transfiguration getInstance() {
        return this;
    }

    @SubscribeEvent
    public static void registerTypes(BaseRegistryEvent<RecipeType> registryEvent) {
        registryEvent.register(new ResourceLocation(ID, "wand"), new RecipeType(new ResourceLocation(ID, "wand")));
        for (RecipeType recipeType : recipeTypes) {
            registryEvent.register(new ResourceLocation(recipeType.name), recipeType);
        }
    }
}
