
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.TyphoeusModel;
import net.apocalypse.careunite.entity.TyphoeusEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class TyphoeusRenderer extends GeoEntityRenderer<TyphoeusEntity> {
	public TyphoeusRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TyphoeusModel());
		this.addRenderLayer(new Layer(this));
		this.shadowRadius = 0.5f;
	}

	@Override
	public RenderType getRenderType(TyphoeusEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, TyphoeusEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(TyphoeusEntity entityLivingBaseIn) {
		return 0.0F;
	}

	public static class Layer extends GeoRenderLayer<TyphoeusEntity> {
		private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/archer_glowmask.png");
	
		public Layer(GeoRenderer<TyphoeusEntity> entityRenderer) {
			super(entityRenderer);
		}
	
		@Override
		public void render(PoseStack poseStack, TyphoeusEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
			RenderType glowRenderType = RenderType.eyes(LAYER);
			float t = animatable.tickInSkill() + partialTick;
			float lit = 0;
			if (15 <= t && t <= 25){
				lit = 0.01f*(t-15)*(t-15);
			} else if (25 < t && t <= 29){
				lit = 1;
			} else if (29 < t && t <= 31){
				lit = 1 - 0.25f * (t-29)*(t-29);
			}
			if (lit > 0){
				getRenderer().reRender(getDefaultBakedModel(animatable), 
					poseStack, bufferSource, animatable, glowRenderType,
					bufferSource.getBuffer(glowRenderType), partialTick, packedLight, 
					OverlayTexture.NO_OVERLAY, lit, lit, lit, 1);
			}
		}
}

}
