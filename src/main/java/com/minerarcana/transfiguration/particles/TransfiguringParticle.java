package com.minerarcana.transfiguration.particles;

import com.minerarcana.transfiguration.api.TransfigurationType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class TransfiguringParticle extends TextureSheetParticle {
    @SuppressWarnings("FieldCanBeLocal")
    private final TransfigurationType transfigurationType;
    private final Vec3 endPosition;
    private int delay;
    private int colorOffset;

    public TransfiguringParticle(ClientLevel world, double x, double y, double z,
                                 TransfiguringParticleData particleData) {
        super(world, x, y, z);

        this.transfigurationType = particleData.getTransfigurationType();
        this.lifetime = particleData.getMaxAge();
        this.colorOffset = particleData.getColorOffset();
        this.delay = particleData.getDelay();
        this.endPosition = particleData.getEndPosition();
        this.handleCurrentColor();
    }

    public void tick() {
        handleCurrentColor();
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else if (this.delay > 0) {
            this.delay--;
        } else {
            Vec3 currentPos = new Vec3(this.x, this.y, this.z);
            Vec3 direction = endPosition.subtract(currentPos).normalize();

            this.xd = direction.x / 8;
            this.yd = direction.y / 8;
            this.zd = direction.z / 8;
            this.move(xd, yd, zd);
            if (Math.abs(
                    (this.xo - this.x) + (this.yo - this.y) + (this.zo - this.z)) < 0.02) {
                this.remove();
            }

        }
    }

    public void handleCurrentColor() {
//	double currentOffset = ((Math.PI * 2) / 32) * colorOffset++;
//	if (colorOffset > 32) {
//	    colorOffset = 0;
//	}
        int primaryColor = transfigurationType.getPrimaryColor();
        int secondaryColor = transfigurationType.getSecondaryColor();

//	int difference = primaryColor - secondaryColor;
//	int color = (int) Math.round(primaryColor - (difference / (float) 2 * currentOffset));
//	this.setColor(((color >> 16) & 0xff) / 256F, ((color >> 8) & 0xff) / 256F, ((color) & 0xff) / 256F);

        float time = (float) this.age / ((float) this.lifetime / 3);
        if (this.age >= lifetime / 3)
            time = 1f;

        lerpColor(primaryColor, secondaryColor, time);
    }

    public void lerpColor(int primaryColor, int secondaryColor, float age) {
        int r1 = (primaryColor >> 16) & 0xFF;
        int g1 = (primaryColor >> 8) & 0xFF;
        int b1 = primaryColor & 0xFF;

        int r2 = (secondaryColor >> 16) & 0xFF;
        int g2 = (secondaryColor >> 8) & 0xFF;
        int b2 = secondaryColor & 0xFF;

        int rO = (int) (r1 + age * (r2 - r1));
        int gO = (int) (g1 + age * (g2 - g1));
        int bO = (int) (b1 + age * (b2 - b1));

        this.setColor(rO / 256f, gO / 256f, bO / 256f);
        // return v0 + t * (v1 - v0);
    }

    @Override
    @Nonnull
    public ParticleRenderType getRenderType() {
        return TransfiguringParticleRenderTypes.EMBER_RENDER;
    }
}
