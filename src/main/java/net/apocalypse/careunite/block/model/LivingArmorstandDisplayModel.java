package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.LivingArmorstandDisplayItem;

public class LivingArmorstandDisplayModel extends GeoModel<LivingArmorstandDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(LivingArmorstandDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/living_armorstand.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(LivingArmorstandDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/living_armorstand.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LivingArmorstandDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/flamarine_armorstand.png");
	}
}
