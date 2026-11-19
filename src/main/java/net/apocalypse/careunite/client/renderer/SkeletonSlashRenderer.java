
package net.apocalypse.careunite.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.entity.SkeletonSlashEntity;
import net.apocalypse.careunite.client.model.Modelwither_slash;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class SkeletonSlashRenderer extends MobRenderer<SkeletonSlashEntity, Modelwither_slash<SkeletonSlashEntity>> {
	public SkeletonSlashRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelwither_slash<SkeletonSlashEntity>(context.bakeLayer(Modelwither_slash.LAYER_LOCATION)), 0.5f);
		this.addLayer(new RenderLayer<SkeletonSlashEntity, Modelwither_slash<SkeletonSlashEntity>>(this) {
			final ResourceLocation LAYER_TEXTURE = new ResourceLocation("caerula_arbor_reunite:textures/entities/skeleton_slash.png");

			@Override
			public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, SkeletonSlashEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
				VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(LAYER_TEXTURE));
				EntityModel model = new Modelwither_slash(Minecraft.getInstance().getEntityModels().bakeLayer(Modelwither_slash.LAYER_LOCATION));
				this.getParentModel().copyPropertiesTo(model);
				model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
				model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(SkeletonSlashEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite:textures/entities/skeleton_slash.png");
	}
}
