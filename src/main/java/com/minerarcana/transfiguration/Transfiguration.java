package com.minerarcana.transfiguration;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.event.BaseRegistryEvent;
import com.teamacronymcoders.base.recipesystem.type.ClickRecipeType;
import com.teamacronymcoders.base.recipesystem.type.RecipeType;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@EventBusSubscriber(modid = Transfiguration.ID)
@Mod(modid = Transfiguration.ID, name = Transfiguration.NAME, version = Transfiguration.VERSION)
public class Transfiguration extends BaseModFoundation<Transfiguration> {
    public static final String ID = "transfiguration";
    public static final String NAME = "Transfiguration";
    public static final String VERSION = "@VERSION@";

    public static final List<RecipeType> recipeTypes = Lists.newArrayList();

    public Configuration configuration;

    public Transfiguration() {
        super(ID, NAME, VERSION, CreativeTabs.MISC);
    }

    @EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        configuration = new Configuration(event.getSuggestedConfigurationFile());
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
        Property createDefaults = configuration.get("createDefaults", "general", true, "Will force a regen of config");

        if (createDefaults.getBoolean()) {
            configuration.getString("name", "magics.accursed", "Accursed", "The Name for this Magic Type");
            configuration.getString("color", "magics.accursed", "000000", "The Hex Color for this Magic Type");

            configuration.getString("name", "magics.lucky", "Lucky", "The Name for this Magic Type");
            configuration.getString("color", "magics.lucky", "FFFFFF", "The Hex Color for this Magic Type");

            createDefaults.set(false);
        }

        ConfigCategory magics = configuration.getCategory("magics");
        for (ConfigCategory magicType : magics.getChildren()) {
            String name = magicType.get("name").getString().toLowerCase();
            String color = magicType.get("color").getString();
            RecipeType recipeType = new ClickRecipeType(new ResourceLocation(ID, name));

            ItemTransfiguring itemMagicPowder = new ItemTransfiguring(name + "_powder", recipeType, color,
                    tuple -> tuple.getFirst().shrink(1));

            ItemTransfiguring itemMagicWand = new ItemTransfiguring(name + "_wand", recipeType, color,
                    tuple -> tuple.getFirst().damageItem(1, tuple.getSecond()));
            itemMagicWand.setMaxDamage(2056);

            registry.register(itemMagicPowder);
            registry.register(itemMagicWand);
            recipeTypes.add(recipeType);
        }

        if (configuration.hasChanged()) {
            configuration.save();
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
