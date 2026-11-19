package net.apocalypse.careunite.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.TypheousSignEntity;
import net.apocalypse.careunite.client.model.Modeltypheous_sign;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class TypheousSignRenderer extends EntityRenderer<TypheousSignEntity> {
	private static final ResourceLocation texture = new ResourceLocation("caerula_arbor_reunite:textures/entities/typheous_sign.png");
	private final Modeltypheous_sign model;

	public TypheousSignRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modeltypheous_sign(context.bakeLayer(Modeltypheous_sign.LAYER_LOCATION));
	}

	@Override
	public void render(TypheousSignEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(2 * (entityIn.tickCount + partialTicks)));
		//poseStack.mulPose(Axis.ZP.rotationDegrees(90));
		model.renderToBuffer(poseStack, vb, 0x0f00f0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(TypheousSignEntity entity) {
		return texture;
	}
}
