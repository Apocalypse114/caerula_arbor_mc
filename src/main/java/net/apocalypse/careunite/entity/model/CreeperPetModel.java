package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.CreeperPetEntity;

public class CreeperPetModel extends GeoModel<CreeperPetEntity> {
	@Override
	public ResourceLocation getAnimationResource(CreeperPetEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/explosivefish.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CreeperPetEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/explosivefish.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CreeperPetEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(CreeperPetEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("head");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
			head.setScaleX(1.125f);
			head.setScaleY(1.125f);
			head.setScaleZ(1.125f);
		}

	}
}
