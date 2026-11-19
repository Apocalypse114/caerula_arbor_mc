
package net.apocalypse.careunite.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HierarchicalModel;

import net.apocalypse.careunite.entity.RaidSignEntity;
import net.apocalypse.careunite.client.model.animations.raid_signAnimation;
import net.apocalypse.careunite.client.model.Modelraid_sign;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.math.Axis;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;

public class RaidSignRenderer extends MobRenderer<RaidSignEntity, Modelraid_sign<RaidSignEntity>> {
	public RaidSignRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedModel(context.bakeLayer(Modelraid_sign.LAYER_LOCATION)), 0f);
	}

	private static final ResourceLocation MAIN = new ResourceLocation("caerula_arbor_reunite:textures/entities/raid_sign.png");
	private static final ResourceLocation BEACON = new ResourceLocation("caerula_arbor_reunite:textures/entities/red_beacon.png");

	private static final float[] COLOR = new float[]{1, 1, 1};

	@Override
	public ResourceLocation getTextureLocation(RaidSignEntity entity) {
		return MAIN;
	}

	private static final class AnimatedModel extends Modelraid_sign<RaidSignEntity> {
		private final ModelPart root;
		private final HierarchicalModel animator = new HierarchicalModel<RaidSignEntity>() {
			@Override
			public ModelPart root() {
				return root;
			}

			@Override
			public void setupAnim(RaidSignEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
				this.root().getAllParts().forEach(ModelPart::resetPose);
				this.animate(entity.animationState0, raid_signAnimation.idle, ageInTicks, 1f);
			}
		};

		public AnimatedModel(ModelPart root) {
			super(root);
			this.root = root;
		}

		@Override
		public void setupAnim(RaidSignEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			animator.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
	}

	@Override
	public void render(RaidSignEntity pEntity, 
		float pEntityYaw, float pPartialTick, 
		PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
		super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
		renderBeaconBeam(pPoseStack, pBuffer, BEACON, 
			pPartialTick, 1, pEntity.tickCount, -4, 256, COLOR, 0.33f);
   }

	@Override
   public boolean shouldRender(RaidSignEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ){
   		return true;
   }

	public static void renderBeaconBeam(PoseStack pPoseStack, MultiBufferSource pBufferSource, 
		ResourceLocation pBeamLocation, 
		float pPartialTick, float pTextureScale, long pGameTime, 
		int pYOffset, int pHeight, float[] pColors, float pGlowRadius) {
		int i = pYOffset + pHeight;
		pPoseStack.pushPose();
		//pPoseStack.translate(0.5D, 0.0D, 0.5D);
		float f = (float)Math.floorMod(pGameTime, 40) + pPartialTick;
		float f1 = pHeight < 0 ? f : -f;
		float f2 = Mth.frac(f1 * 0.2F - (float)Mth.floor(f1 * 0.1F));
		float f3 = pColors[0];
		float f4 = pColors[1];
		float f5 = pColors[2];
		pPoseStack.mulPose(Axis.YP.rotationDegrees(f * 2.25F - 45.0F));
		float f6 = 0.0F;
		float f8 = 0.0F;
		float f10 = 0.0F;
		float f11 = 0.0F;
		float f13 = 0.0F;
		float f14 = 1.0F;
		float f15 = -1.0F + f2;
		 f6 = -pGlowRadius;
		float f7 = -pGlowRadius;
		f8 = -pGlowRadius;
		float f9 = -pGlowRadius;
		f13 = 0.0F;
		f14 = 1.0F;
		f15 = -1.0F + f2;
		float f16 = (float)pHeight * pTextureScale + f15;
		renderPart(pPoseStack, pBufferSource.getBuffer(RenderType.beaconBeam(pBeamLocation, true)), 
			f3, f4, f5, 0.5F, pYOffset, i, f6, f7, pGlowRadius, 
			f8, f9, pGlowRadius, pGlowRadius, pGlowRadius, 0.0F, 1.0F, f16, f15);
		pPoseStack.popPose();
   }

   private static void renderPart(PoseStack pPoseStack, VertexConsumer pConsumer, 
   		float pRed, float pGreen, float pBlue, float pAlpha, 
   		int pMinY, int pMaxY, 
   		float pX0, float pZ0, float pX1, float pZ1, 
   		float pX2, float pZ2, float pX3, float pZ3, 
   		float pMinU, float pMaxU, float pMinV, float pMaxV) {
		PoseStack.Pose posestack$pose = pPoseStack.last();
		Matrix4f matrix4f = posestack$pose.pose();
		Matrix3f matrix3f = posestack$pose.normal();
		renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, 
			pX0, pZ0, pX1, pZ1, pMinU, pMaxU, pMinV, pMaxV);
		renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, 
			pX3, pZ3, pX2, pZ2, pMinU, pMaxU, pMinV, pMaxV);
		renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, 
			pX1, pZ1, pX3, pZ3, pMinU, pMaxU, pMinV, pMaxV);
		renderQuad(matrix4f, matrix3f, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY, 
			pX2, pZ2, pX0, pZ0, pMinU, pMaxU, pMinV, pMaxV);
   }

   	private static void renderQuad(Matrix4f pPose, Matrix3f pNormal, 
   		VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, float pAlpha, 
   		int pMinY, int pMaxY, float pMinX, float pMinZ, float pMaxX, float pMaxZ, 
   		float pMinU, float pMaxU, float pMinV, float pMaxV) {
		addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMaxY, pMinX, pMinZ, pMaxU, pMinV);
		addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMinX, pMinZ, pMaxU, pMaxV);
		addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxX, pMaxZ, pMinU, pMaxV);
		addVertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMaxY, pMaxX, pMaxZ, pMinU, pMinV);
	}

   	private static void addVertex(Matrix4f pPose, Matrix3f pNormal, VertexConsumer pConsumer, 
   		float pRed, float pGreen, float pBlue, float pAlpha, 
   		int pY, float pX, float pZ, float pU, float pV) {
		pConsumer.vertex(pPose, pX, (float)pY, pZ)
			.color(pRed, pGreen, pBlue, pAlpha).uv(pU, pV)
			.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(pNormal, 0.0F, 1.0F, 0.0F).endVertex();
   }
}
