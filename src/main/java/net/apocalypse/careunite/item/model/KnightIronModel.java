package net.apocalypse.careunite.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.item.KnightIronItem;

public class KnightIronModel extends GeoModel<KnightIronItem> {
	@Override
	public ResourceLocation getAnimationResource(KnightIronItem object) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/knight_armor.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(KnightIronItem object) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/knight_armor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(KnightIronItem object) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/item/knight_armor.png");
	}
}
