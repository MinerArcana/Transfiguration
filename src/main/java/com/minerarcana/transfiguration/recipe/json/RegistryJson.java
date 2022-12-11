package com.minerarcana.transfiguration.recipe.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

public class RegistryJson {
    @Nonnull
    public static <T> T getValue(JsonObject jsonObject, String fieldName, IForgeRegistry<T> forgeRegistry) {
        JsonPrimitive field = jsonObject.getAsJsonPrimitive(fieldName);
        if (field == null) {
            throw new JsonParseException("Failed to find Value for Field '" + fieldName + "'");
        } else {
            T value = forgeRegistry.getValue(new ResourceLocation(field.getAsString()));
            if (value == null) {
                throw new JsonParseException("Failed to find Object for Value'" + field.getAsString() + "'");
            } else {
                return value;
            }
        }
    }

    @Nonnull
    public static Block getBlock(JsonObject jsonObject, String fieldName) {
        return getValue(jsonObject, fieldName, ForgeRegistries.BLOCKS);
    }

    @Nonnull
    public static Block getBlock(JsonObject jsonObject) {
        return getBlock(jsonObject, "block");
    }

    @Nonnull
    public static EntityType<?> getEntity(JsonObject jsonObject, String fieldName) {
        return getValue(jsonObject, fieldName, ForgeRegistries.ENTITY_TYPES);
    }

    @Nonnull
    public static EntityType<?> getEntity(JsonObject jsonObject) {
        return getEntity(jsonObject, "entity");
    }

    @Nonnull
    public static TransfigurationType getTransfigurationType(JsonObject jsonObject, String fieldName) {
        return getValue(jsonObject, fieldName, TransfigurationTypes.getRegistry());
    }

    @Nonnull
    public static TransfigurationType getTransfigurationType(JsonObject jsonObject) {
        return getTransfigurationType(jsonObject, "transfigurationType");
    }
}
