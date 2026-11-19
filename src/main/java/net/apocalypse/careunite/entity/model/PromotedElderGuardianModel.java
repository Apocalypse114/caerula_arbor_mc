package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.PromotedElderGuardianEntity;

public class PromotedElderGuardianModel extends GeoModel<PromotedElderGuardianEntity> {
	@Override
	public ResourceLocation getAnimationResource(PromotedElderGuardianEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/promoted_elder_guardian.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PromotedElderGuardianEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/promoted_elder_guardian.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PromotedElderGuardianEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(PromotedElderGuardianEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("eye");
		CoreGeoBone headTotal = getAnimationProcessor().getBone("headAxis");
		if (head != null && headTotal != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			float yaw = entityData.netHeadYaw() * Mth.DEG_TO_RAD, pitch = entityData.headPitch() * Mth.DEG_TO_RAD;
			head.setPosX(Mth.clamp(8 * Mth.sin(yaw) / Mth.cos(yaw), -2, 2));
			headTotal.setRotX(pitch);
		}
		float t = animatable.tickCount + animationState.getPartialTick();
		CoreGeoBone rim = getAnimationProcessor().getBone("rimsAxis");
		if (rim != null){
			rim.setRotZ(t * 4 * Mth.DEG_TO_RAD);
			rim.setRotX(22.5f * Mth.DEG_TO_RAD * Mth.sin(t * 3 * Mth.DEG_TO_RAD));
		}
		CoreGeoBone ring = getAnimationProcessor().getBone("ringAxis");
		if (ring != null){
			ring.setRotZ(-1 * t * 3.5f * Mth.DEG_TO_RAD);
			ring.setRotY(22.5f * Mth.DEG_TO_RAD * Mth.sin(t * 4 * Mth.DEG_TO_RAD));
		}
		for (int i = 0; i<8; i++){
			setSmallEye(i, animatable.shouldEyeBlink(i));
		}
	}

	private void setSmallEye(int index, boolean open){
		int c = index / 2;
		String pre = index % 2 == 0? "a": "b";
		CoreGeoBone eye = getAnimationProcessor().getBone("eyelid"+pre+c);
		if (eye != null){
			eye.setScaleX(open ? 1: 0);
			eye.setScaleY(open ? 1: 0);
			eye.setScaleZ(open ? 1: 0);
		}
	}

}
