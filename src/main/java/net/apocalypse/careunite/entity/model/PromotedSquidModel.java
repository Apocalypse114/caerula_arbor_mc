package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.PromotedSquidEntity;

public class PromotedSquidModel extends GeoModel<PromotedSquidEntity> {
	@Override
	public ResourceLocation getAnimationResource(PromotedSquidEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/promoted_squid.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PromotedSquidEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/promoted_squid.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PromotedSquidEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(PromotedSquidEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("axis");
		if (head != null) {
			if (!animatable.isInWaterOrBubble()){
				head.setPosY(5);
				head.setRotX(-90 * Mth.DEG_TO_RAD);
				head.setRotZ(-180 * Mth.DEG_TO_RAD);
			} else {
				head.setPosY(0);
				head.setRotX(animatable.getUpPitch(animationState.getPartialTick()));
				head.setRotZ(0);
			}
		}
		CoreGeoBone big = getAnimationProcessor().getBone("big"),
				small = getAnimationProcessor().getBone("small");
		if (big != null && small != null){
			big.setHidden(!animatable.shouldShowBig());
			small.setHidden(!animatable.shouldShowSmall());
		}
	}
}
