package com.minerarcana.transfiguration.particles;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.util.Vectors;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nonnull;

public class TransfiguringParticle extends SpriteTexturedParticle {
    @SuppressWarnings("FieldCanBeLocal")
    private final TransfigurationType transfigurationType;
    private final Vector3d endPosition;
    private int delay;

    public TransfiguringParticle(ClientWorld world, double x, double y, double z, TransfiguringParticleData particleData) {
        super(world, x, y, z);

        this.transfigurationType = particleData.getTransfigurationType();
        int color = transfigurationType.getPrimaryColor();
        this.setColor(
                ((color >> 16) & 0xff) / 256F,
                ((color >> 8) & 0xff) / 256F,
                ((color) & 0xff) / 256F
        );
        this.maxAge = particleData.getMaxAge();
        this.delay = particleData.getDelay();
        this.endPosition = particleData.getEndPosition();
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else if (this.delay > 0) {
            this.delay--;
        } else {
            double motionMag = MathHelper.clamp(Math.sqrt(this.getMotionMag()) + 0.1, -0.3, 0.3);
            Vector3d direction = Vectors.getVector(
                    new Vector3d(
                            this.posX,
                            this.posY,
                            this.posZ
                    ),
                    endPosition
            ).mul(motionMag, motionMag, motionMag);
            this.motionX = direction.x;
            this.motionY = 0;
            this.motionZ = direction.z;
            this.move(motionX, motionY, motionZ);
        }
    }

    public double getMotionMag() {
        return motionX * motionX + motionY * motionY + motionZ * motionZ;
    }

    @Override
    @Nonnull
    public IParticleRenderType getRenderType() {
        return TransfiguringParticleRenderTypes.EMBER_RENDER;
    }
}
