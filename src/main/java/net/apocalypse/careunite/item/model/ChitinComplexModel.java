package net.apocalypse.careunite.item.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.item.ChitinComplexItem;

public class ChitinComplexModel extends GeoModel<ChitinComplexItem> {
	public static final ResourceLocation GEO = new ResourceLocation("caerula_arbor_reunite", "geo/complex_chitin.geo.json");
	public static final ResourceLocation ANIM = new ResourceLocation("caerula_arbor_reunite", "animations/complex_chitin.animation.json");
	public static final ResourceLocation TEXTURE = new ResourceLocation("caerula_arbor_reunite", "textures/item/complex_chitin_armor.png");
	
	@Override
	public ResourceLocation getAnimationResource(ChitinComplexItem object) {
		return ANIM;
	}

	@Override
	public ResourceLocation getModelResource(ChitinComplexItem object) {
		return GEO;
	}

	@Override
	public ResourceLocation getTextureResource(ChitinComplexItem object) {
		return TEXTURE;
	}
}
