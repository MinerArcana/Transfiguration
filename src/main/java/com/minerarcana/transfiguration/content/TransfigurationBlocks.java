package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.block.CatalystSubstrateBlock;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.Tags;

public class TransfigurationBlocks {

    public static final BlockEntry<CatalystSubstrateBlock> CATALYST_SUBSTRATE = Transfiguration.getRegistrate()
            .object("catalyst_substrate")
            .block(CatalystSubstrateBlock::new)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .blockstate((context, provider) -> provider.simpleBlock(context.get(), provider.models()
                    .getExistingFile(provider.modLoc("block/catalyst_substrate"))
            ))
            .addLayer(() -> RenderType::translucent)
            .item()
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapeless(context.get())
                    .requires(Tags.Items.GEMS_DIAMOND)
                    .requires(Tags.Items.GEMS_LAPIS)
                    .requires(Items.GHAST_TEAR)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(Items.GHAST_TEAR))
                    .save(provider)
            )
            .model((context, provider) -> provider.blockItem(context))
            .build()
            .register();

    public static final BlockEntry<CatalystSubstrateBlock> CATALYST_SUBSTRATE_STORAGE = Transfiguration.getRegistrate()
            .object("catalyst_substrate_block")
            .block(CatalystSubstrateBlock::new)
            .properties(Block.Properties::noOcclusion)
            .blockstate((context, provider) -> provider.simpleBlock(context.get(), provider.models()
                    .getExistingFile(provider.modLoc("block/catalyst_substrate_block"))
            ))
            .tag(BlockTags.BEACON_BASE_BLOCKS)
            .addLayer(() -> RenderType::translucent)
            .item()
            .recipe((context, provider) -> provider.square(DataIngredient.items(CATALYST_SUBSTRATE), context, false))
            .model((context, provider) -> provider.blockItem(context))
            .build()
            .register();

    public static void setup() {

    }
}
