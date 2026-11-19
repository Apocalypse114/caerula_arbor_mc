package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.SliderBallHugeEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.Mth;

public class SliderBallHugeModel extends GeoModel<SliderBallHugeEntity> {
	@Override
	public ResourceLocation getAnimationResource(SliderBallHugeEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/slider_ball.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SliderBallHugeEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/slider_ball.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SliderBallHugeEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(SliderBallHugeEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("stem");
		if (head != null) {
			Vec3 v = animatable.getDeltaMovement();
			float dx = 1 + (float)Mth.clamp(v.x * 0.25, -0.125, 0.125);
			float dy = 1 + (float)Mth.clamp(v.y * 0.25, -0.125, 0.125);
			float dz = 1 + (float)Mth.clamp(v.z * 0.25, -0.125, 0.125);
			head.setScaleX(dx);
			head.setScaleY(dy);
			head.setScaleZ(dz);
			float rotY = animatable.getBallRotation();
			head.setRotY(rotY * Mth.DEG_TO_RAD);
		}
	}

}
