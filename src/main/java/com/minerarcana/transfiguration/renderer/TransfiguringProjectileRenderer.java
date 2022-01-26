package com.minerarcana.transfiguration.renderer;

import com.minerarcana.transfiguration.entity.TransfiguringProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class TransfiguringProjectileRenderer extends ThrownItemRenderer<TransfiguringProjectileEntity> {
    public TransfiguringProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
}
