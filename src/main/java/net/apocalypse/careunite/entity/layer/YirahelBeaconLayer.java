package net.apocalypse.careunite.entity.layer;

import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.YirahelBeaconEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class YirahelBeaconLayer extends GeoRenderLayer<YirahelBeaconEntity> {
	private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/beacon_glowmask.png");
	private static final ResourceLocation ARMOR = new ResourceLocation("caerula_arbor_reunite", "textures/entities/beacon_layer.png");

	public YirahelBeaconLayer(GeoRenderer<YirahelBeaconEntity> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(PoseStack poseStack, YirahelBeaconEntity animatable, BakedGeoModel bakedModel, 
		RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		int b = animatable.getBshd();
		RenderType glowRenderType = RenderType.eyes(LAYER);
		float lit = 0.2f + b * 0.1f;
		getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, glowRenderType, 
			bufferSource.getBuffer(glowRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, lit, lit, lit, 1);
		RenderType armorRenderType = RenderType.eyes(ARMOR);
		float maxInvT = b * 8;
		float invT = animatable.getInvTime() - partialTick;
		if (invT > 0) {
			lit = invT / maxInvT;
			getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armorRenderType, 
				bufferSource.getBuffer(armorRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, lit, lit, lit, 0.5f);
		}
	}
}
