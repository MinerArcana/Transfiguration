package com.minerarcana.transfiguration.recipe.nbt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class NBTCopier {
    private final List<NBTCopyOperation> operations;

    public NBTCopier(List<NBTCopyOperation> operations) {
        this.operations = operations;
    }

    public boolean hasOperations() {
        return !this.operations.isEmpty();
    }

    public void copy(Tag source, Supplier<Tag> target) {
        if (source != null) {
            this.operations.forEach((p_80255_) -> {
                p_80255_.apply(target, source);
            });
        }
    }

    public JsonElement toJson() {
        if (this.operations.isEmpty()) {
            return JsonNull.INSTANCE;
        } else {
            JsonArray array = new JsonArray();
            for (NBTCopyOperation operation : this.operations) {
                array.add(operation.toJson());
            }
            return array;
        }

    }

    public static NBTCopier fromJson(@Nullable JsonElement jsonElement) {
        List<NBTCopyOperation> operations = new ArrayList<>();

        if (jsonElement != null) {
            if (jsonElement.isJsonObject()) {
                operations.add(NBTCopyOperation.fromJson(jsonElement.getAsJsonObject()));
            } else if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    operations.add(NBTCopyOperation.fromJson(jsonArray.get(i).getAsJsonObject()));
                }
            }
        }

        return new NBTCopier(operations);
    }

    public static NBTCopierBuilder builder() {
        return new NBTCopierBuilder();
    }

}
