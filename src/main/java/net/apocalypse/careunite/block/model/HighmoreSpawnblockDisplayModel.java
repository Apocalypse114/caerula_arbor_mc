package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.HighmoreSpawnblockDisplayItem;

public class HighmoreSpawnblockDisplayModel extends GeoModel<HighmoreSpawnblockDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(HighmoreSpawnblockDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/highmore_spawnblock.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HighmoreSpawnblockDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/highmore_spawnblock.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HighmoreSpawnblockDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/highmore_spawnblock.png");
	}
}
