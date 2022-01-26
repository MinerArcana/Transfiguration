package com.minerarcana.transfiguration.particles;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.util.Buffers;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
public class TransfiguringParticleDeserializer implements ParticleOptions.Deserializer<TransfiguringParticleData> {
    public static final DynamicCommandExceptionType BAD_ID = new DynamicCommandExceptionType(
            (value) -> new TranslatableComponent("argument.transfiguration.type.id.invalid", value)
    );

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public TransfiguringParticleData fromCommand(ParticleType<TransfiguringParticleData> particleType, StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        int i = reader.getCursor();
        ResourceLocation name = ResourceLocation.read(reader);
        TransfigurationType transfigurationType = Transfiguration.transfigurationTypes.getValue(name);
        reader.expect(' ');
        Vec3 direction = new Vec3(
                reader.readDouble(),
                reader.readDouble(),
                reader.readDouble()
        );
        reader.expect(' ');
        int delay = reader.readInt();
        reader.expect(' ');
        int maxAge = reader.readInt();
        reader.expect(' ');
        int colorOffset = reader.readInt();
        if (transfigurationType != null) {
            return new TransfiguringParticleData(transfigurationType, direction, delay, maxAge, colorOffset);
        } else {
            reader.setCursor(i);
            throw BAD_ID.createWithContext(reader, name);
        }
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public TransfiguringParticleData fromNetwork(ParticleType<TransfiguringParticleData> particleType, FriendlyByteBuf buffer) {
        return new TransfiguringParticleData(
                buffer.readRegistryId(),
                Buffers.readVector3d(buffer),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt()
        );
    }
}
