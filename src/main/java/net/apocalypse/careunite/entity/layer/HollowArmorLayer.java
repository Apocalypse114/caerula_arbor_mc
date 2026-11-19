package net.apocalypse.careunite.entity.layer;

import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.HollowArmorEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.ItemRenderer;

public class HollowArmorLayer extends GeoRenderLayer<HollowArmorEntity> {
	//private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/hollowarmor_lit.png");

	public HollowArmorLayer(GeoRenderer<HollowArmorEntity> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(PoseStack poseStack, HollowArmorEntity animatable, BakedGeoModel bakedModel, 
		RenderType renderType, MultiBufferSource bufferSource, 
		VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		RenderType glowRenderType = RenderType.eyes(animatable.getGlowmask());
		getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, 
			glowRenderType, bufferSource.getBuffer(glowRenderType), partialTick, packedLight,
			OverlayTexture.NO_OVERLAY, 0.5f, 0.5f, 0.5f, 1);
		if (animatable.isFoil()){
			getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, 
				glowRenderType, 
				ItemRenderer.getFoilBufferDirect(bufferSource, renderType, false, true), 
				partialTick, packedLight,
				OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		}
	}
}
