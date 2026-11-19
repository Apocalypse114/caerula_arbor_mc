package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.OceanizedPiglinEntity;

public class OceanizedPiglinModel extends GeoModel<OceanizedPiglinEntity> {
	@Override
	public ResourceLocation getAnimationResource(OceanizedPiglinEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/oceanized_piglin.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(OceanizedPiglinEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/oceanized_piglin.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(OceanizedPiglinEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(OceanizedPiglinEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("head");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

		CoreGeoBone sitBody = getAnimationProcessor().getBone("sitAxisBody");
		CoreGeoBone sitLeg1 = getAnimationProcessor().getBone("sitAxisLeg");
		CoreGeoBone sitLeg2 = getAnimationProcessor().getBone("sitAxisLeg1");
		if (sitBody != null && sitLeg1 != null && sitLeg2 != null){
			if (animatable.isPassenger()){
				sitBody.setPosY(-11);
				sitLeg1.setRotX(-82.5f * Mth.DEG_TO_RAD);
				sitLeg1.setRotY(17.5f * Mth.DEG_TO_RAD);
				sitLeg1.setPosY(1);sitLeg1.setPosZ(-5);
				sitLeg2.setRotX(-82.5f * Mth.DEG_TO_RAD);
				sitLeg2.setRotY(-17.5f * Mth.DEG_TO_RAD);
				sitLeg2.setPosY(1);sitLeg2.setPosZ(-5);
			} else {
				sitBody.setPosY(0);
				sitLeg1.setRotX(0);sitLeg1.setRotY(0);sitLeg1.setPosY(0);sitLeg1.setPosZ(0);
				sitLeg2.setRotX(0);sitLeg2.setRotY(0);sitLeg2.setPosY(0);sitLeg2.setPosZ(0);
			}
		}
	}
}
