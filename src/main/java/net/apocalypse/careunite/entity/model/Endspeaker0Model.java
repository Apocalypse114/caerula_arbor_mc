package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.Endspeaker0Entity;

public class Endspeaker0Model extends GeoModel<Endspeaker0Entity> {
	@Override
	public ResourceLocation getAnimationResource(Endspeaker0Entity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/endspeaker_0.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(Endspeaker0Entity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/endspeaker_0.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Endspeaker0Entity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(Endspeaker0Entity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("head");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
