package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.ChestmegaSpawnerDisplayItem;

public class ChestmegaSpawnerDisplayModel extends GeoModel<ChestmegaSpawnerDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(ChestmegaSpawnerDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/chestmega_block.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ChestmegaSpawnerDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/chestmega_block.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ChestmegaSpawnerDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/megachest.png");
	}
}
