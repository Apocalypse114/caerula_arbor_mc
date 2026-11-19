
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.TuitionHandbowModel;
import net.apocalypse.careunite.entity.TuitionHandbowEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class TuitionHandbowRenderer extends GeoEntityRenderer<TuitionHandbowEntity> {
	public TuitionHandbowRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TuitionHandbowModel());
		this.shadowRadius = 0.5f;
		addRenderLayer(new Layer(this));
	}

	@Override
	public RenderType getRenderType(TuitionHandbowEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, TuitionHandbowEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public static class Layer extends GeoRenderLayer<TuitionHandbowEntity> {
		private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/tuition_handbow.png");
	
		public Layer(GeoRenderer<TuitionHandbowEntity> entityRenderer) {
			super(entityRenderer);
		}
	
		@Override
		public void render(PoseStack poseStack, TuitionHandbowEntity animatable, BakedGeoModel bakedModel, 
			RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
			float partialTick, int packedLight, int packedOverlay) {
			RenderType glowRenderType = RenderType.eyes(LAYER);
			getRenderer().reRender(
				getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, glowRenderType, 
				bufferSource.getBuffer(glowRenderType), partialTick, packedLight, 
				OverlayTexture.NO_OVERLAY, 0.5f, 0.5f, 0.5f, 1);
		}
	}
}
