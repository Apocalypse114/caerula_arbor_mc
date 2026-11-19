
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.SaintValerioModel;
import net.apocalypse.careunite.entity.layer.SaintValerioLayer;
import net.apocalypse.careunite.entity.SaintValerioEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.Frustum;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;

public class SaintValerioRenderer extends GeoEntityRenderer<SaintValerioEntity> {
	public SaintValerioRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SaintValerioModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new SaintValerioLayer(this));
	}

	@Override
	public void render(SaintValerioEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		float t = entityIn.pierceSkillTime() + partialTicks;
		if (t > 0 && t < 74){
			chargeSince(poseStack, bufferIn, 15, t, entityIn.yaws[0], entityIn.pitches[0], 1);
			chargeSince(poseStack, bufferIn, 20, t, entityIn.yaws[1], entityIn.pitches[1], 1);
			chargeSince(poseStack, bufferIn, 25, t, entityIn.yaws[2], entityIn.pitches[2], 1);
			chargeSince(poseStack, bufferIn, 30, t, entityIn.yaws[3], entityIn.pitches[3], 1);
			chargeSince(poseStack, bufferIn, 33, t, entityIn.yaws[4], entityIn.pitches[4], 1);
			chargeSince(poseStack, bufferIn, 36, t, entityIn.yaws[5], entityIn.pitches[5], 1);
			chargeSince(poseStack, bufferIn, 39, t, entityIn.yaws[6], entityIn.pitches[6], 1);
			chargeSince(poseStack, bufferIn, 42, t, entityIn.yaws[7], entityIn.pitches[7], 1);
			chargeSince(poseStack, bufferIn, 45, t, entityIn.yaws[8], entityIn.pitches[8], 1);
		}
		t = entityIn.shootSkillTime() + partialTicks;
		if (t > 0 && t < 81){
			chargeSince(poseStack, bufferIn, 10, t, entityIn.yaws[8], entityIn.pitches[0], 1.3f);
			chargeSince(poseStack, bufferIn, 12.5f, t, entityIn.yaws[7], entityIn.pitches[8], 1.6f);
			chargeSince(poseStack, bufferIn, 15, t, entityIn.yaws[6], entityIn.pitches[7], 1.9f);
			chargeSince(poseStack, bufferIn, 20, t, entityIn.yaws[5], entityIn.pitches[6], 1.9f);
			chargeSince(poseStack, bufferIn, 25, t, entityIn.yaws[4], entityIn.pitches[5], 1.9f);
			chargeSince(poseStack, bufferIn, 30, t, entityIn.yaws[3], entityIn.pitches[4], 1.9f);
			chargeSince(poseStack, bufferIn, 35, t, entityIn.yaws[2], entityIn.pitches[3], 1.9f);
			chargeSince(poseStack, bufferIn, 40, t, entityIn.yaws[1], entityIn.pitches[2], 1.9f);
			chargeSince(poseStack, bufferIn, 45, t, entityIn.yaws[0], entityIn.pitches[1], 1.9f);
			chargeSince(poseStack, bufferIn, 50, t, entityIn.yaws[8], entityIn.pitches[0], 1.9f);

			if (t < 65){
				float w = 24;
				float h = 2.2f;
				if (t < 20){
					w *= (t / 20f);
					h -= 1.2 * t / 20f;
				} else if (t < 60){
					h = 1 + 1.2f * (t - 20) / 40f;
				} else {
					h *= (65 - t) * 0.2f;
				}
				skillframe(poseStack, bufferIn, 2 * w, h);
			}
		}
		float half_w = 12;
		t = entityIn.reviveTick() - partialTicks;
		t /= 200f;
		if (t > 0){
			half_w = 16 - t * 4;
		} else if (entityIn.getPhase() > 0){
			half_w = 16;
		}
		float time = entityIn.tickCount + partialTicks;
		if (time < 10){
			half_w *= time * 0.1f;
		}
		frame(poseStack, bufferIn, time, half_w * 2, 1.33f + 0.25f * Mth.sin(time * 2 * Mth.DEG_TO_RAD));
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	private void frame(PoseStack poseStack, MultiBufferSource bufferSource, float t, float width, float height){
		//87C3E3, 9DF6FF;
		t %= 80;
		t = (t <= 40 ? t: 80 - t);
		t /= 40;
		float r = Mth.lerp(t, 0x87, 0x9D);
		float g = Mth.lerp(t, 0xC3, 0xF6);
		float b = Mth.lerp(t, 0xE3, 0xFF);
		ThirsterRenderer.renderPrism(poseStack, bufferSource,
	       width, height,
	       0.75f, 0.33f,
	       r/255f, g/255f, b/255f, LightTexture.FULL_BRIGHT, 0);
	}
	private void skillframe(PoseStack poseStack, MultiBufferSource bufferSource, float width, float height){
		//A7CBD7
		ThirsterRenderer.renderPrism(poseStack, bufferSource,
	       width, height,
	       0.9f, 0.64f,
	       (float)0xe0/255f, (float)0xef/255f, (float)0xf0/255f, LightTexture.FULL_BRIGHT, 0);
	}

	private void chargeSince(PoseStack poseStack, MultiBufferSource bufferSource, float t, float curT, float ya, float pi, float y){
		if (curT >= t && curT <= t + 15){
			curT -= t;
			curT /= 15;
			pierceCharge(poseStack, bufferSource, ya, pi, curT, curT <= 0.33f ? curT * 2.9f: (1 - curT) * 2.9f, y);
		}
	}

	public void pierceCharge(PoseStack poseStack, MultiBufferSource bufferSource, float yaw, float pit, float p, float a, float y){
		renderDirectedPrism(poseStack, bufferSource, Mth.sin(yaw * Mth.DEG_TO_RAD), Mth.sin(pit) * Mth.DEG_TO_RAD, Mth.cos(yaw * Mth.DEG_TO_RAD), y, 0.25f, 2, 0, p, a);
	}

	@Override
	public boolean shouldRender(SaintValerioEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ){
		return true;
	}

	@Override
	public RenderType getRenderType(SaintValerioEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, SaintValerioEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(SaintValerioEntity entityLivingBaseIn) {
		return 0.0F;
	}

	private static final RenderType LINK = RenderType.entityTranslucentEmissive(new ResourceLocation("caerula_arbor_reunite:textures/entities/presaint_charge.png"));

	public static void renderDirectedPrism(PoseStack poseStack, MultiBufferSource bufferSource,
                                           float dx, float dy, float dz, float y,
                                           float w, float h, float uOffset, float vOffset, float alpha) {
                                           	
        float length = Mth.sqrt(dx * dx + dy * dy + dz * dz);
        if (length < 1.0E-4F || w <= 0.0F || h <= 0.0F) return;

        float dirX = dx / length;
        float dirY = dy / length;
        float dirZ = dz / length;

        float endX = dirX * h;
        float endY = dirY * h;
        float endZ = dirZ * h;

        float tempX = 0.0F, tempY = 1.0F, tempZ = 0.0F;
        if (Math.abs(dirY) > 0.999F) {
            tempX = 1.0F; tempY = 0.0F; tempZ = 0.0F;
        }

        float axX = dirY * tempZ - dirZ * tempY;
        float axY = dirZ * tempX - dirX * tempZ;
        float axZ = dirX * tempY - dirY * tempX;
        float axLen = Mth.sqrt(axX * axX + axY * axY + axZ * axZ);
        axX /= axLen; axY /= axLen; axZ /= axLen;

        float azX = axY * dirZ - axZ * dirY;
        float azY = axZ * dirX - axX * dirZ;
        float azZ = axX * dirY - axY * dirX;

        float hw = w / 2.0F;
        float[] offX = new float[4];
        float[] offY = new float[4];
        float[] offZ = new float[4];
        
        offX[0] = axX * hw + azX * hw; offY[0] = axY * hw + azY * hw; offZ[0] = axZ * hw + azZ * hw;
        offX[1] = -axX * hw + azX * hw; offY[1] = -axY * hw + azY * hw; offZ[1] = -axZ * hw + azZ * hw;
        offX[2] = -axX * hw - azX * hw; offY[2] = -axY * hw - azY * hw; offZ[2] = -axZ * hw - azZ * hw;
        offX[3] = axX * hw - azX * hw; offY[3] = axY * hw - azY * hw; offZ[3] = axZ * hw - azZ * hw;

        float vMax = h / 4.0F; 
        float uMax = 1;

        poseStack.pushPose();
        VertexConsumer consumer = bufferSource.getBuffer(LINK);
        poseStack.translate(0, y, 0);

        for (int i = 0; i < 4; i++) {
            int j = (i + 1) % 4;
            
            float bx0 = offX[i], by0 = offY[i], bz0 = offZ[i];
            float bx1 = offX[j], by1 = offY[j], bz1 = offZ[j];
            
            float tx0 = offX[i] + endX, ty0 = offY[i] + endY, tz0 = offZ[i] + endZ;
            float tx1 = offX[j] + endX, ty1 = offY[j] + endY, tz1 = offZ[j] + endZ;

            vertex(consumer, poseStack, bx0, by0, bz0, uOffset, vOffset, alpha);             // 左下
            vertex(consumer, poseStack, bx1, by1, bz1, uOffset + uMax, vOffset, alpha);             // 右下
            vertex(consumer, poseStack, tx1, ty1, tz1, uOffset + uMax, vOffset + vMax, alpha);      // 右上
            vertex(consumer, poseStack, tx0, ty0, tz0, uOffset, vOffset + vMax, alpha);      // 左上
        }

        poseStack.popPose();
    }

    private static void vertex(VertexConsumer consumer, PoseStack poseStack,
                               float x, float y, float z,
                               float u, float v, float a) {
        consumer.vertex(poseStack.last().pose(), x, y, z)
                .color(1.0F, 1.0F, 1.0F, a) 
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT) 
                .normal(poseStack.last().normal(), 0.0F, 1.0F, 0.0F) 
                .endVertex();
    }
}
