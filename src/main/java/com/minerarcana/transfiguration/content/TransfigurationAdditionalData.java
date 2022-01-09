package com.minerarcana.transfiguration.content;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;

public class TransfigurationAdditionalData {

    public static void addLang(RegistrateLangProvider langProvider) {
        langProvider.add("upgrade.transfiguration.transfiguring.advective", "Transfiguring");
    }

    public static void addEntityTypeTags(RegistrateTagsProvider<EntityType<?>> tagsProvider) {
        tagsProvider.getOrCreateBuilder(TransfigurationEntityTypeTags.OUTPUTS_ACCURSED)
                .add(
                        EntityType.WITCH,
                        EntityType.ZOMBIE,
                        EntityType.PILLAGER,
                        EntityType.VINDICATOR,
                        EntityType.ILLUSIONER
                );
    }

    public static void addBlockTags(RegistrateTagsProvider<Block> tagsProvider) {
        tagsProvider.getOrCreateBuilder(TransfigurationBlockTags.OUTPUTS_DISSOLUTION)
                .add(
                        Blocks.SAND,
                        Blocks.GRAVEL,
                        Blocks.AIR
                );

        tagsProvider.getOrCreateBuilder(TransfigurationBlockTags.INPUTS_MUTANDI)
                .addTag(BlockTags.SAPLINGS)
                .addTag(BlockTags.FLOWERS)
                .addTag(BlockTags.CROPS);


        tagsProvider.getOrCreateBuilder(TransfigurationBlockTags.OUTPUTS_MUTANDI)
                .addTag(BlockTags.SAPLINGS)
                .addTag(BlockTags.FLOWERS)
                .addTag(BlockTags.CROPS);

        tagsProvider.getOrCreateBuilder(TransfigurationBlockTags.OUTPUTS_FUNGAL_MUSHROOM)
                .add(
                        Blocks.BROWN_MUSHROOM,
                        Blocks.RED_MUSHROOM
                );

        tagsProvider.getOrCreateBuilder(TransfigurationBlockTags.OUTPUTS_FUNGI_MUSHROOM_LEAVES)
                .add(
                        Blocks.BROWN_MUSHROOM_BLOCK,
                        Blocks.RED_MUSHROOM_BLOCK
                );

        tagsProvider.getOrCreateBuilder(TransfigurationBlockTags.INPUTS_NETHERI_MUSHROOM)
                .add(
                        Blocks.BROWN_MUSHROOM,
                        Blocks.RED_MUSHROOM
                );

        tagsProvider.getOrCreateBuilder(TransfigurationBlockTags.OUTPUTS_NETHERI_MUSHROOM)
                .add(
                        Blocks.CRIMSON_FUNGUS,
                        Blocks.WARPED_FUNGUS
                );

        tagsProvider.getOrCreateBuilder(TransfigurationBlockTags.OUTPUTS_NETHERI_GRASS)
                .add(
                        Blocks.WARPED_NYLIUM,
                        Blocks.CRIMSON_NYLIUM
                );
    }

    public static void addItemTags(RegistrateTagsProvider<Item> tagsProvider) {
        tagsProvider.getOrCreateBuilder(TransfigurationItemTags.CORALS)
                .add(
                        Items.TUBE_CORAL,
                        Items.BRAIN_CORAL,
                        Items.BUBBLE_CORAL,
                        Items.FIRE_CORAL,
                        Items.HORN_CORAL,
                        Items.TUBE_CORAL_FAN,
                        Items.BRAIN_CORAL_FAN,
                        Items.BUBBLE_CORAL_FAN,
                        Items.FIRE_CORAL_FAN,
                        Items.HORN_CORAL_FAN
                );

    }
}
