package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.LumenEntity;

public class LumenModel extends GeoModel<LumenEntity> {
	@Override
	public ResourceLocation getAnimationResource(LumenEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/lumen.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(LumenEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/lumen.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LumenEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(LumenEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("HeadAxis");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

		CoreGeoBone eyeLid = getAnimationProcessor().getBone("EyeLids");
		CoreGeoBone eyeBrow = getAnimationProcessor().getBone("EyeBrows");
		if (eyeLid != null && eyeBrow != null){
			if(animatable.shouldBlink()){
				eyeLid.setScaleY(1);
				eyeBrow.setPosY(-2);
			} else {
				eyeLid.setScaleY(0);
				eyeBrow.setPosY(0);
			}
		}
	}
}
