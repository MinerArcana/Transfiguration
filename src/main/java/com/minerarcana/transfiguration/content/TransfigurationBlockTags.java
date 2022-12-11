package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class TransfigurationBlockTags {
    public static TagKey<Block> OUTPUTS_DISSOLUTION = createTagKey("outputs/dissolution");
    public static TagKey<Block> INPUTS_MUTANDI = createTagKey("inputs/mutandi");
    public static TagKey<Block> OUTPUTS_MUTANDI = createTagKey("outputs/mutandi");
    public static TagKey<Block> OUTPUTS_FUNGAL_MUSHROOM = createTagKey("outputs/fungal/mushroom");
    public static TagKey<Block> OUTPUTS_NETHERI_GRASS = createTagKey("outputs/netheri/grass");
    public static TagKey<Block> INPUTS_NETHERI_MUSHROOM = createTagKey("inputs/netheri/mushroom");
    public static TagKey<Block> OUTPUTS_NETHERI_MUSHROOM = createTagKey("outputs/netheri/mushroom");
    public static TagKey<Block> OUTPUTS_FUNGI_MUSHROOM_LEAVES = createTagKey("outputs/fungal/mushroom_leaves");

    public static TagKey<Block> createTagKey(String path) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.tags())
                .createTagKey(Transfiguration.rl(path));
    }
}
