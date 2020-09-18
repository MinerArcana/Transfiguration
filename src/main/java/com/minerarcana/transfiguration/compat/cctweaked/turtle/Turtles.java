package com.minerarcana.transfiguration.compat.cctweaked.turtle;

import com.minerarcana.transfiguration.content.TransfigurationTypes;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class Turtles {
    public static void handleTurtles(RegistryEvent.Register<Item> itemRegister) {
        Turtles.createTurtleFor(TransfigurationTypes.NETHERI);
    }

    public static void createTurtleFor(RegistryEntry<TransfigurationType> transfigurationType) {
        ComputerCraftAPI.registerTurtleUpgrade(new TransfiguringTurtleUpgrade(transfigurationType));
    }
}
