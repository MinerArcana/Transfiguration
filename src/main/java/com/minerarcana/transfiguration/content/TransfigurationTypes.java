package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.registrate.TransfigurationTypeBuilder;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.item.DyeColor;

public class TransfigurationTypes {
    private static final NonNullBiFunction<String, BuilderCallback, TransfigurationTypeBuilder<TransfigurationType,
            AbstractRegistrate<?>>> TRANSFIGURATION_TYPE = TransfigurationTypeBuilder.entry(Transfiguration::getRegistrate);

    public static final RegistryEntry<TransfigurationType> NETHERI = Transfiguration.getRegistrate()
            .object("netheri")
            .entry(TRANSFIGURATION_TYPE)
            .lang("Netheri")
            .primaryColor(DyeColor.RED.getColorValue())
            .dust()
            .build()
            .catalyst()
            .build()
            .wand()
            .build()
            .register();

    public static void setup() {

    }
}
