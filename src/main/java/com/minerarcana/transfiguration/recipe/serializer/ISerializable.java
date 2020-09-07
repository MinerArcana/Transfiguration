package com.minerarcana.transfiguration.recipe.serializer;

import javax.annotation.Nonnull;

public interface ISerializable<T extends ISerializer<?>> {
    @Nonnull
    T getSerializer();
}
