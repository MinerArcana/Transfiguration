package com.minerarcana.transfiguration.particles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.GL11;

@SuppressWarnings("deprecation")
public class TransfiguringParticleRenderTypes {
    static final ParticleRenderType EMBER_RENDER = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder buffer, TextureManager textureManager) {
            RenderSystem.disableAlphaTest();

            RenderSystem.enableBlend();
            RenderSystem.alphaFunc(516, 0.3f);
            RenderSystem.enableCull();
            textureManager.bind(TextureAtlas.LOCATION_PARTICLES);
            RenderSystem.depthMask(false);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE.value);

            buffer.begin(GL11.GL_QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end(Tesselator tessellator) {
            tessellator.end();
            RenderSystem.enableDepthTest();
            RenderSystem.enableAlphaTest();

            RenderSystem.depthMask(true);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE.value);
            RenderSystem.disableCull();

            RenderSystem.alphaFunc(516, 0.1F);
        }

        @Override
        public String toString() {
            return "transfiguration:em_rend";
        }
    };

    static final ParticleRenderType EMBER_RENDER_NO_MASK = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder buffer, TextureManager textureManager) {
            RenderSystem.disableAlphaTest();
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.enableFog();
            RenderSystem.alphaFunc(516, 0.3f);
            textureManager.bind(TextureAtlas.LOCATION_PARTICLES);
            RenderSystem.depthMask(false);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE.value);
            RenderSystem.disableCull();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end(Tesselator tessellator) {
            tessellator.end();
            RenderSystem.enableDepthTest();
            RenderSystem.enableAlphaTest();

            RenderSystem.depthMask(true);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE.value);

            RenderSystem.alphaFunc(516, 0.1F);
        }

        @Override
        public String toString() {
            return "transfiguration:em_rend_no_mask";
        }
    };
}
