package com.minerarcana.transfiguration.renderer;

import com.minerarcana.transfiguration.entity.TransfiguringEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class TransfiguringEntityRenderer<T extends TransfiguringEntity<?, ?>> extends EntityRenderer<T> {
    public TransfiguringEntityRenderer(EntityRendererManager rendererManager) {
        super(rendererManager);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer,
                       int packedLight) {

    }

    @Override
    @Nonnull
    public ResourceLocation getEntityTexture(@Nonnull T entity) {
        return PlayerContainer.LOCATION_BLOCKS_TEXTURE;
    }
}
