package net.apocalypse.careunite.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.BeaconSignEntity;
import net.apocalypse.careunite.client.model.Modelbeacon_sign;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.Frustum;

public class BeaconSignRenderer extends EntityRenderer<BeaconSignEntity> {
	private static final ResourceLocation texture = new ResourceLocation("caerula_arbor_reunite:textures/entities/beacon_sign.png");
	private static final ResourceLocation nope = new ResourceLocation("caerula_arbor_reunite:textures/entities/white.png");

	private final Modelbeacon_sign model;

	public BeaconSignRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelbeacon_sign(context.bakeLayer(Modelbeacon_sign.LAYER_LOCATION));
	}

	@Override
	public boolean shouldRender(BeaconSignEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ){
		return true;
	}

	public static final float COLOR = 0.003921f;

	@Override
	public void render(BeaconSignEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
		float t = entityIn.tickCount + partialTicks;
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(t));
		//poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		poseStack.popPose();
		renderTwoStagePrism(poseStack, bufferIn, 0.75f + 0.125f * Mth.sin(t * 2 * Mth.DEG_TO_RAD), (float)entityIn.height(partialTicks),- t * 2, 
			0xB9 * COLOR, 0xD0 * COLOR, 0xDF * COLOR, 0.33f, 
			0xE6 * COLOR, 0xF3 * COLOR, 0xF8 * COLOR, 0.75f);
		float texiaoH = (float)entityIn.texiaoH(partialTicks);
		float texiaoW = (float)entityIn.texiaoW(partialTicks);
		if (texiaoH > 0){
			ThirsterRenderer.renderPrism(poseStack, bufferIn,
		       texiaoW, texiaoH, 1, 0.25f,
		       0xf7 * COLOR, 0x59 * COLOR, 0x5c * COLOR, LightTexture.FULL_BRIGHT, 0);
		}
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	public static void renderTwoStagePrism(PoseStack poseStack, MultiBufferSource bufferSource,
                                           float baseEdge, float d, float rot,
                                           float r1, float g1, float b1, float alpha1,
                                           float r2, float g2, float b2, float alpha2) {
        float h = baseEdge / 2.0F;
        d = Mth.clamp(d, 0.0F, 32.0F);

        float a1 = Mth.clamp(alpha1, 0.0F, 1.0F);
        float a2 = Mth.clamp(alpha2, 0.0F, 1.0F);
        r1 = Mth.clamp(r1, 0.0F, 1.0F); g1 = Mth.clamp(g1, 0.0F, 1.0F); b1 = Mth.clamp(b1, 0.0F, 1.0F);
        r2 = Mth.clamp(r2, 0.0F, 1.0F); g2 = Mth.clamp(g2, 0.0F, 1.0F); b2 = Mth.clamp(b2, 0.0F, 1.0F);

        poseStack.pushPose();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.beaconBeam(nope, true));
        poseStack.mulPose(Axis.YP.rotationDegrees(rot));

        if (d > 0.0F) {
            addSide(consumer, poseStack, 
                    -h, 0, -h,   h, 0, -h,   h, d, -h,   -h, d, -h, 
                    0, 0, -1, r1, g1, b1, a1, r1, g1, b1, a1); // 前
            addSide(consumer, poseStack, 
                    h, 0, -h,    h, 0, h,    h, d, h,    h, d, -h, 
                    1, 0, 0, r1, g1, b1, a1, r1, g1, b1, a1);  // 右
            addSide(consumer, poseStack, 
                    h, 0, h,    -h, 0, h,    -h, d, h,    h, d, h, 
                    0, 0, 1, r1, g1, b1, a1, r1, g1, b1, a1);  // 后
            addSide(consumer, poseStack, 
                    -h, 0, h,   -h, 0, -h,   -h, d, -h,   -h, d, h, 
                    -1, 0, 0, r1, g1, b1, a1, r1, g1, b1, a1); // 左
        }
        float upperHeight = 32.0F - d;
        if (upperHeight > 0.0F) {
            addSide(consumer, poseStack, 
                    -h, d, -h,   h, d, -h,   h, 32.0F, -h,   -h, 32.0F, -h, 
                    0, 0, -1, r2, g2, b2, a2, r1, g1, b1, a1); // 前
            addSide(consumer, poseStack, 
                    h, d, -h,    h, d, h,    h, 32.0F, h,    h, 32.0F, -h, 
                    1, 0, 0, r2, g2, b2, a2, r1, g1, b1, a1);  // 右
            addSide(consumer, poseStack, 
                    h, d, h,    -h, d, h,    -h, 32.0F, h,    h, 32.0F, h, 
                    0, 0, 1, r2, g2, b2, a2, r1, g1, b1, a1);  // 后
            addSide(consumer, poseStack, 
                    -h, d, h,   -h, d, -h,   -h, 32.0F, -h,   -h, 32.0F, h, 
                    -1, 0, 0, r2, g2, b2, a2, r1, g1, b1, a1); // 左
        }

        poseStack.popPose();
    }
    private static void addSide(VertexConsumer consumer, PoseStack poseStack,
                                float bx0, float by0, float bz0,   // 底左
                                float bx1, float by1, float bz1,   // 底右
                                float tx1, float ty1, float tz1,   // 顶右
                                float tx0, float ty0, float tz0,   // 顶左
                                float nx, float ny, float nz,
                                float br, float bg, float bb, float ba, // 底部颜色
                                float tr, float tg, float tb, float ta) {
        
        // 底部两个顶点 (V = 0)
        vertex(consumer, poseStack, bx0, by0, bz0, br, bg, bb, ba, 0.0F, 0.0F, nx, ny, nz);
        vertex(consumer, poseStack, bx1, by1, bz1, br, bg, bb, ba, 1.0F, 0.0F, nx, ny, nz);
        
        // 顶部两个顶点 (V = 1)
        vertex(consumer, poseStack, tx1, ty1, tz1, tr, tg, tb, ta, 1.0F, 1.0F, nx, ny, nz);
        vertex(consumer, poseStack, tx0, ty0, tz0, tr, tg, tb, ta, 0.0F, 1.0F, nx, ny, nz);
    }

    /**
     * 写入单个顶点，使用 entityTranslucentEmissive 格式，最亮光照
     */
    private static void vertex(VertexConsumer consumer, PoseStack poseStack,
                               float x, float y, float z,
                               float r, float g, float b, float a,
                               float u, float v,
                               float nx, float ny, float nz) {
        consumer.vertex(poseStack.last().pose(), x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(poseStack.last().normal(), nx, ny, nz)
                .endVertex();
    }

	@Override
	public ResourceLocation getTextureLocation(BeaconSignEntity entity) {
		return texture;
	}
}
