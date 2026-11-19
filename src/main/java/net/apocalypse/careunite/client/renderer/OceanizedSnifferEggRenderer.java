
package net.apocalypse.careunite.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HierarchicalModel;

import net.apocalypse.careunite.entity.OceanizedSnifferEggEntity;
import net.apocalypse.careunite.client.model.animations.OceanizedSnifferEggAnimation;
import net.apocalypse.careunite.client.model.ModelOceanizedSnifferEgg;

public class OceanizedSnifferEggRenderer extends MobRenderer<OceanizedSnifferEggEntity, ModelOceanizedSnifferEgg<OceanizedSnifferEggEntity>> {
	public OceanizedSnifferEggRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedModel(context.bakeLayer(ModelOceanizedSnifferEgg.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(OceanizedSnifferEggEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite:textures/entities/oceanized_sniffer_egg.png");
	}

	private static final class AnimatedModel extends ModelOceanizedSnifferEgg<OceanizedSnifferEggEntity> {
		private final ModelPart root;
		private final HierarchicalModel animator = new HierarchicalModel<OceanizedSnifferEggEntity>() {
			@Override
			public ModelPart root() {
				return root;
			}

			@Override
			public void setupAnim(OceanizedSnifferEggEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
				this.root().getAllParts().forEach(ModelPart::resetPose);
				this.animate(entity.animationState0, OceanizedSnifferEggAnimation.EggIdle, ageInTicks, 1f);
			}
		};

		public AnimatedModel(ModelPart root) {
			super(root);
			this.root = root;
		}

		@Override
		public void setupAnim(OceanizedSnifferEggEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			animator.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
	}
}
