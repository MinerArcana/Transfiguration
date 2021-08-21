package com.minerarcana.transfiguration.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class TransfiguringParticleFactory implements IParticleFactory<TransfiguringParticleData> {
    private final IAnimatedSprite sprite;

    public TransfiguringParticleFactory(IAnimatedSprite sprite) {
        this.sprite = sprite;
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public Particle makeParticle(TransfiguringParticleData type, ClientWorld world, double x, double y, double z,
                                 double xSpeed, double ySpeed, double zSpeed) {
        TransfiguringParticle particle = new TransfiguringParticle(
                world,
                x, y, z,
                xSpeed, ySpeed, zSpeed,
                type.getTransfigurationType()
        );
        particle.selectSpriteRandomly(sprite);
        return particle;
    }
}
