package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.OceanizedPillagerEntity;

public class OceanizedPillagerModel extends GeoModel<OceanizedPillagerEntity> {
	@Override
	public ResourceLocation getAnimationResource(OceanizedPillagerEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/oceanized_pillager.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(OceanizedPillagerEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/oceanized_pillager.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(OceanizedPillagerEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(OceanizedPillagerEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

		CoreGeoBone sitBody = getAnimationProcessor().getBone("sitAxisBody");
		CoreGeoBone sitLeg1 = getAnimationProcessor().getBone("sitAxisLegLeft");
		CoreGeoBone sitLeg2 = getAnimationProcessor().getBone("sitAxisLegRight");
		if (sitBody != null && sitLeg1 != null && sitLeg2 != null){
			if (animatable.isPassenger()){
				sitBody.setPosY(-11);
				sitLeg1.setRotX(90 * Mth.DEG_TO_RAD);
				sitLeg1.setRotY(12.5f * Mth.DEG_TO_RAD);
				sitLeg2.setRotX(90 * Mth.DEG_TO_RAD);
				sitLeg2.setRotY(-12.5f * Mth.DEG_TO_RAD);
			} else {
				sitBody.setPosY(0);
				sitLeg1.setRotX(0);
				sitLeg1.setRotY(0);
				sitLeg2.setRotX(0);
				sitLeg2.setRotY(0);
			}
		}
	}
}
