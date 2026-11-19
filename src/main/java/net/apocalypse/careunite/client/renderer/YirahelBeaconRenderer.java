
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.YirahelBeaconModel;
import net.apocalypse.careunite.entity.layer.YirahelBeaconLayer;
import net.apocalypse.careunite.entity.YirahelBeaconEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.culling.Frustum;

public class YirahelBeaconRenderer extends GeoEntityRenderer<YirahelBeaconEntity> {
	public YirahelBeaconRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new YirahelBeaconModel());
		this.shadowRadius = 0.6f;
		this.addRenderLayer(new YirahelBeaconLayer(this));
	}

	@Override
	public RenderType getRenderType(YirahelBeaconEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, YirahelBeaconEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public boolean shouldRender(YirahelBeaconEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ){
		return true;
	}

	@Override
	protected float getDeathMaxRotation(YirahelBeaconEntity entityLivingBaseIn) {
		return 0.0F;
	}

}
