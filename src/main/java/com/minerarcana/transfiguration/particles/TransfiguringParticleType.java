package com.minerarcana.transfiguration.particles;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import com.minerarcana.transfiguration.util.Codecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;

import javax.annotation.Nonnull;

public class TransfiguringParticleType extends ParticleType<TransfiguringParticleData> {

    public static final Codec<TransfiguringParticleData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codecs.forRegistry("transfigurationType", TransfigurationTypes.getRegistry())
                    .forGetter(TransfiguringParticleData::getTransfigurationType),
            Codecs.VECTOR.fieldOf("direction")
                    .forGetter(TransfiguringParticleData::getEndPosition),
            Codec.INT.fieldOf("delay")
                    .forGetter(TransfiguringParticleData::getDelay),
            Codec.INT.fieldOf("maxAge")
                    .forGetter(TransfiguringParticleData::getMaxAge),
            Codec.INT.fieldOf("colorOffset")
                    .forGetter(TransfiguringParticleData::getColorOffset)
    ).apply(instance, TransfiguringParticleData::create));

    public TransfiguringParticleType() {
        this(false);
    }

    public TransfiguringParticleType(boolean alwaysShow) {
        super(alwaysShow, new TransfiguringParticleDeserializer());
    }

    @Override
    @Nonnull
    public Codec<TransfiguringParticleData> codec() {
        return CODEC;
    }
}
