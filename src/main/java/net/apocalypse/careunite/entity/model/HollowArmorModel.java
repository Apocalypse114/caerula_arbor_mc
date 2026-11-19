package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.HollowArmorEntity;

public class HollowArmorModel extends GeoModel<HollowArmorEntity> {
	public static ResourceLocation anim = new ResourceLocation("caerula_arbor_reunite", "animations/hollow_armor.animation.json");
	public static ResourceLocation model = new ResourceLocation("caerula_arbor_reunite", "geo/hollow_armor.geo.json");


	@Override
	public ResourceLocation getAnimationResource(HollowArmorEntity entity) {
		return anim;
	}

	@Override
	public ResourceLocation getModelResource(HollowArmorEntity entity) {
		return model;
	}

	@Override
	public ResourceLocation getTextureResource(HollowArmorEntity entity) {
		return entity.getTexture();
	}

	@Override
	public void setCustomAnimations(HollowArmorEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("headAxis");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
