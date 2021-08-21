package com.minerarcana.transfiguration.particles;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationParticles;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TransfiguringParticleData implements IParticleData {
    private final TransfigurationType type;

    public TransfiguringParticleData(TransfigurationType type) {
        this.type = type;
    }

    public TransfigurationType getTransfigurationType() {
        return this.type;
    }

    @Override
    @Nonnull
    public ParticleType<?> getType() {
        return TransfigurationParticles.TRANSFIGURING.get();
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer) {
        buffer.writeRegistryId(type);
    }

    @Override
    @Nonnull
    public String getParameters() {
        return Objects.requireNonNull(type.getRegistryName()).toString();
    }

    public static TransfiguringParticleData create(TransfigurationType transfigurationType) {
        return new TransfiguringParticleData(Objects.requireNonNull(transfigurationType));
    }
}
