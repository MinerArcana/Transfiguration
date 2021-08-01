package com.minerarcana.transfiguration.content;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;

public class TransfigurationBlockTags {
    public static ITag.INamedTag<Block> OUTPUTS_DISSOLUTION = BlockTags.makeWrapperTag("transfiguration:outputs/dissolution");
    public static ITag.INamedTag<Block> INPUTS_MUTANDI = BlockTags.makeWrapperTag("transfiguration:inputs/mutandi");
    public static ITag.INamedTag<Block> OUTPUTS_MUTANDI = BlockTags.makeWrapperTag("transfiguration:outputs/mutandi");
}
