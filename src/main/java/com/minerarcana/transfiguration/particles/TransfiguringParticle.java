package com.minerarcana.transfiguration.particles;

import com.minerarcana.transfiguration.api.TransfigurationType;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nonnull;

public class TransfiguringParticle extends SpriteTexturedParticle {
    @SuppressWarnings("FieldCanBeLocal")
    private final TransfigurationType transfigurationType;
    private final Vector3d endPosition;
    private int delay;
    private int colorOffset;

    public TransfiguringParticle(ClientWorld world, double x, double y, double z, TransfiguringParticleData particleData) {
        super(world, x, y, z);

        this.transfigurationType = particleData.getTransfigurationType();
        this.maxAge = particleData.getMaxAge();
        this.colorOffset = particleData.getColorOffset();
        this.delay = particleData.getDelay();
        this.endPosition = particleData.getEndPosition();
        this.handleCurrentColor();
    }

    public void tick() {
        handleCurrentColor();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else if (this.delay > 0) {
            this.delay--;
        } else {
            Vector3d currentPos = new Vector3d(this.posX, this.posY, this.posZ);
            Vector3d direction = endPosition.subtract(currentPos).normalize();

            this.motionX = direction.x / 8;
            this.motionY = direction.y / 8;
            this.motionZ = direction.z / 8;
            this.move(motionX, motionY, motionZ);
            if (Math.abs((this.prevPosX - this.posX) + (this.prevPosY - this.posY) + (this.prevPosZ - this.posZ)) < 0.02) {
                this.setExpired();
            }

        }
    }

    public void handleCurrentColor() {
        double currentOffset = ((Math.PI * 2) / 32) * colorOffset++;
        if (colorOffset > 32) {
            colorOffset = 0;
        }
        int primaryColor = transfigurationType.getPrimaryColor();
        int secondaryColor = transfigurationType.getSecondaryColor();

        int difference = primaryColor - secondaryColor;
        int color = (int) Math.round(primaryColor - (difference / (float) 2 * currentOffset));
        this.setColor(
                ((color >> 16) & 0xff) / 256F,
                ((color >> 8) & 0xff) / 256F,
                ((color) & 0xff) / 256F
        );
    }

    @Override
    @Nonnull
    public IParticleRenderType getRenderType() {
        return TransfiguringParticleRenderTypes.EMBER_RENDER;
    }
}
