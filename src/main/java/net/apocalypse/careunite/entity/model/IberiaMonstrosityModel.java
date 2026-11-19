package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.IberiaMonstrosityEntity;
import software.bernie.geckolib.core.animation.AnimationState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;

public class IberiaMonstrosityModel extends GeoModel<IberiaMonstrosityEntity> {
	@Override
	public ResourceLocation getAnimationResource(IberiaMonstrosityEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/iberia_monstrosity.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(IberiaMonstrosityEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/iberia_monstrosity.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IberiaMonstrosityEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

//	@Override
//	public void setCustomAnimations(IberiaMonstrosityEntity animatable, long instanceId, AnimationState animationState) {
//		CoreGeoBone gun = getAnimationProcessor().getBone("gun_tunnel");
//		if (gun != null) {
//			animatable.setGunPos(gun.getPivotX(), gun.getPivotY(), gun.getPivotZ());
//		}
//	}
}
