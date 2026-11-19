package net.apocalypse.careunite.entity.layer;

import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.IberiaMonstrosityEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class IberiaMonstrosityLayer extends GeoRenderLayer<IberiaMonstrosityEntity> {
	private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite", "textures/entities/iberia_monstrosity_glowmask.png");

	public IberiaMonstrosityLayer(GeoRenderer<IberiaMonstrosityEntity> entityRenderer) {
		super(entityRenderer);
	}

	@Override
	public void render(PoseStack poseStack, IberiaMonstrosityEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		RenderType glowRenderType = RenderType.eyes(LAYER);
		float bright = animatable.deathProgress(partialTick);
		getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, 
			animatable, glowRenderType, bufferSource.getBuffer(glowRenderType), 
			partialTick, packedLight, OverlayTexture.NO_OVERLAY, bright, bright, bright, 1);
	}
}
