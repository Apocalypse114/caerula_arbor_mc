
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.MoistEnderCrystalModel;
import net.apocalypse.careunite.entity.layer.MoistEnderCrystalLayer;
import net.apocalypse.careunite.entity.MoistEnderCrystalEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Matrix3f;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.mojang.math.Axis;

public class MoistEnderCrystalRenderer extends GeoEntityRenderer<MoistEnderCrystalEntity> {
	public MoistEnderCrystalRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new MoistEnderCrystalModel());
		this.shadowRadius = 0f;
		this.addRenderLayer(new MoistEnderCrystalLayer(this));
	}

	@Override
	public RenderType getRenderType(MoistEnderCrystalEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, MoistEnderCrystalEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(MoistEnderCrystalEntity entityLivingBaseIn) {
		return 0.0F;
	}

	private double lerpX(Entity entity, float pPartialTicks){
		return Mth.lerp((double)pPartialTicks, entity.xo, entity.getX());
	}
	private double lerpY(Entity entity, float pPartialTicks){
		return Mth.lerp((double)pPartialTicks, entity.yo, entity.getY());
	}
	private double lerpZ(Entity entity, float pPartialTicks){
		return Mth.lerp((double)pPartialTicks, entity.zo, entity.getZ());
	}

	@Override
	public void render(
		MoistEnderCrystalEntity entity, float entityYaw, float partialTick, 
		PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
	) {
		poseStack.pushPose();
		Entity c = entity.getOwner();
		if (c!=null && c.isAlive())
			renderBeams(
				(float)(lerpX(c, partialTick) - lerpX(entity, partialTick)), 
				(float)(lerpY(c, partialTick) - lerpY(entity, partialTick)),
				(float)(lerpZ(c, partialTick) - lerpZ(entity, partialTick)), 
				partialTick, entity.tickCount, poseStack, bufferSource, packedLight);
		else entity.owner = null;
		poseStack.popPose();
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
		
	}

	private static final RenderType BEAM = RenderType.entitySmoothCutout(new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png"));

	public static void renderBeams(
		float pX, float pY, float pZ, float pPartialTick, int pTickCount, 
		PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight
	) {
		float f = Mth.sqrt(pX * pX + pZ * pZ);
		float f1 = Mth.sqrt(pX * pX + pY * pY + pZ * pZ);
		pPoseStack.pushPose();
		pPoseStack.translate(0.0F, 1F, 0.0F);
		pPoseStack.mulPose(Axis.YP.rotation((float)(-Math.atan2((double)pZ, (double)pX)) - ((float)Math.PI / 2F)));
		pPoseStack.mulPose(Axis.XP.rotation((float)(-Math.atan2((double)f, (double)pY)) - ((float)Math.PI / 2F)));
		VertexConsumer vertexconsumer = pBuffer.getBuffer(BEAM);
		float f2 = 0.0F - ((float)pTickCount + pPartialTick) * 0.01F;
		float f3 = Mth.sqrt(pX * pX + pY * pY + pZ * pZ) / 32.0F - ((float)pTickCount + pPartialTick) * 0.01F;
		int i = 8;
		float f4 = 0.0F;
		float f5 = 0.75F;
		float f6 = 0.0F;
		PoseStack.Pose posestack$pose = pPoseStack.last();
		Matrix4f matrix4f = posestack$pose.pose();
		Matrix3f matrix3f = posestack$pose.normal();
		for (int j=1;j<=4;j++){
			float f7 = Mth.sin((float)j * ((float)Math.PI * 2F) / 4.0F) * 0.75F;
			float f8 = Mth.cos((float)j * ((float)Math.PI * 2F) / 4.0F) * 0.75F;
			float f9 = (float)j / 8.0F;
			vertexconsumer.vertex(matrix4f, f4 * 0.5F, f5 * 0.5F, 0.0F).color(232, 197, 246, 255)
				.uv(f6, f2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
				.normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
			vertexconsumer.vertex(matrix4f, f4 * 0.125f, f5 * 0.125f, f1).color(181, 227, 238, 255)
				.uv(f6, f3).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
				.normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
			vertexconsumer.vertex(matrix4f, f7 * 0.125f, f8 * 0.125f, f1).color(181, 227, 238, 255)
				.uv(f9, f3).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
				.normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
			vertexconsumer.vertex(matrix4f, f7 * 0.5F, f8 * 0.5F, 0.0F).color(232, 197, 246, 255)
				.uv(f9, f2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
				.normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
			f4 = f7;
			f5 = f8;
			f6 = f9;
		}
		pPoseStack.popPose();
   }
}
