
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.ShakingBellModel;
import net.apocalypse.careunite.entity.layer.ShakingBellLayer;
import net.apocalypse.careunite.entity.ShakingBellEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class ShakingBellRenderer extends GeoEntityRenderer<ShakingBellEntity> {
	public ShakingBellRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new ShakingBellModel());
		this.shadowRadius = 0.4f;
		this.addRenderLayer(new ShakingBellLayer(this));
	}

	@Override
	public RenderType getRenderType(ShakingBellEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, ShakingBellEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(ShakingBellEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
