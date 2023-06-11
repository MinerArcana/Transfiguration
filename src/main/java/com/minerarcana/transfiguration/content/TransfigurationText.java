package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import net.minecraft.network.chat.Component;

public class TransfigurationText {

    public static Component BLOCK_TRANSFIGURATION = Transfiguration.getRegistrate()
            .addLang("screen", Transfiguration.rl("block_transfiguration"), "Block Transfiguration");

    public static Component ENTITY_TRANSFIGURATION = Transfiguration.getRegistrate()
            .addLang("screen", Transfiguration.rl("entity_transfiguration"), "Entity Transfiguration");

    public static void setup() {

    }
}
