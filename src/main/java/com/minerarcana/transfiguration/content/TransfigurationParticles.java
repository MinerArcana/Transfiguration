package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.particles.TransfiguringParticleData;
import com.minerarcana.transfiguration.particles.TransfiguringParticleType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.particles.ParticleType;

public class TransfigurationParticles {

    public static final RegistryEntry<ParticleType<TransfiguringParticleData>> TRANSFIGURING =
            Transfiguration.getRegistrate()
                    .object("transfiguring")
                    .simple(ParticleType.class, TransfiguringParticleType::new);

    public static void setup() {

    }
}
