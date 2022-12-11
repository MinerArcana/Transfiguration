package com.minerarcana.transfiguration.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class RegistryHelpers {
    public static <T> Function<T, ResourceLocation> getRegistryName(IForgeRegistry<T> registry) {
        return registry::getKey;
    }

    public static <T> ResourceLocation getRegistryName(IForgeRegistry<T> registry, T entry) {
        return Objects.requireNonNull(registry.getKey(entry));
    }

    public static <T> Supplier<ResourceLocation> supplyRegistryName(IForgeRegistry<T> registry, T entry) {
        return () -> Objects.requireNonNull(registry.getKey(entry));
    }

    public static ResourceLocation getRegistryName(ItemStack itemStack) {
        return getRegistryName(ForgeRegistries.ITEMS, itemStack.getItem());
    }

    public static Supplier<ResourceLocation> supplyRegistryName(ItemStack itemStack) {
        return () -> getRegistryName(itemStack);
    }
}
