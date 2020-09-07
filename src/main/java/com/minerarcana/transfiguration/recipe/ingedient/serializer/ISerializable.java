package com.minerarcana.transfiguration.recipe.ingedient.serializer;

import javax.annotation.Nonnull;

public interface ISerializable<T extends ISerializer<?>> {
    @Nonnull
    T getSerializer();
}
