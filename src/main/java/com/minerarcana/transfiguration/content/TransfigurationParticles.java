package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.particles.TransfiguringParticleData;
import com.minerarcana.transfiguration.particles.TransfiguringParticleType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;

public class TransfigurationParticles {

    public static final RegistryEntry<ParticleType<TransfiguringParticleData>> TRANSFIGURING =
            Transfiguration.getRegistrate()
                    .object("transfiguring")
                    .simple(Registry.PARTICLE_TYPE_REGISTRY, TransfiguringParticleType::new);

    public static void setup() {

    }
}
