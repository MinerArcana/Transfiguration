package com.minerarcana.transfiguration.particles;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationParticles;
import com.minerarcana.transfiguration.util.Buffers;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TransfiguringParticleData implements IParticleData {
    private final TransfigurationType type;
    private final Vector3d endPosition;
    private final int delay;
    private final int maxAge;

    public TransfiguringParticleData(TransfigurationType type, Vector3d endPosition, int delay, int maxAge) {
        this.type = type;
        this.endPosition = endPosition;
        this.delay = delay;
        this.maxAge = maxAge;
    }

    public TransfigurationType getTransfigurationType() {
        return this.type;
    }

    public int getDelay() {
        return delay;
    }

    public Vector3d getEndPosition() {
        return endPosition;
    }

    public int getMaxAge() {
        return maxAge;
    }

    @Override
    @Nonnull
    public ParticleType<?> getType() {
        return TransfigurationParticles.TRANSFIGURING.get();
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer) {
        buffer.writeRegistryId(type);
        Buffers.writeVector3d(endPosition, buffer);
        buffer.writeInt(delay);
        buffer.writeInt(maxAge);
    }

    @Override
    @Nonnull
    public String getParameters() {
        return Objects.requireNonNull(type.getRegistryName()) + " " + endPosition + " " + delay + " " + maxAge;
    }

    public static TransfiguringParticleData create(TransfigurationType transfigurationType, Vector3d direction,
                                                   int delay, int maxAge) {
        return new TransfiguringParticleData(Objects.requireNonNull(transfigurationType), direction, delay, maxAge);
    }


}
