package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.block.CatalystSubstrateBlock;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;

public class TransfigurationBlocks {

    public static final BlockEntry<CatalystSubstrateBlock> CATALYST_SUBSTRATE = Transfiguration.getRegistrate()
            .object("catalyst_substrate")
            .block(CatalystSubstrateBlock::new)
            .properties(AbstractBlock.Properties::notSolid)
            .blockstate((context, provider) -> provider.simpleBlock(context.get(), provider.models()
                    .getExistingFile(provider.modLoc("block/catalyst_substrate"))
            ))
            .addLayer(() -> RenderType::getTranslucent)
            .item()
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapelessRecipe(context.get())
                    .addIngredient(Tags.Items.GEMS_DIAMOND)
                    .addIngredient(Tags.Items.GEMS_LAPIS)
                    .addIngredient(Items.GHAST_TEAR)
                    .addCriterion("has_item", RegistrateRecipeProvider.hasItem(Items.GHAST_TEAR))
                    .build(provider)
            )
            .model((context, provider) -> provider.blockItem(context))
            .build()
            .register();

    public static final BlockEntry<CatalystSubstrateBlock> CATALYST_SUBSTRATE_STORAGE = Transfiguration.getRegistrate()
            .object("catalyst_substrate_block")
            .block(CatalystSubstrateBlock::new)
            .properties(AbstractBlock.Properties::notSolid)
            .blockstate((context, provider) -> provider.simpleBlock(context.get(), provider.models()
                    .getExistingFile(provider.modLoc("block/catalyst_substrate_block"))
            ))
            .tag(BlockTags.BEACON_BASE_BLOCKS)
            .addLayer(() -> RenderType::getTranslucent)
            .item()
            .recipe((context, provider) -> provider.square(DataIngredient.items(CATALYST_SUBSTRATE), context, false))
            .model((context, provider) -> provider.blockItem(context))
            .build()
            .register();

    public static void setup() {

    }
}
