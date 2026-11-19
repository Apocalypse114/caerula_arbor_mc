
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.IshmaelModel;
import net.apocalypse.careunite.entity.layer.IshmaelLayer;
import net.apocalypse.careunite.entity.IshmaelEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Matrix3f;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.mojang.math.Axis;

public class IshmaelRenderer extends GeoEntityRenderer<IshmaelEntity> {
	public IshmaelRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new IshmaelModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new IshmaelLayer(this));
	}

	@Override
	public RenderType getRenderType(IshmaelEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

//	@Override
//	public void render(
//		IshmaelEntity entity, float entityYaw, float partialTick, 
//		PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
//	) {
//		int t = entity.getBeamTick();
//		if (t > 0){
//			poseStack.pushPose();
//			Entity c = entity.getAim();
//			if (c != null){
//				List<Vec3> start = entity.getStartPos();
//				int num = entity.charge();
//				Vec3 target = c.position().add(0, c.getBbHeight() * 0.5, 0), me = entity.position();
//				for (int i = 0; i < num; i++){
//					Vec3 pos = start.get(i);
//					renderBeams(
//						(float)(target.x + pos.x - me.x), (float)(target.y + pos.y - me.y), (float)(target.z + pos.z - me.z), 
//						partialTick, entity.tickCount, t * 0.1f, poseStack, bufferSource, packedLight);
//				}
//			}
//			poseStack.popPose();
//		}
//		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
//	}

	@Override
	public void preRender(PoseStack poseStack, IshmaelEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(IshmaelEntity entityLivingBaseIn) {
		return 0.0F;
	}

	private static final RenderType BEAM = RenderType.entitySmoothCutout(new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png"));

	public static void renderBeams(
		float pX, float pY, float pZ, float pPartialTick, int pTickCount, float scale,
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
			vertexconsumer.vertex(matrix4f, f4 * 0.5F * scale, f5 * 0.5F * scale, 0.0F).color(232, 197, 246, 255)
				.uv(f6, f2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
				.normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
			vertexconsumer.vertex(matrix4f, f4 * 0.125f * scale, f5 * 0.125f * scale, f1).color(181, 227, 238, 255)
				.uv(f6, f3).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
				.normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
			vertexconsumer.vertex(matrix4f, f7 * 0.125f * scale, f8 * 0.125f * scale, f1).color(181, 227, 238, 255)
				.uv(f9, f3).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
				.normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
			vertexconsumer.vertex(matrix4f, f7 * 0.5F * scale, f8 * 0.5F * scale, 0.0F).color(232, 197, 246, 255)
				.uv(f9, f2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight)
				.normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
			f4 = f7;
			f5 = f8;
			f6 = f9;
		}
		pPoseStack.popPose();
   }
}
