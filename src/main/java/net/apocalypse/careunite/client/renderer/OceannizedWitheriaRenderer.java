
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.OceannizedWitheriaModel;
import net.apocalypse.careunite.entity.layer.OceannizedWitheriaLayer;
import net.apocalypse.careunite.entity.OceannizedWitheriaEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class OceannizedWitheriaRenderer extends GeoEntityRenderer<OceannizedWitheriaEntity> {
	public OceannizedWitheriaRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new OceannizedWitheriaModel());
		this.shadowRadius = 1f;
		this.addRenderLayer(new OceannizedWitheriaLayer(this));
	}

	@Override
	public RenderType getRenderType(OceannizedWitheriaEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, OceannizedWitheriaEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		float scale = 1.25f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(OceannizedWitheriaEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
