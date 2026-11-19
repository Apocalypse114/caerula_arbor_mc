
package net.apocalypse.careunite.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.entity.TombstoneStultiferaSmallEntity;
import net.apocalypse.careunite.client.model.animations.TombstoneStultiferaSmallAnimation;
import net.apocalypse.careunite.client.model.ModelTombstoneVariantDisplay;
import net.apocalypse.careunite.client.model.ModelTombstoneStultiferaSmall;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class TombstoneStultiferaSmallRenderer extends MobRenderer<TombstoneStultiferaSmallEntity, ModelTombstoneStultiferaSmall<TombstoneStultiferaSmallEntity>> {
	public TombstoneStultiferaSmallRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedModel(context.bakeLayer(ModelTombstoneStultiferaSmall.LAYER_LOCATION)), 0.4f);
		this.addLayer(new RenderLayer<TombstoneStultiferaSmallEntity, ModelTombstoneStultiferaSmall<TombstoneStultiferaSmallEntity>>(this) {
			
			@Override
			public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, TombstoneStultiferaSmallEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
				VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(getVariantDisplay(entity)));
				EntityModel model = new ModelTombstoneVariantDisplay(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTombstoneVariantDisplay.LAYER_LOCATION));
				this.getParentModel().copyPropertiesTo(model);
				model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
				model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				model.renderToBuffer(poseStack, vertexConsumer, light, LivingEntityRenderer.getOverlayCoords(entity, 0), 1, 1, 1, 1);
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(TombstoneStultiferaSmallEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite:textures/entities/tombstone_stultifera_small.png");
	}

	private static ResourceLocation getVariantDisplay(TombstoneStultiferaSmallEntity tomb){
		return new ResourceLocation("caerula_arbor_reunite:textures/entities/tomb_variant_"+tomb.getEntityData().get(TombstoneStultiferaSmallEntity.DATA_Varint)+".png");
	}

	private static final class AnimatedModel extends ModelTombstoneStultiferaSmall<TombstoneStultiferaSmallEntity> {
		private final ModelPart root;
		private final HierarchicalModel animator = new HierarchicalModel<TombstoneStultiferaSmallEntity>() {
			@Override
			public ModelPart root() {
				return root;
			}

			@Override
			public void setupAnim(TombstoneStultiferaSmallEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
				this.root().getAllParts().forEach(ModelPart::resetPose);
				this.animate(entity.animationState0, TombstoneStultiferaSmallAnimation.IDLE, ageInTicks, 1f);
			}
		};

		public AnimatedModel(ModelPart root) {
			super(root);
			this.root = root;
		}

		@Override
		public void setupAnim(TombstoneStultiferaSmallEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			animator.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
	}
}
