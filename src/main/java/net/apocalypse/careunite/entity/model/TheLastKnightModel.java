package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.TheLastKnightEntity;

public class TheLastKnightModel extends GeoModel<TheLastKnightEntity> {
	@Override
	public ResourceLocation getAnimationResource(TheLastKnightEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/last_knight.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TheLastKnightEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/last_knight.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TheLastKnightEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(TheLastKnightEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("Head");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
