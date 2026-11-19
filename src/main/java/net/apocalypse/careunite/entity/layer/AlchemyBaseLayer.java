package net.apocalypse.careunite.entity.layer;

import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.AlchemyBaseEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class AlchemyBaseLayer extends GeoRenderLayer<AlchemyBaseEntity> {
	private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/alchemy_base_lit.png");
	private static final ResourceLocation LAYER1 = new ResourceLocation("caerula_arbor_reunite", "textures/entities/alchemy_base_breath.png");

	public AlchemyBaseLayer(GeoRenderer<AlchemyBaseEntity> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(PoseStack poseStack, AlchemyBaseEntity animatable, BakedGeoModel bakedModel, 
		RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
		float partialTick, int packedLight, int packedOverlay) {
		RenderType glowRenderType = RenderType.eyes(LAYER);
		getRenderer().reRender(
			getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, glowRenderType, 
			bufferSource.getBuffer(glowRenderType), partialTick, packedLight, 
			OverlayTexture.NO_OVERLAY, 0.5f, 0.5f, 0.5f, 1);
		RenderType glowRenderType1 = RenderType.eyes(LAYER1);
		float t = animatable.tickCount + partialTick;
		t %= 40;
		float lit = t < 20 ? 0.1f + t * 0.05f * 0.9f: 1.9f - t * 0.05f * 0.9f;
		getRenderer().reRender(
			getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, glowRenderType, 
			bufferSource.getBuffer(glowRenderType1), partialTick, packedLight, 
			OverlayTexture.NO_OVERLAY, lit, lit, lit, 1);
	}
}
