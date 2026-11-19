
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.Al1SHelperModel;
import net.apocalypse.careunite.entity.layer.Al1SHelperLayer;
import net.apocalypse.careunite.entity.Al1SHelperEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class Al1SHelperRenderer extends GeoEntityRenderer<Al1SHelperEntity> {
	public Al1SHelperRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new Al1SHelperModel());
		this.shadowRadius = 0.6f;
		this.addRenderLayer(new Al1SHelperLayer(this));
	}

	@Override
	public RenderType getRenderType(Al1SHelperEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, Al1SHelperEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(Al1SHelperEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
