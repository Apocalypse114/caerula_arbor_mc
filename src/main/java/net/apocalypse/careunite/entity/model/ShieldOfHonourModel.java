package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.ShieldOfHonourEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

public class ShieldOfHonourModel extends GeoModel<ShieldOfHonourEntity> {
	@Override
	public ResourceLocation getAnimationResource(ShieldOfHonourEntity entity) {
		return an;
	}

	public static final ResourceLocation mo = new ResourceLocation("caerula_arbor_reunite", "geo/shield_of_honour.geo.json");
	public static final ResourceLocation an = new ResourceLocation("caerula_arbor_reunite", "animations/shield_of_honour.animation.json");
	public static final ResourceLocation tx = new ResourceLocation("caerula_arbor_reunite", "textures/entities/shield_of_honour.png");

	@Override
	public ResourceLocation getModelResource(ShieldOfHonourEntity entity) {
		return mo;
	}

	@Override
	public ResourceLocation getTextureResource(ShieldOfHonourEntity entity) {
		return tx;
	}

	@Override
	public void setCustomAnimations(ShieldOfHonourEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone s1 = getAnimationProcessor().getBone("surface");
		CoreGeoBone s2 = getAnimationProcessor().getBone("surface_mirror");
		CoreGeoBone c1 = getAnimationProcessor().getBone("center");
		CoreGeoBone c2 = getAnimationProcessor().getBone("center2");
		if (s1 != null) {
			s1.setHidden(animatable.reversed);
			s2.setHidden(!animatable.reversed);
			c1.setHidden(animatable.reversed);
			c2.setHidden(!animatable.reversed);
		}

	}

}
