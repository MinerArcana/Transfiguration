package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.block.CatalystSubstrateBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.block.AbstractBlock;

public class TransfigurationBlocks {

    public static final BlockEntry<CatalystSubstrateBlock> CATALYST_SUBSTRATE = Transfiguration.getRegistrate()
            .object("catalyst_substrate")
            .block(CatalystSubstrateBlock::new)
            .properties(AbstractBlock.Properties::doesNotBlockMovement)
            .blockstate((context, provider) -> provider.simpleBlock(context.get(), provider.models()
                    .getExistingFile(provider.modLoc("block/catalyst_substrate"))
            ))
            .item()
            .build()
            .register();

    public static void setup() {

    }
}
