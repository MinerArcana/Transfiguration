package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class TransfigurationEntityTypeTags {
    public static TagKey<EntityType<?>> OUTPUTS_ACCURSED = createTagKey("outputs/accursed");

    public static TagKey<EntityType<?>> createTagKey(String path) {
        return Objects.requireNonNull(ForgeRegistries.ENTITIES.tags())
                .createTagKey(Transfiguration.rl(path));
    }
}
