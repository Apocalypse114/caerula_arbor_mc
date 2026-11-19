
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

import net.apocalypse.careunite.entity.TombstoneStultiferaEntity;
import net.apocalypse.careunite.client.model.animations.TombstoneStultiferaSmallAnimation;
import net.apocalypse.careunite.client.model.ModelTombstoneVariantDisplay;
import net.apocalypse.careunite.client.model.ModelTombstoneStultifera;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class TombstoneStultiferaRenderer extends MobRenderer<TombstoneStultiferaEntity, ModelTombstoneStultifera<TombstoneStultiferaEntity>> {
	public TombstoneStultiferaRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedModel(context.bakeLayer(ModelTombstoneStultifera.LAYER_LOCATION)), 0.5f);
		this.addLayer(new RenderLayer<TombstoneStultiferaEntity, ModelTombstoneStultifera<TombstoneStultiferaEntity>>(this) {

			@Override
			public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, TombstoneStultiferaEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
				VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(getVariantDisplay(entity)));
				EntityModel model = new ModelTombstoneVariantDisplay(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTombstoneVariantDisplay.LAYER_LOCATION));
				this.getParentModel().copyPropertiesTo(model);
				model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
				model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(TombstoneStultiferaEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite:textures/entities/tombstone_stultifera.png");
	}

	private static ResourceLocation getVariantDisplay(TombstoneStultiferaEntity tomb){
		return new ResourceLocation("caerula_arbor_reunite:textures/entities/tomb_variant_"+tomb.getEntityData().get(TombstoneStultiferaEntity.DATA_Varint)+".png");
	}

	private static final class AnimatedModel extends ModelTombstoneStultifera<TombstoneStultiferaEntity> {
		private final ModelPart root;
		private final HierarchicalModel animator = new HierarchicalModel<TombstoneStultiferaEntity>() {
			@Override
			public ModelPart root() {
				return root;
			}

			@Override
			public void setupAnim(TombstoneStultiferaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
				this.root().getAllParts().forEach(ModelPart::resetPose);
				this.animate(entity.animationState0, TombstoneStultiferaSmallAnimation.IDLE, ageInTicks, 1f);
			}
		};

		public AnimatedModel(ModelPart root) {
			super(root);
			this.root = root;
		}

		@Override
		public void setupAnim(TombstoneStultiferaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			animator.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
	}
}
