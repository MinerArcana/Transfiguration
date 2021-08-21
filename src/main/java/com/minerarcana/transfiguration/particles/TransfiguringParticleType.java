package com.minerarcana.transfiguration.particles;

import com.minerarcana.transfiguration.Transfiguration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TransfiguringParticleType extends ParticleType<TransfiguringParticleData> {
    public static final Codec<TransfiguringParticleData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("transfigurationType")
                    .xmap(
                            string -> Transfiguration.transfigurationTypes.getValue(new ResourceLocation(string)),
                            transfigurationType -> Objects.requireNonNull(transfigurationType.getRegistryName()).toString()
                    )
                    .forGetter(TransfiguringParticleData::getTransfigurationType)
    ).apply(instance, TransfiguringParticleData::create));

    public TransfiguringParticleType() {
        this(false);
    }

    public TransfiguringParticleType(boolean alwaysShow) {
        super(alwaysShow, new TransfiguringParticleDeserializer());
    }

    @Override
    @Nonnull
    public Codec<TransfiguringParticleData> func_230522_e_() {
        return CODEC;
    }
}
