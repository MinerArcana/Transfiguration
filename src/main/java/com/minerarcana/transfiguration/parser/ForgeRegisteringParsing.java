package com.minerarcana.transfiguration.parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.block.Block;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class ForgeRegisteringParsing {
    @Nonnull
    public static Block blockFromJson(String fieldName, JsonObject jsonObject) {
        return entryFromJson(ForgeRegistries.BLOCKS, fieldName, jsonObject);
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry<T>> T entryFromJson(IForgeRegistry<T> forgeRegistry, String fieldName,
                                                                     JsonObject jsonObject) throws JsonParseException {
        String entryName = JSONUtils.getString(jsonObject, fieldName);
        T value = forgeRegistry.getValue(new ResourceLocation(entryName));
        if (value == null) {
            throw new JsonParseException("No Registry Entry found for '" + entryName + "'");
        } else {
            return value;
        }
    }
}
