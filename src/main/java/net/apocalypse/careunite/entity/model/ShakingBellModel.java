package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.ShakingBellEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import net.minecraft.util.Mth;

public class ShakingBellModel extends GeoModel<ShakingBellEntity> {
	@Override
	public ResourceLocation getAnimationResource(ShakingBellEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/shaking_bell.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ShakingBellEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/shaking_bell.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ShakingBellEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(ShakingBellEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone h1 = getAnimationProcessor().getBone("helicopter1");
		CoreGeoBone h2 = getAnimationProcessor().getBone("helicopter2");
		if (h1 != null) {
			float t = animatable.tickCount + animationState.getPartialTick();
			h1.setRotY(t * 15f * Mth.DEG_TO_RAD);
			h2.setRotY(-1 * t * 18f * Mth.DEG_TO_RAD);
		}
	}

}
