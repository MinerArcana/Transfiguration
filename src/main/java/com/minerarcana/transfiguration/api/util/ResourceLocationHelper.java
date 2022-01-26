package com.minerarcana.transfiguration.api.util;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class ResourceLocationHelper {
    public static ResourceLocation append(@Nullable ResourceLocation resourceLocation, String append) {
        return append(resourceLocation, "/", append);
    }

    public static ResourceLocation append(@Nullable ResourceLocation resourceLocation, String separator, String append) {
        if (resourceLocation != null) {
            return new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath() + separator + append);
        } else {
            throw new IllegalStateException("Registry Name called for before Set");
        }
    }
}
