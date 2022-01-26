package com.minerarcana.transfiguration.content;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class TransfigurationAdditionalData {

    public static void addLang(RegistrateLangProvider langProvider) {
        langProvider.add("upgrade.transfiguration.transfiguring.advective", "Transfiguring");
    }

    public static void addEntityTypeTags(RegistrateTagsProvider<EntityType<?>> tagsProvider) {
        tagsProvider.tag(TransfigurationEntityTypeTags.OUTPUTS_ACCURSED)
                .add(
                        EntityType.WITCH,
                        EntityType.ZOMBIE,
                        EntityType.PILLAGER,
                        EntityType.VINDICATOR,
                        EntityType.ILLUSIONER
                );
    }

    public static void addBlockTags(RegistrateTagsProvider<Block> tagsProvider) {
        tagsProvider.tag(TransfigurationBlockTags.OUTPUTS_DISSOLUTION)
                .add(
                        Blocks.SAND,
                        Blocks.GRAVEL,
                        Blocks.AIR
                );

        tagsProvider.tag(TransfigurationBlockTags.INPUTS_MUTANDI)
                .addTag(BlockTags.SAPLINGS)
                .addTag(BlockTags.FLOWERS)
                .addTag(BlockTags.CROPS);


        tagsProvider.tag(TransfigurationBlockTags.OUTPUTS_MUTANDI)
                .addTag(BlockTags.SAPLINGS)
                .addTag(BlockTags.FLOWERS)
                .addTag(BlockTags.CROPS);

        tagsProvider.tag(TransfigurationBlockTags.OUTPUTS_FUNGAL_MUSHROOM)
                .add(
                        Blocks.BROWN_MUSHROOM,
                        Blocks.RED_MUSHROOM
                );

        tagsProvider.tag(TransfigurationBlockTags.OUTPUTS_FUNGI_MUSHROOM_LEAVES)
                .add(
                        Blocks.BROWN_MUSHROOM_BLOCK,
                        Blocks.RED_MUSHROOM_BLOCK
                );

        tagsProvider.tag(TransfigurationBlockTags.INPUTS_NETHERI_MUSHROOM)
                .add(
                        Blocks.BROWN_MUSHROOM,
                        Blocks.RED_MUSHROOM
                );

        tagsProvider.tag(TransfigurationBlockTags.OUTPUTS_NETHERI_MUSHROOM)
                .add(
                        Blocks.CRIMSON_FUNGUS,
                        Blocks.WARPED_FUNGUS
                );

        tagsProvider.tag(TransfigurationBlockTags.OUTPUTS_NETHERI_GRASS)
                .add(
                        Blocks.WARPED_NYLIUM,
                        Blocks.CRIMSON_NYLIUM
                );
    }

    public static void addItemTags(RegistrateTagsProvider<Item> tagsProvider) {
        tagsProvider.tag(TransfigurationItemTags.CORALS)
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
