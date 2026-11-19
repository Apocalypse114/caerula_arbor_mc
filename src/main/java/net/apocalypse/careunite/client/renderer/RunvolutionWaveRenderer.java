
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.RunvolutionWaveModel;
import net.apocalypse.careunite.entity.layer.RunvolutionWaveLayer;
import net.apocalypse.careunite.entity.RunvolutionWaveEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.culling.Frustum;

public class RunvolutionWaveRenderer extends GeoEntityRenderer<RunvolutionWaveEntity> {
	public RunvolutionWaveRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new RunvolutionWaveModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new RunvolutionWaveLayer(this));
	}

	@Override
	public RenderType getRenderType(RunvolutionWaveEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public boolean shouldRender(RunvolutionWaveEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ){return true;}

	@Override
	public void preRender(PoseStack poseStack, RunvolutionWaveEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(RunvolutionWaveEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
