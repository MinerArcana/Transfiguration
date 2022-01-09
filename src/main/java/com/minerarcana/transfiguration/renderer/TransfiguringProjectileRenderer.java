package com.minerarcana.transfiguration.renderer;

import com.minerarcana.transfiguration.entity.TransfiguringProjectileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;

public class TransfiguringProjectileRenderer extends SpriteRenderer<TransfiguringProjectileEntity> {
    public TransfiguringProjectileRenderer(EntityRendererManager renderManager) {
        super(renderManager, Minecraft.getInstance().getItemRenderer());
    }
}
