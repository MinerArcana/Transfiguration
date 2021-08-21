package com.minerarcana.transfiguration.particles;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
public class TransfiguringParticleDeserializer implements IParticleData.IDeserializer<TransfiguringParticleData> {
    public static final DynamicCommandExceptionType BAD_ID = new DynamicCommandExceptionType(
            (value) -> new TranslationTextComponent("argument.transfiguration.type.id.invalid", value)
    );

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public TransfiguringParticleData deserialize(ParticleType<TransfiguringParticleData> particleType, StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        int i = reader.getCursor();
        ResourceLocation name = ResourceLocation.read(reader);
        TransfigurationType transfigurationType = Transfiguration.transfigurationTypes.getValue(name);
        if (transfigurationType != null) {
            return new TransfiguringParticleData(transfigurationType);
        } else {
            reader.setCursor(i);
            throw BAD_ID.createWithContext(reader, name);
        }
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public TransfiguringParticleData read(ParticleType<TransfiguringParticleData> particleType, PacketBuffer buffer) {
        return new TransfiguringParticleData(buffer.readRegistryId());
    }
}
