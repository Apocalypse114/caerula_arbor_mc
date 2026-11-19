package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.AlchemyBaseEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import net.minecraft.util.Mth;
import software.bernie.geckolib.core.animation.AnimationState;

public class AlchemyBaseModel extends GeoModel<AlchemyBaseEntity> {
	public static final ResourceLocation anim = new ResourceLocation("caerula_arbor_reunite", "animations/alchemy_base.animation.json");
	public static final ResourceLocation model0 = new ResourceLocation("caerula_arbor_reunite", "geo/alchemy_base.geo.json");
	public static final ResourceLocation texture = new ResourceLocation("caerula_arbor_reunite", "textures/entities/alchemy_base.png");

	@Override
	public ResourceLocation getAnimationResource(AlchemyBaseEntity entity) {
		return anim;
	}

	@Override
	public ResourceLocation getModelResource(AlchemyBaseEntity entity) {
		return model0;
	}

	@Override
	public ResourceLocation getTextureResource(AlchemyBaseEntity entity) {
		return texture;
	}

	@Override
	public void setCustomAnimations(AlchemyBaseEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("triangleAxis");
		if (head != null) {
			head.setRotZ(((animatable.tickCount + animationState.getPartialTick()) * 3) % 360 * Mth.DEG_TO_RAD);
		}
	}

}
