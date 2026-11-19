package net.apocalypse.careunite.entity.layer;

import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.SaintValerioEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class SaintValerioLayer extends GeoRenderLayer<SaintValerioEntity> {
	private static final ResourceLocation[] LAYERS = new ResourceLocation[]{
		new ResourceLocation("caerula_arbor_reunite", "textures/entities/saint_warrerio_glow_0.png"),
		new ResourceLocation("caerula_arbor_reunite", "textures/entities/saint_warrerio_glow_1.png"),
		new ResourceLocation("caerula_arbor_reunite", "textures/entities/saint_warrerio_glow_2.png"),
		new ResourceLocation("caerula_arbor_reunite", "textures/entities/saint_warrerio_glow_3.png"),
		new ResourceLocation("caerula_arbor_reunite", "textures/entities/saint_warrerio_glow_4.png"),
		new ResourceLocation("caerula_arbor_reunite", "textures/entities/saint_warrerio_glow_5.png"),
	};

	public SaintValerioLayer(GeoRenderer<SaintValerioEntity> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(PoseStack poseStack, SaintValerioEntity animatable, BakedGeoModel bakedModel, RenderType renderType, 
		MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		float c = 0;
		float t = animatable.reviveTick() - partialTick;
		if (t > 0){
			if (t <= 100) c = (100 - t) * 0.01f * 0.7f;
		} else if (animatable.getPhase() > 0){
			c = 0.7f;
		}
		if (c > 0){
			int i = animatable.tickCount / 3 % 6;
			RenderType glowRenderType = RenderType.eyes(LAYERS[i]);
			getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, 
				glowRenderType, bufferSource.getBuffer(glowRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, c, c, c, 1);
		}
	}
}
