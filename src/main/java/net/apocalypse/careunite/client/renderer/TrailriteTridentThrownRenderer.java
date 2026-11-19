package net.apocalypse.careunite.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.TrailriteTridentThrownEntity;
import net.apocalypse.careunite.client.model.ModelTrailriteTrident;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.ItemRenderer;

public class TrailriteTridentThrownRenderer extends EntityRenderer<TrailriteTridentThrownEntity> {
	private static final ResourceLocation texture = new ResourceLocation("caerula_arbor_reunite:textures/entities/trident_trailrite.png");
	private final ModelTrailriteTrident model;

	public TrailriteTridentThrownRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new ModelTrailriteTrident(context.bakeLayer(ModelTrailriteTrident.LAYER_LOCATION));
	}

	@Override
	public void render(TrailriteTridentThrownEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		this.model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(bufferIn, this.model.renderType(this.getTextureLocation(entityIn)), false, entityIn.isFoil());
		this.model.renderToBuffer(poseStack, vertexconsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(TrailriteTridentThrownEntity entity) {
		return texture;
	}
}
