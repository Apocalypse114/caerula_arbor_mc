
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.OceanWitherSkeletonModel;
import net.apocalypse.careunite.entity.layer.OceanWitherSkeletonLayer;
import net.apocalypse.careunite.entity.OceanWitherSkeletonEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class OceanWitherSkeletonRenderer extends GeoEntityRenderer<OceanWitherSkeletonEntity> {
	public OceanWitherSkeletonRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new OceanWitherSkeletonModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new OceanWitherSkeletonLayer(this));
	}

	@Override
	public RenderType getRenderType(OceanWitherSkeletonEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, OceanWitherSkeletonEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		float scale = 1.25f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(OceanWitherSkeletonEntity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public void actuallyRender(PoseStack poseStack, OceanWitherSkeletonEntity animatable, BakedGeoModel model, 
			RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, 
			boolean isReRender, float partialTick, int packedLight, int packedOverlay, 
			float red, float green, float blue, float alpha) {
		float color = animatable.tickCount < 20 ? animatable.tickCount * 0.05f: 1;
		super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, 
			isReRender, partialTick, packedLight, packedOverlay, color, color, color, alpha);
	}
}
