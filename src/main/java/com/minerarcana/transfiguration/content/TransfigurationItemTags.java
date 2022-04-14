package com.minerarcana.transfiguration.content;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class TransfigurationItemTags {
    public static final TagKey<Item> CORALS = createTagKey(new ResourceLocation("forge", "corals"));

    public static TagKey<Item> createTagKey(ResourceLocation path) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.tags())
                .createTagKey(path);
    }
}
