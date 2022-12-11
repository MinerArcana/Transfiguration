package com.minerarcana.transfiguration.recipe.builder;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.recipe.serializer.ISerializer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public interface IFinishedObject<T extends ISerializer<?>> {
    void serialize(JsonObject jsonObject);

    @Nonnull
    default JsonObject getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", this.getSerializer().getKey().toString());
        this.serialize(jsonObject);
        return jsonObject;
    }

    @NotNull
    T getSerializer();

    @NotNull
    ResourceLocation getId();
}
