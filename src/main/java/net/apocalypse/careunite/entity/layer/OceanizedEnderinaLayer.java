package net.apocalypse.careunite.entity.layer;

import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;

public class OceanizedEnderinaLayer extends GeoRenderLayer<OceanizedEnderinaEntity> {
	private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/enderina_lit.png");
	private static final ResourceLocation LAYER_SKY = new ResourceLocation("caerula_arbor_reunite:textures/screens/enderina_style.png");

	public OceanizedEnderinaLayer(GeoRenderer<OceanizedEnderinaEntity> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(PoseStack poseStack, OceanizedEnderinaEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		if(animatable.getPhase() > 0){
			float tick = animatable.tickCount + partialTick;
		    RenderType energySwirlType = RenderType.energySwirl(LAYER_SKY, (tick % 64) * 0.015625f, (tick % 32) * 0.03215f);
		    VertexConsumer energyConsumer = bufferSource.getBuffer(energySwirlType);
		    float f = (float)(0.4 + 0.25 * Math.sin(tick* 2 * Mth.DEG_TO_RAD));
		    getRenderer().reRender(getDefaultBakedModel(animatable), 
				poseStack, bufferSource, animatable, energySwirlType, 
				energyConsumer, partialTick, packedLight, 
				OverlayTexture.NO_OVERLAY, f, f, f, f);
		}
		RenderType glowRenderType = RenderType.eyes(LAYER);
		getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, glowRenderType, bufferSource.getBuffer(glowRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
	}
}
