
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.TheLastKnightModel;
import net.apocalypse.careunite.entity.layer.TheLastKnightLayer;
import net.apocalypse.careunite.entity.TheLastKnightEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class TheLastKnightRenderer extends GeoEntityRenderer<TheLastKnightEntity> {
	public TheLastKnightRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TheLastKnightModel());
		this.shadowRadius = 1f;
		this.addRenderLayer(new TheLastKnightLayer(this));
	}

	@Override
	public RenderType getRenderType(TheLastKnightEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, TheLastKnightEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(TheLastKnightEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
