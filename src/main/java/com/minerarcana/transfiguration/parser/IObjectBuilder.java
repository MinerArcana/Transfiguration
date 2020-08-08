package com.minerarcana.transfiguration.parser;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public interface IObjectBuilder<T> {

    T build(@Nullable ResourceLocation resourceLocation);
}
