
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.MartusModel;
import net.apocalypse.careunite.entity.layer.MartusLayer;
import net.apocalypse.careunite.entity.MartusEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.Mth;
import net.minecraft.client.renderer.culling.Frustum;

public class MartusRenderer extends GeoEntityRenderer<MartusEntity> {
	public MartusRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new MartusModel());
		this.shadowRadius = 0.6f;
		this.addRenderLayer(new MartusLayer(this));
	}

	private static final RenderType LINK = RenderType.entityTranslucentEmissive(new ResourceLocation("caerula_arbor_reunite:textures/entities/martus_link.png"));

	@Override
	public void render(
		MartusEntity entity, float entityYaw, float partialTick, 
		PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
	){
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

		Entity[] entites = entity.clientLinkingEntity();

		for (int i=0; i < entites.length; i++){
			Entity toRender = entites[i];
			if (toRender != null && toRender.isAlive()){
				Vec3 myPos = entity.getPosition(partialTick).add(0, 1, 0);
				Vec3 hisPos = toRender.getPosition(partialTick).add(0, toRender.getBbHeight() * 0.5f, 0);
				Vec3 offset = myPos.vectorTo(hisPos);
				renderDirectedPrism(poseStack, bufferSource, (float)offset.x, (float)offset.y, (float)offset.z, 0.375f, (float)offset.length(), 
					((toRender.tickCount + partialTick) * 0.1f)%1,
					((toRender.tickCount + partialTick) * 0.06f)%1);
			}
		}
	}

	@Override
	public boolean shouldRender(MartusEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ){
		return true;
	}

	@Override
	public RenderType getRenderType(MartusEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, MartusEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public static void renderDirectedPrism(PoseStack poseStack, MultiBufferSource bufferSource,
                                           float dx, float dy, float dz,
                                           float w, float h, float uOffset, float vOffset) {
                                           	
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
        poseStack.translate(0, 1, 0);

        for (int i = 0; i < 4; i++) {
            int j = (i + 1) % 4;
            
            float bx0 = offX[i], by0 = offY[i], bz0 = offZ[i];
            float bx1 = offX[j], by1 = offY[j], bz1 = offZ[j];
            
            float tx0 = offX[i] + endX, ty0 = offY[i] + endY, tz0 = offZ[i] + endZ;
            float tx1 = offX[j] + endX, ty1 = offY[j] + endY, tz1 = offZ[j] + endZ;

            vertex(consumer, poseStack, bx0, by0, bz0, uOffset, vOffset);             // 左下
            vertex(consumer, poseStack, bx1, by1, bz1, uOffset + uMax, vOffset);             // 右下
            vertex(consumer, poseStack, tx1, ty1, tz1, uOffset + uMax, vOffset + vMax);      // 右上
            vertex(consumer, poseStack, tx0, ty0, tz0, uOffset, vOffset + vMax);      // 左上
        }

        poseStack.popPose();
    }

    private static void vertex(VertexConsumer consumer, PoseStack poseStack,
                               float x, float y, float z,
                               float u, float v) {
        consumer.vertex(poseStack.last().pose(), x, y, z)
                .color(1.0F, 1.0F, 1.0F, 0.64F) 
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT) 
                .normal(poseStack.last().normal(), 0.0F, 1.0F, 0.0F) 
                .endVertex();
    }

	@Override
	protected float getDeathMaxRotation(MartusEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
