package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class TransfigurationEntityTypeTags {
    public static TagKey<EntityType<?>> OUTPUTS_ACCURSED = createTagKey("outputs/accursed");

    public static TagKey<EntityType<?>> VILLAGER = createTagKey("forge", "villagers");

    public static TagKey<EntityType<?>> createTagKey(String id, String path) {
        return Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags())
                .createTagKey(new ResourceLocation(id, path));
    }

    public static TagKey<EntityType<?>> createTagKey(String path) {
        return Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags())
                .createTagKey(Transfiguration.rl(path));
    }
}
