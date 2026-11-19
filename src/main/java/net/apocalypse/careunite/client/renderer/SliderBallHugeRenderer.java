
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.SliderBallHugeModel;
import net.apocalypse.careunite.entity.layer.SliderBallHugeLayer;
import net.apocalypse.careunite.entity.SliderBallHugeEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class SliderBallHugeRenderer extends GeoEntityRenderer<SliderBallHugeEntity> {
	public SliderBallHugeRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SliderBallHugeModel());
		this.shadowRadius = 1f;
		this.addRenderLayer(new SliderBallHugeLayer(this));
	}

	@Override
	public RenderType getRenderType(SliderBallHugeEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, SliderBallHugeEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 4f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
