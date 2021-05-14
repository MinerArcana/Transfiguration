package com.minerarcana.transfiguration.compat.jei.ingredients;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.model.data.EmptyModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;

public class BlockIngredientRenderer implements IIngredientRenderer<BlockState> {
    @Override
    @ParametersAreNonnullByDefault
    public void render(MatrixStack matrixStack, int i, int i1, @Nullable BlockState blockState) {
        if (blockState != null) {
            Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(
                    blockState,
                    matrixStack,
                    Minecraft.getInstance().getRenderTypeBuffers()
                            .getBufferSource(),
                    0,
                    OverlayTexture.NO_OVERLAY,
                    EmptyModelData.INSTANCE
            );
        }
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public List<ITextComponent> getTooltip(BlockState blockState, ITooltipFlag iTooltipFlag) {
        return Collections.singletonList(
                new TranslationTextComponent(blockState.getBlock().getTranslationKey())
        );
    }
}
