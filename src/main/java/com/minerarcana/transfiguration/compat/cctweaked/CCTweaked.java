package com.minerarcana.transfiguration.compat.cctweaked;

import com.minerarcana.transfiguration.compat.cctweaked.turtle.Turtles;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CCTweaked {

    public static void setup() {
        if (ModList.get().isLoaded("computercraft")) {
            Turtles.setup();
        }
    }
}
