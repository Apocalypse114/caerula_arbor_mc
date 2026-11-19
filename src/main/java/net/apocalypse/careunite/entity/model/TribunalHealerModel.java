package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.TribunalHealerEntity;

public class TribunalHealerModel extends GeoModel<TribunalHealerEntity> {
	@Override
	public ResourceLocation getAnimationResource(TribunalHealerEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/tribunal_healer.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TribunalHealerEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/tribunal_healer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TribunalHealerEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(TribunalHealerEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("Head");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
