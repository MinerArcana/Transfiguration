package com.minerarcana.transfiguration.event;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.content.TransfigurationParticles;
import com.minerarcana.transfiguration.particles.TransfiguringParticleFactory;
import com.minerarcana.transfiguration.particles.TransfiguringParticleRenderTypes;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.io.IOException;

@EventBusSubscriber(modid = Transfiguration.ID, value = Dist.CLIENT, bus = Bus.MOD)
public class ForgeClientEventHandler {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.register(
                TransfigurationParticles.TRANSFIGURING.get(),
                TransfiguringParticleFactory::new
        );
    }

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
        // Adds a shader to the list, the callback runs when loading is complete.
        event.registerShader(
                new ShaderInstance(
                        event.getResourceManager(),
                        Transfiguration.rl("bright_solid"),
                        DefaultVertexFormat.PARTICLE
                ),
                TransfiguringParticleRenderTypes::setBrightSolid
        );
    }
}
