package com.minerarcana.transfiguration.recipe.builder;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FinishedObject<T extends ISerializer<?> & IForgeRegistryEntry<T>> implements IFinishedObject<T> {
    private final T serializer;
    private final Consumer<JsonObject> serialize;
    private final Supplier<ResourceLocation> idSupplier;

    public FinishedObject(T serializer, Supplier<ResourceLocation> idSupplier, Consumer<JsonObject> serialize) {
        this.serializer = serializer;
        this.serialize = serialize;
        this.idSupplier = idSupplier;
    }

    @Override
    public void serialize(JsonObject jsonObject) {
        serialize.accept(jsonObject);
    }

    @Override
    @Nonnull
    public T getSerializer() {
        return serializer;
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return idSupplier.get();
    }
}
