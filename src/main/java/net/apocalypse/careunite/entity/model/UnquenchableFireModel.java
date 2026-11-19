package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.UnquenchableFireEntity;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;

public class UnquenchableFireModel extends GeoModel<UnquenchableFireEntity> {
	@Override
	public ResourceLocation getAnimationResource(UnquenchableFireEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/unquenchable_fire.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(UnquenchableFireEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/unquenchable_fire.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(UnquenchableFireEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(UnquenchableFireEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone sss = getAnimationProcessor().getBone("sign");
		CoreGeoBone lll = getAnimationProcessor().getBone("lines");
		if (sss != null) {
			if (animatable.getTime() <= 56){
				sss.setHidden(false);
				lll.setHidden(false);
			} else {
				sss.setHidden(true);
				lll.setHidden(true);
			}
		}

	}


}
