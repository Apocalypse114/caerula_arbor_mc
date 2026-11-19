
package net.apocalypse.careunite.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HierarchicalModel;

import net.apocalypse.careunite.entity.TombstoneUndertideSmallEntity;
import net.apocalypse.careunite.client.model.animations.TombstoneUndertidesAnimation;
import net.apocalypse.careunite.client.model.ModelTombstoneUndertidesSmall;

public class TombstoneUndertideSmallRenderer extends MobRenderer<TombstoneUndertideSmallEntity, ModelTombstoneUndertidesSmall<TombstoneUndertideSmallEntity>> {
	public TombstoneUndertideSmallRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedModel(context.bakeLayer(ModelTombstoneUndertidesSmall.LAYER_LOCATION)), 0.4f);
	}

	@Override
	public ResourceLocation getTextureLocation(TombstoneUndertideSmallEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite:textures/entities/tombstone_undertides_small.png");
	}

	private static final class AnimatedModel extends ModelTombstoneUndertidesSmall<TombstoneUndertideSmallEntity> {
		private final ModelPart root;
		private final HierarchicalModel animator = new HierarchicalModel<TombstoneUndertideSmallEntity>() {
			@Override
			public ModelPart root() {
				return root;
			}

			@Override
			public void setupAnim(TombstoneUndertideSmallEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
				this.root().getAllParts().forEach(ModelPart::resetPose);
				this.animate(entity.animationState0, TombstoneUndertidesAnimation.IDLE, ageInTicks, 1f);
			}
		};

		public AnimatedModel(ModelPart root) {
			super(root);
			this.root = root;
		}

		@Override
		public void setupAnim(TombstoneUndertideSmallEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			animator.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
	}
}
