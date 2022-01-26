package com.minerarcana.transfiguration.renderer;

import com.minerarcana.transfiguration.entity.TransfiguringEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class TransfiguringEntityRenderer<T extends TransfiguringEntity<?, ?>> extends EntityRenderer<T> {
    public TransfiguringEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer,
                       int packedLight) {

    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull T entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
