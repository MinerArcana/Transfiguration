package com.minerarcana.transfiguration.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class TransfiguringParticleFactory implements ParticleProvider<TransfiguringParticleData> {
    private final SpriteSet sprite;

    public TransfiguringParticleFactory(SpriteSet sprite) {
        this.sprite = sprite;
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public Particle createParticle(TransfiguringParticleData type, ClientLevel world, double x, double y, double z,
                                 double xSpeed, double ySpeed, double zSpeed) {
        TransfiguringParticle particle = new TransfiguringParticle(world, x, y, z, type);
        particle.pickSprite(sprite);
        return particle;
    }
}
