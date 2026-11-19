package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.PumpkinCarriageEntity;

public class PumpkinCarriageModel extends GeoModel<PumpkinCarriageEntity> {
	public static final ResourceLocation anim = new ResourceLocation("caerula_arbor_reunite", "animations/pumpkin_carriage.animation.json");
	public static final ResourceLocation model = new ResourceLocation("caerula_arbor_reunite", "geo/pumpkin_carriage.geo.json");

	@Override
	public ResourceLocation getAnimationResource(PumpkinCarriageEntity entity) {
		return anim;
	}

	@Override
	public ResourceLocation getModelResource(PumpkinCarriageEntity entity) {
		return model;
	}

	@Override
	public ResourceLocation getTextureResource(PumpkinCarriageEntity entity) {
		return entity.textureLoc();
	}

	@Override
	public void setCustomAnimations(PumpkinCarriageEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("headAxis");
		if (head != null && !animatable.isStunned()) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
