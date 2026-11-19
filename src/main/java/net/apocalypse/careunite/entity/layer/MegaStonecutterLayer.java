package net.apocalypse.careunite.entity.layer;

import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.MegaStonecutterEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class MegaStonecutterLayer extends GeoRenderLayer<MegaStonecutterEntity> {
	private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/mega_stonecutter_chain.png");

	public MegaStonecutterLayer(GeoRenderer<MegaStonecutterEntity> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(PoseStack poseStack, MegaStonecutterEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		if(animatable.inSkill()){
			float tick = animatable.tickCount + partialTick;
			tick *= 0.5f;
		    RenderType energySwirlType = RenderType.energySwirl(LAYER, (tick % 64) * 0.015625f, (tick % 32) * 0.03125f);
		    VertexConsumer energyConsumer = bufferSource.getBuffer(energySwirlType);
		    getRenderer().reRender(getDefaultBakedModel(animatable), 
				poseStack, bufferSource, animatable, energySwirlType, 
				energyConsumer, partialTick, packedLight, 
				OverlayTexture.NO_OVERLAY, 0.75f, 0.75f, 0.75f, 1);
		}
	}
}
