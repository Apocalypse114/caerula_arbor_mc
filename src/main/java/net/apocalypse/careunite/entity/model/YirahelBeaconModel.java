package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.YirahelBeaconEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

public class YirahelBeaconModel extends GeoModel<YirahelBeaconEntity> {
	@Override
	public ResourceLocation getAnimationResource(YirahelBeaconEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/yirahel_beacon.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(YirahelBeaconEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/yirahel_beacon.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(YirahelBeaconEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(YirahelBeaconEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone laser = getAnimationProcessor().getBone("laser");
		CoreGeoBone bullet = getAnimationProcessor().getBone("bullet");
		CoreGeoBone b1 = getAnimationProcessor().getBone("base1");
		CoreGeoBone b2 = getAnimationProcessor().getBone("base2");
		CoreGeoBone b3 = getAnimationProcessor().getBone("base3");
		CoreGeoBone b4 = getAnimationProcessor().getBone("base4");
		if (laser != null) {
			int b = animatable.getBshd();
			laser.setHidden(!animatable.renderLaser());
			bullet.setHidden(!animatable.renderBullet());
			b1.setHidden(b < 1);
			b2.setHidden(b < 2);
			b3.setHidden(b < 3);
			b4.setHidden(b < 4);
		}
	}

}
