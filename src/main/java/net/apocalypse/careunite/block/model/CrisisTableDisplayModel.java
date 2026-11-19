package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.CrisisTableDisplayItem;

public class CrisisTableDisplayModel extends GeoModel<CrisisTableDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(CrisisTableDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/crisis_table.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CrisisTableDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/crisis_table.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CrisisTableDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/crisis_table.png");
	}
}
