package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.item.TransfiguringItem;
import com.minerarcana.transfiguration.item.TransfiguringWandItem;
import com.tterrag.registrate.util.entry.RegistryEntry;

public class TransfigurationItems {

    public static final RegistryEntry<TransfiguringItem> NETHERI_DUST = Transfiguration.getRegistrate()
            .object("netheri_dust")
            .item(properties -> new TransfiguringItem(Transfiguration.NETHERI, properties))
            .register();

    public static final RegistryEntry<TransfiguringWandItem> NETHERI_WAND = Transfiguration.getRegistrate()
            .object("netheri_wand")
            .item(properties -> new TransfiguringWandItem(Transfiguration.NETHERI, properties))
            .register();

    public static void setup() {

    }
}
