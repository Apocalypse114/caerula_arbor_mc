
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.CaerulaOffspringModel;
import net.apocalypse.careunite.entity.layer.CaerulaOffspringLayer;
import net.apocalypse.careunite.entity.CaerulaOffspringEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class CaerulaOffspringRenderer extends GeoEntityRenderer<CaerulaOffspringEntity> {
	public CaerulaOffspringRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new CaerulaOffspringModel());
		this.shadowRadius = 0.4f;
		this.addRenderLayer(new CaerulaOffspringLayer(this));
	}

	@Override
	public RenderType getRenderType(CaerulaOffspringEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, CaerulaOffspringEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		float scale = 1.25f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(CaerulaOffspringEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
