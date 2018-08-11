package com.minerarcana.transfiguration;

import com.teamacronymcoders.base.BaseModFoundation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Transfiguration.ID, name = Transfiguration.NAME, version = Transfiguration.VERSION)
public class Transfiguration extends BaseModFoundation<Transfiguration> {
    public static final String ID = "transfiguration";
    public static final String NAME = "Transfiguration";
    public static final String VERSION = "@VERSION@";

    public Transfiguration() {
        super(ID, NAME, VERSION, CreativeTabs.FOOD);
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
    public boolean hasExternalResources() {
        return true;
    }

    @Override
    public Transfiguration getInstance() {
        return this;
    }
}
