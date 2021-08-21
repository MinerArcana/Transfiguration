package com.minerarcana.transfiguration.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Objects;

public class Codecs {
    public static final Codec<Vector3d> VECTOR = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("x").forGetter(Vector3d::getX),
            Codec.DOUBLE.fieldOf("y").forGetter(Vector3d::getY),
            Codec.DOUBLE.fieldOf("z").forGetter(Vector3d::getZ)
    ).apply(instance, Vector3d::new));

    public static <T extends IForgeRegistryEntry<T>> MapCodec<T> forRegistry(String fieldName, IForgeRegistry<T> registry) {
        return Codec.STRING.fieldOf(fieldName)
                .xmap(
                        string -> registry.getValue(new ResourceLocation(string)),
                        entry -> Objects.requireNonNull(entry.getRegistryName()).toString()
                );
    }
}
