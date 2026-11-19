package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.TrailriteArmorstandDisplayItem;

public class TrailriteArmorstandDisplayModel extends GeoModel<TrailriteArmorstandDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(TrailriteArmorstandDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/trairite_armorstand.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TrailriteArmorstandDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/trairite_armorstand.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TrailriteArmorstandDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/trailrte_armorstand.png");
	}
}
