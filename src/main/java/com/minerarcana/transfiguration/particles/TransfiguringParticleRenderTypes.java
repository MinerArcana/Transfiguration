package com.minerarcana.transfiguration.particles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class TransfiguringParticleRenderTypes {
    private static ShaderInstance BRIGHT_SOLID;

    public static void setBrightSolid(ShaderInstance brightSolid) {
        BRIGHT_SOLID = brightSolid;
    }

    public static ShaderInstance getBrightSolid() {
        return BRIGHT_SOLID;
    }

    public static final ParticleRenderType EMBER_RENDER = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder buffer, @Nonnull TextureManager textureManager) {
            Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
            RenderSystem.enableBlend();

            RenderSystem.enableCull();

            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            RenderSystem.enableDepthTest();

            RenderSystem.depthMask(false);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE.value);
            RenderSystem.setShader(TransfiguringParticleRenderTypes::getBrightSolid);

            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end(Tesselator tessellator) {
            tessellator.end();
        }

        @Override
        public String toString() {
            return "transfiguration:em_rend";
        }
    };
}
