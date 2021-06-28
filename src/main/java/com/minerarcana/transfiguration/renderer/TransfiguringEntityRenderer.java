package com.minerarcana.transfiguration.renderer;

import com.minerarcana.transfiguration.entity.TransfiguringEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.storage.MapData;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class TransfiguringEntityRenderer<T extends TransfiguringEntity<?, ?, ?>> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;

    public TransfiguringEntityRenderer(EntityRendererManager rendererManager) {
        this(rendererManager, Minecraft.getInstance().getItemRenderer());
    }

    public TransfiguringEntityRenderer(EntityRendererManager rendererManager, ItemRenderer renderer) {
        super(rendererManager);
        this.itemRenderer = renderer;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer,
                       int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
        matrixStack.push();

        ItemStack itemstack = entity.getItem();
        if (!itemstack.isEmpty()) {
            matrixStack.translate(0.0D, -0.475D, 0D);
            switch (entity.getPlacement()) {
                case DOWN:
                    matrixStack.rotate(new Quaternion(90, 0, 0, true));
                    matrixStack.translate(0.5, 0.5, -0.5);
                    break;
                default:
            }
            //matrixStack.scale(0.75F, 0.75F, 0.75F);
            this.itemRenderer.renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED, packedLight, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
        }

        matrixStack.pop();
    }

    @Override
    @Nonnull
    public ResourceLocation getEntityTexture(@Nonnull T entity) {
        return PlayerContainer.LOCATION_BLOCKS_TEXTURE;
    }
}
