package com.minerarcana.transfiguration.particles;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.util.Buffers;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
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
        reader.expect(' ');
        Vector3d direction = new Vector3d(
                reader.readDouble(),
                reader.readDouble(),
                reader.readDouble()
        );
        reader.expect(' ');
        int delay = reader.readInt();
        reader.expect(' ');
        int maxAge = reader.readInt();
        if (transfigurationType != null) {
            return new TransfiguringParticleData(transfigurationType, direction, delay, maxAge);
        } else {
            reader.setCursor(i);
            throw BAD_ID.createWithContext(reader, name);
        }
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public TransfiguringParticleData read(ParticleType<TransfiguringParticleData> particleType, PacketBuffer buffer) {
        return new TransfiguringParticleData(
                buffer.readRegistryId(),
                Buffers.readVector3d(buffer),
                buffer.readInt(),
                buffer.readInt()
        );
    }
}
