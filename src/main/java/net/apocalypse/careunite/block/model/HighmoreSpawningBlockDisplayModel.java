package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.HighmoreSpawningBlockDisplayItem;

public class HighmoreSpawningBlockDisplayModel extends GeoModel<HighmoreSpawningBlockDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(HighmoreSpawningBlockDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/highmore_spawnblock.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HighmoreSpawningBlockDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/highmore_spawnblock.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HighmoreSpawningBlockDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/highmore_spawnblock.png");
	}
}
