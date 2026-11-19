package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.TyphoeusEntity;

public class TyphoeusModel extends GeoModel<TyphoeusEntity> {
	@Override
	public ResourceLocation getAnimationResource(TyphoeusEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/inquisitor_archer.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TyphoeusEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/inquisitor_archer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TyphoeusEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(TyphoeusEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("headAxis");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
