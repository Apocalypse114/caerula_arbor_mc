
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.PenalBatallionCrossbowmanModel;
import net.apocalypse.careunite.entity.layer.PenalBatallionCrossbowmanLayer;
import net.apocalypse.careunite.entity.PenalBatallionCrossbowmanEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class PenalBatallionCrossbowmanRenderer extends GeoEntityRenderer<PenalBatallionCrossbowmanEntity> {
	public PenalBatallionCrossbowmanRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new PenalBatallionCrossbowmanModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new PenalBatallionCrossbowmanLayer(this));
	}

	@Override
	public RenderType getRenderType(PenalBatallionCrossbowmanEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, PenalBatallionCrossbowmanEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(PenalBatallionCrossbowmanEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
