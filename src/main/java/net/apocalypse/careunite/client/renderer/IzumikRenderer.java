
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.IzumikModel;
import net.apocalypse.careunite.entity.layer.IzumikLayer;
import net.apocalypse.careunite.entity.IzumikEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Matrix4f;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.culling.Frustum;

public class IzumikRenderer extends GeoEntityRenderer<IzumikEntity> {
	public IzumikRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new IzumikModel());
		this.shadowRadius = 3f;
		this.addRenderLayer(new IzumikLayer(this));
	}

	@Override
	public boolean shouldRender(IzumikEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ){
		return true;
	}

	@Override
	public RenderType getRenderType(IzumikEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, IzumikEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1.5f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void render(
		IzumikEntity entity, float entityYaw, float partialTick, 
		PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
	) {
		float t = entity.tickSinceSkill() + partialTick;
		int ph = entity.getPhase();
		float R = 0;
		float G = 0;
		float B = 0;
		if (ph <= 0 && t > 0 && t <= 32){
			float alpha = 0;
			if (t > 22 && t < 25){
				alpha = 0;
			} else if (t >= 25 && t < 26) {
				alpha = 1;
			} else if (t >= 26){
				alpha = (32 - t) / 6f;
			}
			if (alpha > 0){
				R = (float)0xEE / 255f;
				G = (float)0xB1 / 255f;
				B = (float)0xDD / 255f;
				renderDodecagon(poseStack, bufferSource, 16 + (t - 25) * 0.125f, R, G, B, alpha * 0.5f);
			}
		} else if (ph > 0 && t > 0 && t <= 78){
			float r = 0;
			float w = 0;
			if (t <= 28){
				if (t<18){
					float p = t % 9;
					r = 12 - 1.2f - p * 1.2f;
					w = r * 0.1f;
					p /= 9f;
					if (t <= 9){
						R = lerpFloat(0xf1, 0x9d, p);
						G = lerpFloat(0xf1, 0xa9, p);
						B = lerpFloat(0xf9, 0xbe, p);
					} else {
						R = lerpFloat(0xa5, 0x62, p);
						G = lerpFloat(0xdf, 0xca, p);
						B = lerpFloat(0xe0, 0xcd, p);
					}
				} else {
					float p = t - 18;
					r = 13.2f - 1.2f - p * 1.2f;
					w = r * 0.1f;
					p *= 0.1f;
					R = lerpFloat(0xf4, 0xe4, p);
					G = lerpFloat(0xd6, 0xa3, p);
					B = lerpFloat(0xfc, 0xf3, p);
				}
			} else if (t>=32 && t<= 74){
				float p = t - 32;
				float pover = 42 - p;
				r = p;
				w = p <= 22 ? Mth.clamp(p * 0.1f, 0.1f, 2): pover * 0.1f;
				p /= 22f;
				R = lerpFloat(0xf2, 0xee, p);
				G = lerpFloat(0xf2, 0xb1, p);
				B = lerpFloat(0xf2, 0xdd, p);
			}
			poseStack.pushPose();
			poseStack.translate(0, 1.5f, 0);
			if (r > 0){
				renderFrame(poseStack, bufferSource, r, (int)R, (int)G ,(int)B, w);
				if (t > 28) renderDodecagon(poseStack, bufferSource, r, (float)R/255f, (float)G/255f, (float)B/255f, (74f - t) / 74f);
			}
			t -= 2;
			if (t>=32 && t<= 74){
				float p = t - 32;
				float pover = 42 - p;
				r = p;
				w = p <= 22 ? Mth.clamp(p * 0.1f, 0.1f, 2): pover * 0.1f;
				p /= 22f;
				p = 1 - p;
				R = lerpFloat(0xf2, 0xee, p);
				G = lerpFloat(0xf2, 0xb1, p);
				B = lerpFloat(0xf2, 0xdd, p);
				renderFrame(poseStack, bufferSource, r, (int)R, (int)G ,(int)B, w);
			}
			poseStack.popPose();
		}
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
		
	}

	private float lerpFloat(int f1, int f2, float p){
		return f1 + (f2 - f1) * p;
	}

	private static final RenderType FRAME = RenderType.entityTranslucentEmissive(new ResourceLocation("caerula_arbor_reunite:textures/entities/white.png"));

	private static final RenderType DODE = RenderType.entityTranslucentEmissive(new ResourceLocation("caerula_arbor_reunite:textures/entities/white_noise.png"));

	public static void renderFrame(
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        float radius,
        int r, int g, int b,
        float width
	) {
	    final int sides = 12;
	    final float alpha = 0.5f;
	    final float rf = r / 255.0f;
	    final float gf = g / 255.0f;
	    final float bf = b / 255.0f;
	    final float halfWidth = width / 2.0f;
	
	    VertexConsumer consumer = bufferSource.getBuffer(FRAME);
	    Matrix4f pose = poseStack.last().pose();

	    float[] xs = new float[sides];
	    float[] zs = new float[sides];
	    for (int i = 0; i < sides; i++) {
	        double angle = 2.0 * Math.PI * i / sides;
	        xs[i] = (float) (Math.cos(angle) * radius);
	        zs[i] = (float) (Math.sin(angle) * radius);
	    }
	
	    for (int i = 0; i < sides; i++) {
	        int j = (i + 1) % sides;
	        float ax = xs[i], az = zs[i];
	        float bx = xs[j], bz = zs[j];
	
	        float dx = bx - ax;
	        float dz = bz - az;
	        float len = (float) Math.sqrt(dx * dx + dz * dz);
	        if (len < 1e-6f) continue;
	
	        // XZ 平面内垂直于边的单位法线，并确保朝外
	        float nx = -dz / len;
	        float nz = dx / len;
	        float mx = (ax + bx) * 0.5f;
	        float mz = (az + bz) * 0.5f;
	        if (mx * nx + mz * nz < 0) {
	            nx = -nx;
	            nz = -nz;
	        }
	
	        float aOutX = ax + nx * halfWidth, aOutZ = az + nz * halfWidth;
	        float bOutX = bx + nx * halfWidth, bOutZ = bz + nz * halfWidth;
	        float aInX  = ax - nx * halfWidth, aInZ  = az - nz * halfWidth;
	        float bInX  = bx - nx * halfWidth, bInZ  = bz - nz * halfWidth;
	
	        writeQuad(consumer, pose,
	                aOutX, aOutZ,
	                bOutX, bOutZ,
	                bInX,  bInZ,
	                aInX,  aInZ,
	                rf, gf, bf, alpha,
	                0.0f, 1.0f, 0.0f);
	
	        writeQuad(consumer, pose,
	                aInX,  aInZ,
	                bInX,  bInZ,
	                bOutX, bOutZ,
	                aOutX, aOutZ,
	                rf, gf, bf, alpha,
	                0.0f, -1.0f, 0.0f);
	    }
	}

	public static void renderDodecagon(PoseStack poseStack, MultiBufferSource bufferSource,
                                       float apothem,
                                       float red, float green, float blue, float alpha) {
        if (apothem <= 0.0F) return;

        float circumradius = apothem / Mth.cos((float) Math.toRadians(15.0F));

        float r = Mth.clamp(red, 0.0F, 1.0F);
        float g = Mth.clamp(green, 0.0F, 1.0F);
        float b = Mth.clamp(blue, 0.0F, 1.0F);
        float a = Mth.clamp(alpha, 0.0F, 1.0F);

        poseStack.pushPose();

        poseStack.translate(0, 1, 0);

        VertexConsumer consumer = bufferSource.getBuffer(DODE);

        float cx = 0.0F, cy = 0.0F, cz = 0.0F;
        
        float centerU = 0.5F, centerV = 0.5F;

        int sides = 12;
        for (int i = 0; i < sides; i++) {

            float angle1 = (float) (2.0 * Math.PI * i / sides);
            float angle2 = (float) (2.0 * Math.PI * (i + 1) / sides);

            float x1 = circumradius * Mth.cos(angle1);
            float z1 = circumradius * Mth.sin(angle1);
            
            float x2 = circumradius * Mth.cos(angle2);
            float z2 = circumradius * Mth.sin(angle2);

            float u1 = 0.5F + 0.5F * Mth.cos(angle1);
            float v1 = 0.5F + 0.5F * Mth.sin(angle1);
            
            float u2 = 0.5F + 0.5F * Mth.cos(angle2);
            float v2 = 0.5F + 0.5F * Mth.sin(angle2);

            vertex(consumer, poseStack, cx, cy, cz, r, g, b, a, centerU, centerV);
            vertex(consumer, poseStack, x1, cy, z1, r, g, b, a, u1, v1);
            vertex(consumer, poseStack, x2, cy, z2, r, g, b, a, u2, v2);

            vertex(consumer, poseStack, x2, cy, z2, r, g, b, a, u2, v2);
        }

        poseStack.popPose();
    }
	
	/** 写入一个 QUADS 模式的四边形，4 个顶点按周界顺序传入 */
	private static void writeQuad(
	        VertexConsumer consumer, Matrix4f pose,
	        float x1, float z1,
	        float x2, float z2,
	        float x3, float z3,
	        float x4, float z4,
	        float r, float g, float b, float a,
	        float nx, float ny, float nz
	) {
	    addVertex(consumer, pose, x1, z1, r, g, b, a, 0.0f, 0.0f, nx, ny, nz);
	    addVertex(consumer, pose, x2, z2, r, g, b, a, 1.0f, 0.0f, nx, ny, nz);
	    addVertex(consumer, pose, x3, z3, r, g, b, a, 1.0f, 1.0f, nx, ny, nz);
	    addVertex(consumer, pose, x4, z4, r, g, b, a, 0.0f, 1.0f, nx, ny, nz);
	}
	
	private static void addVertex(
	        VertexConsumer consumer, Matrix4f pose,
	        float x, float z,
	        float r, float g, float b, float a,
	        float u, float v,
	        float nx, float ny, float nz
	) {
	    // NEW_ENTITY 格式：Position -> Color -> UV0 -> UV1(overlay) -> UV2(light) -> Normal
	    consumer.vertex(pose, x, 0.0f, z)
	            .color(r, g, b, a)
	            .uv(u, v)
	            .overlayCoords(OverlayTexture.NO_OVERLAY)
	            .uv2(0xF000F0)
	            .normal(nx, ny, nz)
	            .endVertex();
	}

	private static void vertex(VertexConsumer consumer, PoseStack poseStack,
                               float x, float y, float z,
                               float r, float g, float b, float a,
                               float u, float v) {
        consumer.vertex(poseStack.last().pose(), x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(poseStack.last().normal(), 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

	@Override
	protected float getDeathMaxRotation(IzumikEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
