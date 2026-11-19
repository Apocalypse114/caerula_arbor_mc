package net.apocalypse.careunite.core;

import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.procedures.GetSanityPercentageProcedure;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.procedures.GetSanityPercentageProcedure;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LightTexture;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.LivingEntity;

public class CARenderUtil {
	private static final ResourceLocation SANITY_BAR = new ResourceLocation("caerula_arbor_reunite:textures/screens/sanity_disp_bar.png");
	private static final ResourceLocation ALC = new ResourceLocation("caerula_arbor_reunite:textures/screens/alchemy_base_slot.png");
	private static final ResourceLocation LAYER = new ResourceLocation("caerula_arbor_reunite:textures/screens/layered_barrier.png");

	public static void renderLayeredBarrier(LivingEntity entityIn, PoseStack poseStack, MultiBufferSource bufferSource){
		if (entityIn == Minecraft.getInstance().player) return;
		float alpha = CaerulaConfigsConfiguration.SANITY_ALPHA.get().floatValue();
		if (alpha <= 0) return;
		AttributeInstance ins = entityIn.getAttribute(CaerulaArborModAttributes.LAYERED_BARRIER.get());
		if (ins == null) return;
		int layered = (int)ins.getBaseValue();
		if (layered > 0){
			if (layered > 9) layered = 9;
			layered -= 1;
			int corX = layered % 3;
			int corY = layered / 3;
			renderBillboard(poseStack, bufferSource, LAYER, 36, 36, 0, entityIn.getBbHeight() * 0.5f, 0, alpha, 
				corX * 12, corY * 12, 12, 12, entityIn.getBbWidth() * 0.75f, LightTexture.FULL_BRIGHT, -0.375f, 0.375f);
		}
	}

	public static void renderSanity(Entity entityIn, PoseStack poseStack, MultiBufferSource bufferSource, int light){
		if (entityIn == Minecraft.getInstance().player) return;
		float p = (float) GetSanityPercentageProcedure.execute(entityIn);
		if (p >= 0 && p < 1){
			float dy = entityIn.getBbHeight() + CaerulaConfigsConfiguration.SANITY_DY.get().floatValue();
			float len = 24 * p;
			Double alpha = CaerulaConfigsConfiguration.SANITY_ALPHA.get();
			renderBillboard(poseStack, bufferSource, SANITY_BAR, 25, 4, 0, dy + 0.1875f, 0, 
				alpha.floatValue(), 0, 0, 25, 2, 0, light, -0.75f, 0);
			renderBillboard(poseStack, bufferSource, SANITY_BAR, 25, 4, 0, dy + 0.1875f, 0, 
				alpha.floatValue(), 0, 2, len, 2, 0.01f, LightTexture.FULL_BRIGHT, -0.75f, 0);
		}
	}

	public static void renderAlchemy(AlchemyCreation ac, PoseStack poseStack, MultiBufferSource bufferSource, int light){
		if (ac == null) return;
		int curB = ac.getClientBaseHeld();
		int maxB = ac.maxBase();
		int slotH = 1 + 3 * maxB;
		float blockH = slotH * 0.0625f;
		Double alpha = CaerulaConfigsConfiguration.ALC_ALPHA.get();
		float cdy = CaerulaConfigsConfiguration.ALC_DY.get().floatValue();
		float cdx = CaerulaConfigsConfiguration.ALC_DX.get().floatValue() - 0.5f;
		float bbh = ac.getBbHeight();
		float bbw = ac.getBbWidth();
		renderBillboard(poseStack, bufferSource, ALC, 8, 28, 0, bbh * 0.5f + blockH + cdy, 0, alpha.floatValue(), 
			0, 0, 5, slotH, 0.5f, light, -bbw * 0.55f + cdx, 0);
		int barH = 1 + 3 * curB;
		renderBillboard(poseStack, bufferSource, ALC, 8, 28, 0, bbh * 0.5f + blockH + cdy, 0, alpha.floatValue(), 
			5, 0, 3, barH, 0.51f, LightTexture.FULL_BRIGHT, -bbw * 0.55f + cdx + 0.0625f , 0);
	}

	public static void renderBillboard(PoseStack poseStack, MultiBufferSource bufferSource,
                                       ResourceLocation texture, int wide, int height,
                                       float dx, float dy, float dz, float alpha,
                                       int uOffset, int vOffset, float uSize, int vSize, float depth, int light, float faceOffset, float yOffset) {
        
        if (wide <= 0 || height <= 0 || uSize <= 0 || vSize <= 0 || alpha <= 0) return;
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        poseStack.pushPose();
        poseStack.translate(dx, dy, dz);
        poseStack.mulPose(camera.rotation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.translate(faceOffset, yOffset, depth);

        float physicalWidth = uSize / 16.0F;
        float physicalHeight = vSize / 16.0F;

        float uMin = (float) uOffset / wide;
        float uMax = (float) (uOffset + uSize) / wide;
        float vMin = (float) vOffset / height;
        float vMax = (float) (vOffset + vSize) / height;

        float r = 1.0F, g = 1.0F, b = 1.0F;
        float a = Mth.clamp(alpha, 0.0F, 1.0F);

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(texture));

        vertex(consumer, poseStack, 0, 0, 0, r, g, b, a, uMin, vMin, light);
        vertex(consumer, poseStack, physicalWidth, 0, 0, r, g, b, a, uMax, vMin, light);
        vertex(consumer, poseStack, physicalWidth, -physicalHeight, 0, r, g, b, a, uMax, vMax, light);
        vertex(consumer, poseStack, 0, -physicalHeight, 0, r, g, b, a, uMin, vMax, light);

        poseStack.popPose();
    }

    private static void vertex(VertexConsumer consumer, PoseStack poseStack,
                               float x, float y, float z,
                               float r, float g, float b, float a,
                               float u, float v, int light) {
        consumer.vertex(poseStack.last().pose(), x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(poseStack.last().normal(), 0.0F, 0.0F, 1.0F) 
                .endVertex();
    }
}
