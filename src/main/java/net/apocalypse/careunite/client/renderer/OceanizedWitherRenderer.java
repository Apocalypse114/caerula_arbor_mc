
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.OceanizedWitherModel;
import net.apocalypse.careunite.entity.layer.OceanizedWitherLayer;
import net.apocalypse.careunite.entity.OceanizedWitherEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class OceanizedWitherRenderer extends GeoEntityRenderer<OceanizedWitherEntity> {
	public OceanizedWitherRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new OceanizedWitherModel());
		this.shadowRadius = 1f;
		this.addRenderLayer(new OceanizedWitherLayer(this));
	}

	@Override
	public RenderType getRenderType(OceanizedWitherEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, OceanizedWitherEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		float scale = 1.25f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(OceanizedWitherEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
