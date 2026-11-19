package net.apocalypse.careunite.entity.layer;

import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.PromotedElderGuardianEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class PromotedElderGuardianLayer extends GeoRenderLayer<PromotedElderGuardianEntity> {
	private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/elder_guardian_lit.png");

	public PromotedElderGuardianLayer(GeoRenderer<PromotedElderGuardianEntity> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(PoseStack poseStack, PromotedElderGuardianEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		RenderType glowRenderType = RenderType.eyes(LAYER);
		getRenderer().reRender(getDefaultBakedModel(animatable), 
			poseStack, bufferSource, animatable, glowRenderType, 
			bufferSource.getBuffer(glowRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 0.64f, 0.64f, 0.64f, 1);
	}
}
