package net.apocalypse.careunite.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.BeaconSelectorEntity;
import net.apocalypse.careunite.client.model.Modelbeacon_sign;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.Frustum;

public class BeaconSelectorRenderer extends EntityRenderer<BeaconSelectorEntity> {
	private static final ResourceLocation texture = new ResourceLocation("caerula_arbor_reunite:textures/entities/white.png");
	private static final ResourceLocation nos = new ResourceLocation("caerula_arbor_reunite:textures/entities/white.png");

//	private final Modelbeacon_sign model;

	public BeaconSelectorRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(BeaconSelectorEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = bufferIn.getBuffer(RenderType.entityTranslucentEmissive(this.getTextureLocation(entityIn)));
		VertexConsumer vbn = bufferIn.getBuffer(RenderType.entityTranslucentEmissive(nos));

		double dx = entityIn.lerpDx(partialTicks);
		double len = entityIn.getLen(partialTicks);
		float t = entityIn.tickCount + partialTicks;

		renderRectangle(poseStack, (float)len, dx, vb);

		renderBg(poseStack, bufferIn, (float)(32 + dx), vbn, (float)len * 0.125f * 0.35f * (1 + Mth.sin(Mth.DEG_TO_RAD * 2 * t)));
		
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(BeaconSelectorEntity entity) {
		return texture;
	}

	@Override
	public boolean shouldRender(BeaconSelectorEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ){
		return true;
	}

	public static void renderRectangle(PoseStack poseStack,
		float width, double dx, VertexConsumer consumer) {
        float halfLength = 32.0F; 
        float r = 43.0f / 255.0f , g = 185.0f / 255.0f, b = 176.0f / 255.0f;
        float alphaRight = 1.0F; // X 较大的边 (X = 0)
        float alphaLeft = 0.0F;  // X 较小的边 (X = -width)

        poseStack.pushPose();

        poseStack.translate(dx, 0.5, 0);

        vertex(consumer, poseStack, 0, 0, -halfLength, r, g, b, alphaRight, 1.0F, 0.0F);
        vertex(consumer, poseStack, 0, 0, halfLength, r, g, b, alphaRight, 1.0F, 1.0F);
        vertex(consumer, poseStack, -width, 0, halfLength, r, g, b, alphaLeft, 0.0F, 1.0F);
        vertex(consumer, poseStack, -width, 0, -halfLength, r, g, b, alphaLeft, 0.0F, 0.0F);

        poseStack.popPose();
    }

    public static void renderBg(PoseStack poseStack, MultiBufferSource bufferSource,
                                       float width, VertexConsumer consumer, float alpha) {

        width = Mth.clamp(width, 0.0F, 64.0F);
        if (width <= 0.0F) return;
        float minX = -32.0F;
        float maxX = -32.0F + width;

        float minZ = -32.0F;
        float maxZ = 32.0F;
        float uMax = width / 64.0F;
        float r = 43.0f / 255.0f , g = 185.0f / 255.0f, b = 176.0f / 255.0f;

        poseStack.pushPose();

        poseStack.translate(0, 0.475, 0);
        
        vertex(consumer, poseStack, minX, 0, minZ, r, g, b, alpha, 0.0F, 1.0F);
        vertex(consumer, poseStack, maxX, 0, minZ, r, g, b, alpha, uMax, 1.0F);
        vertex(consumer, poseStack, maxX, 0, maxZ, r, g, b, alpha, uMax, 0.0F);
        vertex(consumer, poseStack, minX, 0, maxZ, r, g, b, alpha, 0.0F, 0.0F);

        poseStack.popPose();
    }

    private static void vertex(VertexConsumer consumer, PoseStack poseStack,
                               float x, float y, float z,
                               float r, float g, float b, float a,
                               float u, float v) {
        consumer.vertex(poseStack.last().pose(), x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT) // 始终使用最亮光照 0xF000F0
                .normal(poseStack.last().normal(), 0.0F, 1.0F, 0.0F) // 平面垂直于 Y 轴，法线朝上
                .endVertex();
    }
}
