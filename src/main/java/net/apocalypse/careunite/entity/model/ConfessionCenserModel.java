package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.ConfessionCenserEntity;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;

public class ConfessionCenserModel extends GeoModel<ConfessionCenserEntity> {
	public static final ResourceLocation anim = new ResourceLocation("caerula_arbor_reunite", "animations/confession_censer.animation.json");
	public static final ResourceLocation model = new ResourceLocation("caerula_arbor_reunite", "geo/confession_censer.geo.json");

	@Override
	public ResourceLocation getAnimationResource(ConfessionCenserEntity entity) {
		return anim;
	}

	@Override
	public ResourceLocation getModelResource(ConfessionCenserEntity entity) {
		return model;
	}

	@Override
	public ResourceLocation getTextureResource(ConfessionCenserEntity entity) {
		return entity.getTextureRes();
	}

	@Override
	public void setCustomAnimations(ConfessionCenserEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone c1 = getAnimationProcessor().getBone("chain1");
		CoreGeoBone c2 = getAnimationProcessor().getBone("chain2");
		if (c1 != null) {
			c1.setHidden(animatable.reversed);
			c2.setHidden(!animatable.reversed);
		}
	}

}
