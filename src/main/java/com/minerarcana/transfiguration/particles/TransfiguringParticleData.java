package com.minerarcana.transfiguration.particles;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.content.TransfigurationParticles;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import com.minerarcana.transfiguration.util.Buffers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TransfiguringParticleData implements ParticleOptions {
    private final TransfigurationType type;
    private final Vec3 endPosition;
    private final int delay;
    private final int maxAge;
    private final int colorOffset;

    public TransfiguringParticleData(TransfigurationType type, Vec3 endPosition, int delay, int maxAge, int colorOffset) {
        this.type = type;
        this.endPosition = endPosition;
        this.delay = delay;
        this.maxAge = maxAge;
        this.colorOffset = colorOffset;
    }

    public TransfigurationType getTransfigurationType() {
        return this.type;
    }

    public int getDelay() {
        return delay;
    }

    public Vec3 getEndPosition() {
        return endPosition;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getColorOffset() {
        return colorOffset;
    }

    @Override
    @Nonnull
    public ParticleType<?> getType() {
        return TransfigurationParticles.TRANSFIGURING.get();
    }

    @Override
    public void writeToNetwork(@Nonnull FriendlyByteBuf buffer) {
        buffer.writeRegistryId(TransfigurationTypes.getRegistry(), type);
        Buffers.writeVector3d(endPosition, buffer);
        buffer.writeInt(delay);
        buffer.writeInt(maxAge);
        buffer.writeInt(colorOffset);
    }

    @Override
    @Nonnull
    public String writeToString() {
        return TransfigurationTypes.getKey(this.type) + " " + endPosition + " " + delay + " " + maxAge + " " + colorOffset;
    }

    public static TransfiguringParticleData create(TransfigurationType transfigurationType, Vec3 direction,
                                                   int delay, int maxAge, int colorOffset) {
        return new TransfiguringParticleData(Objects.requireNonNull(transfigurationType), direction, delay, maxAge, colorOffset);
    }


}
