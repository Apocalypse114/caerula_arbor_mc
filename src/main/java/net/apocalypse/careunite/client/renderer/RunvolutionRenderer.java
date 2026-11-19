
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.RunvolutionModel;
import net.apocalypse.careunite.entity.layer.RunvolutionLayer;
import net.apocalypse.careunite.entity.RunvolutionEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class RunvolutionRenderer extends GeoEntityRenderer<RunvolutionEntity> {
	public RunvolutionRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new RunvolutionModel());
		this.shadowRadius = 2f;
		this.addRenderLayer(new RunvolutionLayer(this));
	}

	@Override
	public RenderType getRenderType(RunvolutionEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, RunvolutionEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 2f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(RunvolutionEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
