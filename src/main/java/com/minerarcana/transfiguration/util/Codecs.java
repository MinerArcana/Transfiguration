package com.minerarcana.transfiguration.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Objects;

public class Codecs {
    public static final Codec<Vec3> VECTOR = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("x").forGetter(Vec3::x),
            Codec.DOUBLE.fieldOf("y").forGetter(Vec3::y),
            Codec.DOUBLE.fieldOf("z").forGetter(Vec3::z)
    ).apply(instance, Vec3::new));

    public static <T extends IForgeRegistryEntry<T>> MapCodec<T> forRegistry(String fieldName, IForgeRegistry<T> registry) {
        return Codec.STRING.fieldOf(fieldName)
                .xmap(
                        string -> registry.getValue(new ResourceLocation(string)),
                        entry -> Objects.requireNonNull(entry.getRegistryName()).toString()
                );
    }
}
