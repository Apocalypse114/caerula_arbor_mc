
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.ThirsterModel;
import net.apocalypse.careunite.entity.ThirsterEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.apocalypse.careunite.procedures.GetLivingBarrierBarProcedure;
import com.mojang.math.Axis;
import net.minecraft.world.entity.LivingEntity;

import net.apocalypse.careunite.CaerulaArborMod;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

public class ThirsterRenderer extends GeoEntityRenderer<ThirsterEntity> {
	public ThirsterRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new ThirsterModel());
		this.shadowRadius = 1f;
	}

	private static final RenderType TYPE = RenderType.entityTranslucentEmissive(new ResourceLocation("caerula_arbor_reunite:textures/entities/white.png"));

	@Override
	public RenderType getRenderType(ThirsterEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void render(
		ThirsterEntity entity, float entityYaw, float partialTick, 
		PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
	){
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

		//renderPrism(entity, partialTick, poseStack, bufferSource, LightTexture.FULL_BRIGHT);
	}

	public static void renderPrism(Entity entityIn, float part, PoseStack poseStack, MultiBufferSource bufferSource, int brightness) {
		double brr = GetLivingBarrierBarProcedure.execute(entityIn);
		if (brr <= 0) return;
		if (!(entityIn instanceof LivingEntity)) return;
		LivingEntity liv = (LivingEntity) entityIn;
		//float lastH = liv.getLastHurtByMobTimestamp();
		float t = entityIn.tickCount + part;
		//float deltaT = t - lastH;
		//CaerulaArborMod.LOGGER.info("delta hurt time calc:tickCount {} - stamp {} = delta {}",t, lastH, deltaT);
        renderPrism(poseStack, bufferSource, 
        	entityIn.getBbWidth() * (1.25f + 0.125f * Mth.sin(Mth.DEG_TO_RAD * t * 4.5f)), 
        	(float)(entityIn.getBbHeight() * brr * 0.04), 
        	brightness, t, liv.hurtTime);
    }

    public static void renderPrism(PoseStack poseStack, MultiBufferSource bufferSource,
                                   float baseEdge, float height, int brightness, float t, float alph) {
        float alpha1 = 0.5f;
        float alph2 = 0.1f;
        float r = 0x3d, g = 0xb2, b = 0x89;
        float r1 = 0xac, g1 = 0xe6, b1 = 0xd4;
        double alphaConf = CaerulaConfigsConfiguration.BARR_ALPHA.get();
        if (alphaConf <= 0) return;
        if (baseEdge > 0 && height > 0){
        	if (alph <= 10){
        		//CaerulaArborMod.LOGGER.info("delta hurt time: {}", alph);
        		alph *= 0.1f;
        		alpha1 = Mth.lerp(alph, 0.5f, 0.9f);
        		alph2 = Mth.lerp(alph, 0.1f, 0.3f);
        		r = Mth.lerp(alph, r, r1);
        		g = Mth.lerp(alph, g, g1);
        		b = Mth.lerp(alph, b, b1);
        	}
        	alphaConf = Mth.clamp(alphaConf, 0, 1);
        	alpha1 *= alphaConf;
        	alph2 *= alphaConf;
	        renderPrism(poseStack, bufferSource, baseEdge, height,
	                alpha1, alph2, r/255f, g/255f, b/255f, brightness, t);
        }
    }

    public static void renderPrism(PoseStack poseStack, MultiBufferSource bufferSource,
                                   float baseEdge, float height,
                                   float baseAlpha, float topAlpha,
                                   float red, float green, float blue, int brt, float ti) {
        float halfBase = baseEdge / 2.0F;
        float baseA = Mth.clamp(baseAlpha, 0.0F, 1.0F);
        float topA  = Mth.clamp(topAlpha,  0.0F, 1.0F);
        float r = Mth.clamp(red,   0.0F, 1.0F);
        float g = Mth.clamp(green, 0.0F, 1.0F);
        float b = Mth.clamp(blue,  0.0F, 1.0F);

        poseStack.pushPose();

		poseStack.mulPose(Axis.YP.rotationDegrees(ti * 2.25f));
        VertexConsumer consumer = bufferSource.getBuffer(TYPE);
        float h = halfBase;
        float t = height;
        addSide(consumer, poseStack,
                -h, 0, -h,   h, 0, -h,   h, t, -h,   -h, t, -h,
                0, 0, -1, r, g, b, baseA, topA, brt);   // 前
        addSide(consumer, poseStack,
                h, 0, -h,    h, 0, h,    h, t, h,    h, t, -h,
                1, 0, 0, r, g, b, baseA, topA, brt);    // 右
        addSide(consumer, poseStack,
                h, 0, h,    -h, 0, h,    -h, t, h,    h, t, h,
                0, 0, 1, r, g, b, baseA, topA, brt);    // 后
        addSide(consumer, poseStack,
                -h, 0, h,   -h, 0, -h,   -h, t, -h,   -h, t, h,
                -1, 0, 0, r, g, b, baseA, topA, brt);   // 左
        
        poseStack.popPose();
    }

    private static void addSide(VertexConsumer consumer, PoseStack poseStack,
                                float bx0, float by0, float bz0,   // 底左
                                float bx1, float by1, float bz1,   // 底右
                                float tx1, float ty1, float tz1,   // 顶右
                                float tx0, float ty0, float tz0,   // 顶左
                                float nx, float ny, float nz,
                                float r, float g, float b,
                                float baseAlpha, float topAlpha, int brt) {
        vertex(consumer, poseStack, bx0, by0, bz0, r, g, b, baseAlpha, 0.0F, 1.0F, nx, ny, nz, brt);
        vertex(consumer, poseStack, bx1, by1, bz1, r, g, b, baseAlpha, 1.0F, 1.0F, nx, ny, nz, brt);
        vertex(consumer, poseStack, tx1, ty1, tz1, r, g, b, topAlpha,  1.0F, 0.0F, nx, ny, nz, brt);
        vertex(consumer, poseStack, tx0, ty0, tz0, r, g, b, topAlpha,  0.0F, 0.0F, nx, ny, nz, brt);
    }

    private static void vertex(VertexConsumer consumer, PoseStack poseStack,
                               float x, float y, float z,
                               float r, float g, float b, float a,
                               float u, float v,
                               float nx, float ny, float nz, int brt) {
        consumer.vertex(poseStack.last().pose(), x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(brt)
                .normal(poseStack.last().normal(), nx, ny, nz)
                .endVertex();
    }

	private static int lerpPackedLight(int fromPacked, int toPacked, float t) {
	    int fromSky = (fromPacked >> 16) & 0xFFFF;
	    int fromBlock = fromPacked & 0xFFFF;
	
	    int toSky = (toPacked >> 16) & 0xFFFF;
	    int toBlock = toPacked & 0xFFFF;
	
	    int sky = Mth.lerpInt(t, fromSky, toSky);
	    int block = Mth.lerpInt(t, fromBlock, toBlock);
	
	    return (sky << 16) | (block & 0xFFFF);
	}

	@Override
	public void preRender(PoseStack poseStack, ThirsterEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1.2f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(ThirsterEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
