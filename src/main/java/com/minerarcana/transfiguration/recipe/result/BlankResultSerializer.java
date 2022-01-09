package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class BlankResultSerializer<T extends Result> extends ResultSerializer<T> {
    private final Supplier<T> supplier;

    public BlankResultSerializer(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Nonnull
    @Override
    public T parse(@Nonnull PacketBuffer buffer) {
        return supplier.get();
    }

    @Nonnull
    @Override
    public T parse(@Nonnull JsonObject json) throws JsonParseException {
        return supplier.get();
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull T object) {

    }
}
