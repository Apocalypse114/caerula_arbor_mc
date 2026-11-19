package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.OceanWitherSkeletonEntity;

public class OceanWitherSkeletonModel extends GeoModel<OceanWitherSkeletonEntity> {
	@Override
	public ResourceLocation getAnimationResource(OceanWitherSkeletonEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/ocean_wither_skeleton.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(OceanWitherSkeletonEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/ocean_wither_skeleton.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(OceanWitherSkeletonEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(OceanWitherSkeletonEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("Head");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
