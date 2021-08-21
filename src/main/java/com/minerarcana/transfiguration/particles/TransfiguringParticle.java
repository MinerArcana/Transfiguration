package com.minerarcana.transfiguration.particles;

import com.minerarcana.transfiguration.api.TransfigurationType;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;

import javax.annotation.Nonnull;

public class TransfiguringParticle extends SpriteTexturedParticle {
    private final TransfigurationType transfigurationType;

    public TransfiguringParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, TransfigurationType transfigurationType) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);

        this.transfigurationType = transfigurationType;
        int color = transfigurationType.getPrimaryColor();
        this.setColor(
                ((color >> 16) & 0xff) / 256F,
                ((color >> 8) & 0xff) / 256F,
                ((color) & 0xff) / 256F
        );
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        }
    }

    @Override
    @Nonnull
    public IParticleRenderType getRenderType() {
        return TransfiguringParticleRenderTypes.EMBER_RENDER;
    }
}
