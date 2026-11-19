
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.PromotedSquidModel;
import net.apocalypse.careunite.entity.PromotedSquidEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class PromotedSquidRenderer extends GeoEntityRenderer<PromotedSquidEntity> {
	public PromotedSquidRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new PromotedSquidModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new SquidLayer(this));
	}

	@Override
	public RenderType getRenderType(PromotedSquidEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, PromotedSquidEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public static class SquidLayer extends GeoRenderLayer<PromotedSquidEntity> {
		private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/squid_lit.png");
	
		public SquidLayer(GeoRenderer<PromotedSquidEntity> entityRenderer) {
			super(entityRenderer);
		}
	
		@Override
		public void render(PoseStack poseStack, PromotedSquidEntity animatable, BakedGeoModel bakedModel, RenderType renderType, 
			MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
			RenderType glowRenderType = RenderType.eyes(LAYER);
			getRenderer().reRender(getDefaultBakedModel(animatable), 
				poseStack, bufferSource, animatable, glowRenderType, 
				bufferSource.getBuffer(glowRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 0.75f, 0.75f, 0.75f, 1);
		}
	}
}
