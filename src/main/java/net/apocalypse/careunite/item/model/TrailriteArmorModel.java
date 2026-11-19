package net.apocalypse.careunite.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.item.TrailriteArmorItem;

public class TrailriteArmorModel extends GeoModel<TrailriteArmorItem> {
	public static final ResourceLocation ANIM = new ResourceLocation("caerula_arbor_reunite", "animations/trairite_armor.animation.json");
	public static final ResourceLocation GEO = new ResourceLocation("caerula_arbor_reunite", "geo/trairite_armor.geo.json");
	public static final ResourceLocation TEXTURE = new ResourceLocation("caerula_arbor_reunite", "textures/item/trailrite_armor.png");
	@Override
	public ResourceLocation getAnimationResource(TrailriteArmorItem object) {
		return ANIM;
	}

	@Override
	public ResourceLocation getModelResource(TrailriteArmorItem object) {
		return GEO;
	}

	@Override
	public ResourceLocation getTextureResource(TrailriteArmorItem object) {
		return TEXTURE;
	}
}
