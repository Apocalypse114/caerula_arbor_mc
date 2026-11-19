package net.apocalypse.careunite.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.InquisitionFireEntity;
import net.apocalypse.careunite.client.model.Modelbatalion;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class InquisitionFireRenderer extends GeoEntityRenderer<InquisitionFireEntity> {
	public InquisitionFireRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new Model());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new Layer(this));
	}

	@Override
	public RenderType getRenderType(InquisitionFireEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, InquisitionFireEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}


	public static class Model extends GeoModel<InquisitionFireEntity> {
		private static final ResourceLocation ANIM = new ResourceLocation("caerula_arbor_reunite", "animations/inquisition_fire.animation.json");
		private static final ResourceLocation MODEL = new ResourceLocation("caerula_arbor_reunite", "geo/inquisition_fire.geo.json");
		private static final ResourceLocation TEXTU = new ResourceLocation("caerula_arbor_reunite", "textures/entities/inquisition_fire.png");
		@Override
		public ResourceLocation getAnimationResource(InquisitionFireEntity entity) {
			return ANIM;
		}
	
		@Override
		public ResourceLocation getModelResource(InquisitionFireEntity entity) {
			return MODEL;
		}
	
		@Override
		public ResourceLocation getTextureResource(InquisitionFireEntity entity) {
			return TEXTU;
		}
	}

	public static class Layer extends GeoRenderLayer<InquisitionFireEntity> {
		private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/inquisition_fire.png");
	
		public Layer(GeoRenderer<InquisitionFireEntity> entityRenderer) {
			super(entityRenderer);
		}
	
		@Override
		public void render(PoseStack poseStack, InquisitionFireEntity animatable, BakedGeoModel bakedModel, RenderType renderType, 
				MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
			RenderType glowRenderType = RenderType.eyes(LAYER);
			getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, glowRenderType, 
				bufferSource.getBuffer(glowRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 0.75f, 0.75f, 0.75f, 1);
		}
}
}
