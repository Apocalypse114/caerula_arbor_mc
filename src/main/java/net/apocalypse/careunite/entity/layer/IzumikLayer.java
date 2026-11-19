package net.apocalypse.careunite.entity.layer;

import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.IzumikEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;

public class IzumikLayer extends GeoRenderLayer<IzumikEntity> {
	private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/izumik_lit.png");
	private static final ResourceLocation LAYER_INV = new ResourceLocation("caerula_arbor_reunite:textures/entities/invulnerable_layer.png");
	
	public IzumikLayer(GeoRenderer<IzumikEntity> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(PoseStack poseStack, IzumikEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		if(animatable.getPhase() <= 0){
			float tick = animatable.tickCount + partialTick;
			float p = (float)(0.5 + 0.25 * Math.sin(tick* 3 * Mth.DEG_TO_RAD));
			float q = (float)(0.5 + 0.25 * Math.sin(tick* 2 * Mth.DEG_TO_RAD));
		    RenderType energySwirlType = RenderType.energySwirl(LAYER_INV, p, q);
		    VertexConsumer energyConsumer = bufferSource.getBuffer(energySwirlType);
		    float f = (float)(0.85 + 0.15 * Math.sin(tick* 3 * Mth.DEG_TO_RAD));
		    getRenderer().reRender(getDefaultBakedModel(animatable), 
				poseStack, bufferSource, animatable, energySwirlType, 
				energyConsumer, partialTick, packedLight, 
				OverlayTexture.NO_OVERLAY, f, f, f, 1);
		}
		RenderType glowRenderType = RenderType.eyes(LAYER);
		getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, glowRenderType, bufferSource.getBuffer(glowRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
	}
}
