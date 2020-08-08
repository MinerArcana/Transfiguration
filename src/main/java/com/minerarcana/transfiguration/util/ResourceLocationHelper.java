package com.minerarcana.transfiguration.util;

import net.minecraft.util.ResourceLocation;

public class ResourceLocationHelper {
    public static ResourceLocation append(ResourceLocation resourceLocation, String append) {
        return new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath() + "/" + append);
    }
}
