
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.OceanizedSnifferModel;
import net.apocalypse.careunite.entity.layer.OceanizedSnifferLayer;
import net.apocalypse.careunite.entity.OceanizedSnifferEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class OceanizedSnifferRenderer extends GeoEntityRenderer<OceanizedSnifferEntity> {
	public OceanizedSnifferRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new OceanizedSnifferModel());
		this.shadowRadius = 1.5f;
		this.addRenderLayer(new OceanizedSnifferLayer(this));
	}

	@Override
	public RenderType getRenderType(OceanizedSnifferEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void render(
		OceanizedSnifferEntity entity, float entityYaw, float partialTick, 
		PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
	){
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

		//ThirsterRenderer.renderPrism(entity, partialTick, poseStack, bufferSource, LightTexture.FULL_BRIGHT);
	}

	@Override
	public void preRender(PoseStack poseStack, OceanizedSnifferEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(OceanizedSnifferEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
