package net.apocalypse.careunite.entity.layer;

import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.OceanizedLlamaEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class OceanizedLlamaLayer extends GeoRenderLayer<OceanizedLlamaEntity> {
	private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/llama_lit.png");
	private static final ResourceLocation SPARK = new ResourceLocation("caerula_arbor_reunite", "textures/entities/spark_layer.png");

	public OceanizedLlamaLayer(GeoRenderer<OceanizedLlamaEntity> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(
		PoseStack poseStack, OceanizedLlamaEntity animatable, BakedGeoModel bakedModel, RenderType renderType, 
		MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay
	) {
	
		if (animatable.isCharged()){
			RenderType glowRenderType = RenderType.eyes(LAYER);
			getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource,
				animatable, glowRenderType, bufferSource.getBuffer(glowRenderType), 
				partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			float tick = animatable.tickCount + partialTick;
		    RenderType energySwirlType = RenderType.energySwirl(SPARK, (tick % 64) * 0.015625f, (tick % 64) * 0.015625f);
		    VertexConsumer energyConsumer = bufferSource.getBuffer(energySwirlType);
		    getRenderer().reRender(getDefaultBakedModel(animatable), 
				poseStack, bufferSource, animatable, energySwirlType, 
				energyConsumer, partialTick, packedLight, 
				OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		}
	}
}
