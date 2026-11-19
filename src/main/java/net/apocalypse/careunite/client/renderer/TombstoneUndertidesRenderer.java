
package net.apocalypse.careunite.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.entity.TombstoneUndertidesEntity;
import net.apocalypse.careunite.client.model.animations.TombstoneUndertidesAnimation;
import net.apocalypse.careunite.client.model.ModelTombstoneVariantDisplay;
import net.apocalypse.careunite.client.model.ModelTombstoneUndertides;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class TombstoneUndertidesRenderer extends MobRenderer<TombstoneUndertidesEntity, ModelTombstoneUndertides<TombstoneUndertidesEntity>> {
	public TombstoneUndertidesRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedModel(context.bakeLayer(ModelTombstoneUndertides.LAYER_LOCATION)), 0.5f);
		this.addLayer(new RenderLayer<TombstoneUndertidesEntity, ModelTombstoneUndertides<TombstoneUndertidesEntity>>(this) {

			@Override
			public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, TombstoneUndertidesEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
				ResourceLocation 
LAYER_TEXTURE = new ResourceLocation(getVariantDisplay(entity));
				VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(LAYER_TEXTURE));
				EntityModel model = new ModelTombstoneVariantDisplay(Minecraft.getInstance()
					.getEntityModels()
					.bakeLayer(ModelTombstoneVariantDisplay.LAYER_LOCATION));
				this.getParentModel().copyPropertiesTo(model);
				model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
				model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(TombstoneUndertidesEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite:textures/entities/tombstone_undertides.png");
	}

	private static String getVariantDisplay(TombstoneUndertidesEntity tomb){
		return "caerula_arbor_reunite:textures/entities/tomb_variant_"+tomb.getEntityData().get(TombstoneUndertidesEntity.DATA_Varint)+".png";
	}

	private static final class AnimatedModel extends ModelTombstoneUndertides<TombstoneUndertidesEntity> {
		private final ModelPart root;
		private final HierarchicalModel animator = new HierarchicalModel<TombstoneUndertidesEntity>() {
			@Override
			public ModelPart root() {
				return root;
			}

			@Override
			public void setupAnim(TombstoneUndertidesEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
				this.root().getAllParts().forEach(ModelPart::resetPose);
				this.animate(entity.animationState0, TombstoneUndertidesAnimation.IDLE, ageInTicks, 1f);
			}
		};

		public AnimatedModel(ModelPart root) {
			super(root);
			this.root = root;
		}

		@Override
		public void setupAnim(TombstoneUndertidesEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			animator.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
	}
}
