
package net.apocalypse.careunite.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.model.OceanizedPigModel;
import net.apocalypse.careunite.entity.layer.OceanizedPigLayer;
import net.apocalypse.careunite.entity.OceanizedPigEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class OceanizedPigRenderer extends GeoEntityRenderer<OceanizedPigEntity> {
	public OceanizedPigRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new OceanizedPigModel());
		this.shadowRadius = 0.6f;
		this.addRenderLayer(new OceanizedPigLayer(this));
	}

	@Override
	public RenderType getRenderType(OceanizedPigEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, OceanizedPigEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
