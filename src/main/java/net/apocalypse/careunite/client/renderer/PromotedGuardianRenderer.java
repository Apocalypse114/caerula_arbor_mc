
package net.apocalypse.careunite.client.renderer;

import com.mojang.math.Axis;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.PromotedGuardianModel;
import net.apocalypse.careunite.entity.layer.PromotedGuardianLayer;
import net.apocalypse.careunite.entity.PromotedGuardianEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.culling.Frustum;

public class PromotedGuardianRenderer extends GeoEntityRenderer<PromotedGuardianEntity> {
	public PromotedGuardianRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new PromotedGuardianModel());
		this.shadowRadius = 0.75f;
		this.addRenderLayer(new PromotedGuardianLayer(this));
	}

	@Override
	public RenderType getRenderType(PromotedGuardianEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public boolean shouldRender(PromotedGuardianEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ){return true;}

	@Override
	public void render(PromotedGuardianEntity pEntity, float pEntityYaw, float pPartialTicks,
					   PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);

		if (pEntity.isDeadOrDying()) return;
		Entity target = pEntity.getClientTarget();
		if (target == null) return;
		int t = pEntity.getLockTick();
		if (t <= 0) return;
		// 计算动画参数（与原始代码相同）
		float attackTime = 40 - t + pPartialTicks;
		float attackAnimScale = attackTime >= 20 ? 1: attackTime / 20;

		// 计算光束方向与长度（使用目标位置和自身眼部位置）
		Vec3 myPos = pEntity.getPosition(pPartialTicks);
		// 计算光束方向与长度（使用目标位置和自身眼部位置）
		Vec3 targetPos = target.getPosition(pPartialTicks).add(0, target.getBbHeight() * 0.5, 0);
		Vec3 eyePos = myPos.add(0, 0.5, 0).add(pEntity.getLookAngle().scale(0.5));
		Vec3 direction = eyePos.vectorTo(targetPos);
		float beamLength = (float) (direction.length() + 1.0D);
		direction = direction.normalize();

		// 调用通用激光渲染方法（传入相对起点 (0, eyeHeight, 0) 已由 getPosition 处理，
		// 此处起点为 (0,0,0) 因为外部已经处于实体位置，且我们将 eyeHeight 作为局部偏移传入）
		renderBeam(pPoseStack, pBuffer,
				new Vec3(0.0D,  pEntity.getEyeHeight(), 0.0D),
				direction, beamLength,
				attackTime, pEntity.tickCount, attackAnimScale);
		int exEye = Mth.clamp(pEntity.getExtraEye(), 0, 4);
		if (exEye > 0){
			for (int i = 0; i< exEye; i++){
				Vec3 eyePos0Raw = extraEyePos(i), eyePos0 = myPos.add(eyePos0Raw);
				direction = eyePos0.vectorTo(targetPos);
				renderBeam(pPoseStack, pBuffer,
						eyePos0Raw,
						direction.normalize(), (float) (direction.length() + 1),
						attackTime, pEntity.tickCount, attackAnimScale);
			}
		}
	}

	private Vec3 extraEyePos(int index){
		CoreGeoBone bone = this.getGeoModel().getAnimationProcessor().getBone("centerEye"+index);
		if (bone != null) return new Vec3(bone.getPivotX()*0.0625, bone.getPivotY()*0.0625, bone.getPivotZ()*0.0625);
		return Vec3.ZERO;
	}

	@Override
	public void preRender(PoseStack poseStack, PromotedGuardianEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	private static final ResourceLocation GUARDIAN_BEAM_LOCATION = new ResourceLocation("caerula_arbor_reunite:textures/entities/guardian_beam.png");
	private static final RenderType BEAM_RENDER_TYPE = RenderType.eyes(GUARDIAN_BEAM_LOCATION);

	private void renderBeam(PoseStack poseStack, MultiBufferSource buffer,
							Vec3 origin, Vec3 direction, float length,
							float attackTime, int tickCount, float attackAnimScale) {
		// 1. 颜色计算（与原始算法一致）
		float animScaleSq = attackAnimScale * attackAnimScale;
		int red   = 155 + (int) (animScaleSq * 87F);
		int green = 203 - (int) (animScaleSq * 78F);
		int blue  = 113 + (int) (animScaleSq * 78F);

		// 2. 扭曲相位与纹理进度
		float twistPhase = attackTime * 0.05F * -1.5F;
		float beamProgress = attackTime * 0.5F % 1.0F;

		// 3. 变换到光束局部坐标系（原点为起点，Y轴指向光束方向）
		poseStack.pushPose();
		poseStack.translate(origin.x, origin.y, origin.z);

		float pitch = (float) Math.acos(direction.y);
		float yaw   = (float) Math.atan2(direction.z, direction.x);
		poseStack.mulPose(Axis.YP.rotationDegrees((((float) Math.PI / 2F) - yaw) * (180F / (float) Math.PI)));
		poseStack.mulPose(Axis.XP.rotationDegrees(pitch * (180F / (float) Math.PI)));

		// 4. 计算光束横截面偏移量（主体半径0.2，末端半径0.282）
		float b1x = Mth.cos(twistPhase + (float) Math.PI) * 0.2F;
		float b1z = Mth.sin(twistPhase + (float) Math.PI) * 0.2F;
		float b2x = Mth.cos(twistPhase) * 0.2F;
		float b2z = Mth.sin(twistPhase) * 0.2F;
		float b3x = Mth.cos(twistPhase + (float) Math.PI / 2F) * 0.2F;
		float b3z = Mth.sin(twistPhase + (float) Math.PI / 2F) * 0.2F;
		float b4x = Mth.cos(twistPhase + (float) Math.PI * 1.5F) * 0.2F;
		float b4z = Mth.sin(twistPhase + (float) Math.PI * 1.5F) * 0.2F;

		float t1x = Mth.cos(twistPhase + 2.3561945F) * 0.282F;
		float t1z = Mth.sin(twistPhase + 2.3561945F) * 0.282F;
		float t2x = Mth.cos(twistPhase + (float) Math.PI / 4F) * 0.282F;
		float t2z = Mth.sin(twistPhase + (float) Math.PI / 4F) * 0.282F;
		float t3x = Mth.cos(twistPhase + 3.926991F) * 0.282F;
		float t3z = Mth.sin(twistPhase + 3.926991F) * 0.282F;
		float t4x = Mth.cos(twistPhase + 5.4977875F) * 0.282F;
		float t4z = Mth.sin(twistPhase + 5.4977875F) * 0.282F;

		// 5. 纹理V坐标（长度方向）
		float vStart = -1.0F + beamProgress;
		float vEnd   = length * 2.5F + vStart;
		float tipVOffset = (tickCount % 2 == 0) ? 0.5F : 0.0F;

		// 6. 获取VertexConsumer并渲染
		VertexConsumer consumer = buffer.getBuffer(BEAM_RENDER_TYPE);
		PoseStack.Pose pose = poseStack.last();
		Matrix4f mat4 = pose.pose();
		Matrix3f mat3 = pose.normal();

		// 主体四边形（两条交叉带）
		vertex(consumer, mat4, mat3, b1x, length, b1z, red, green, blue, 0.4999F, vEnd);
		vertex(consumer, mat4, mat3, b1x, 0.0F,  b1z, red, green, blue, 0.4999F, vStart);
		vertex(consumer, mat4, mat3, b2x, 0.0F,  b2z, red, green, blue, 0.0F,    vStart);
		vertex(consumer, mat4, mat3, b2x, length, b2z, red, green, blue, 0.0F,    vEnd);

		vertex(consumer, mat4, mat3, b3x, length, b3z, red, green, blue, 0.4999F, vEnd);
		vertex(consumer, mat4, mat3, b3x, 0.0F,  b3z, red, green, blue, 0.4999F, vStart);
		vertex(consumer, mat4, mat3, b4x, 0.0F,  b4z, red, green, blue, 0.0F,    vStart);
		vertex(consumer, mat4, mat3, b4x, length, b4z, red, green, blue, 0.0F,    vEnd);

		// 末端四边形（闪烁纹理）
		vertex(consumer, mat4, mat3, t1x, length, t1z, red, green, blue, 0.5F, tipVOffset + 0.5F);
		vertex(consumer, mat4, mat3, t2x, length, t2z, red, green, blue, 1.0F, tipVOffset + 0.5F);
		vertex(consumer, mat4, mat3, t4x, length, t4z, red, green, blue, 1.0F, tipVOffset);
		vertex(consumer, mat4, mat3, t3x, length, t3z, red, green, blue, 0.5F, tipVOffset);

		poseStack.popPose();
	}

	private static void vertex(VertexConsumer pConsumer, Matrix4f pPose, Matrix3f pNormal,
							   float pX, float pY, float pZ,
							   int pRed, int pGreen, int pBlue,
							   float pU, float pV) {
		pConsumer.vertex(pPose, pX, pY, pZ)
				.color(pRed, pGreen, pBlue, 255)
				.uv(pU, pV)
				.overlayCoords(OverlayTexture.NO_OVERLAY)
				.uv2(15728880)
				.normal(pNormal, 0.0F, 1.0F, 0.0F)
				.endVertex();
	}

	@Override
	protected float getDeathMaxRotation(PromotedGuardianEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
