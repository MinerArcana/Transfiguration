package com.minerarcana.transfiguration.recipe.builder;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Objects;

public interface IFinishedObject<T extends ISerializer<?> & IForgeRegistryEntry<T>> {
    void serialize(JsonObject jsonObject);

    @Nonnull
    default JsonObject getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", Objects.requireNonNull(this.getSerializer().getRegistryName()).toString());
        this.serialize(jsonObject);
        return jsonObject;
    }

    @Nonnull
    T getSerializer();

    @Nonnull
    ResourceLocation getId();
}
