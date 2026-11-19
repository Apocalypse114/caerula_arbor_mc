package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.OceanizedBeeEntity;
import software.bernie.geckolib.core.animation.AnimationState;
import net.minecraft.util.Mth;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;

public class OceanizedBeeModel extends GeoModel<OceanizedBeeEntity> {
	@Override
	public ResourceLocation getAnimationResource(OceanizedBeeEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/oceanized_bee.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(OceanizedBeeEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/oceanized_bee.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(OceanizedBeeEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(OceanizedBeeEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("wings");
		if (head != null) {
			float t = animatable.tickCount + animationState.getPartialTick();
			head.setRotY(t * (-18) * Mth.DEG_TO_RAD);
		}

	}


}
