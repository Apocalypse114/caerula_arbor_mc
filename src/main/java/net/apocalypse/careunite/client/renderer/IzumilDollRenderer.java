
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.IzumilDollModel;
import net.apocalypse.careunite.entity.layer.IzumilDollLayer;
import net.apocalypse.careunite.entity.IzumilDollEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class IzumilDollRenderer extends GeoEntityRenderer<IzumilDollEntity> {
	public IzumilDollRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new IzumilDollModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new IzumilDollLayer(this));
	}

	@Override
	public RenderType getRenderType(IzumilDollEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, IzumilDollEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
