package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.AegirMortarEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import net.minecraft.util.Mth;

public class AegirMortarModel extends GeoModel<AegirMortarEntity> {
	@Override
	public ResourceLocation getAnimationResource(AegirMortarEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/aegir_mortar.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(AegirMortarEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/aegir_mortar.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AegirMortarEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(AegirMortarEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("rotation");
		if (head != null) {
			head.setRotY(animatable.getGunYRot() * Mth.DEG_TO_RAD);
		}
	}
}
