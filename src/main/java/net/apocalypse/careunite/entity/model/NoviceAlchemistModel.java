package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.NoviceAlchemistEntity;
import net.minecraft.world.entity.animal.Animal;

public class NoviceAlchemistModel extends GeoModel<NoviceAlchemistEntity> {
	public static final ResourceLocation anim = new ResourceLocation("caerula_arbor_reunite", "animations/novice_alchemist.animation.json");
	public static final ResourceLocation model = new ResourceLocation("caerula_arbor_reunite", "geo/novice_alchemist.geo.json");
		
	@Override
	public ResourceLocation getAnimationResource(NoviceAlchemistEntity entity) {
		return anim;
	}

	@Override
	public ResourceLocation getModelResource(NoviceAlchemistEntity entity) {
		return model;
	}

	@Override
	public ResourceLocation getTextureResource(NoviceAlchemistEntity entity) {
		return entity.getTextRes();
	}

	@Override
	public void setCustomAnimations(NoviceAlchemistEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("headAxis");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
