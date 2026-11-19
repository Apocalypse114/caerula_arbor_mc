package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.IshmaelEntity;

public class IshmaelModel extends GeoModel<IshmaelEntity> {
	@Override
	public ResourceLocation getAnimationResource(IshmaelEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/ishmael.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(IshmaelEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/ishmael.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IshmaelEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(IshmaelEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("HeadAnim");
		CoreGeoBone hair = getAnimationProcessor().getBone("HairAxis");
		EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
		float headPitch = entityData.headPitch() * Mth.DEG_TO_RAD;
		float headYaw = entityData.netHeadYaw() * Mth.DEG_TO_RAD;
		if (head != null) {
			head.setRotX(headPitch);
			head.setRotY(headYaw);
		}

		if (hair != null){
			hair.setRotX(-1 * headPitch);
		}
		CoreGeoBone eyeLid = getAnimationProcessor().getBone("EyeLid");
		CoreGeoBone eyeBrow = getAnimationProcessor().getBone("EyeBrow");
		if (eyeLid != null && eyeBrow != null){
			if(animatable.shouldBlink()){
				eyeLid.setScaleY(1);
				eyeBrow.setPosY(-2);
			} else {
				eyeLid.setScaleY(0);
				eyeBrow.setPosY(0);
			}
		}
		CoreGeoBone sp1 = getAnimationProcessor().getBone("spark1");
		CoreGeoBone sp2 = getAnimationProcessor().getBone("spark2");
		CoreGeoBone sp3 = getAnimationProcessor().getBone("spark3");
		if (sp1 != null && sp2 != null && sp3 != null){
			boolean s1 = false, s2 = false, s3 = false;
			int num = animatable.charge();
			if(num > 0) s1 = true;
			if(num > 1) s2 = true;
			if(num > 2) s3 = true;
			sp1.setHidden(!s1);
			sp2.setHidden(!s2);
			sp3.setHidden(!s3);
		}
		float t = animatable.tickCount + animationState.getPartialTick();
		CoreGeoBone core = getAnimationProcessor().getBone("crystals");
		CoreGeoBone sps = getAnimationProcessor().getBone("sparks");
		if(core != null){
			core.setRotY(t * 1.5f * Mth.DEG_TO_RAD);
		}
		if(sps != null){
			sps.setRotY(t * (-2) * Mth.DEG_TO_RAD);
		}
	}
}
