package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.ChestmegaSpawnerTileEntity;

public class ChestmegaSpawnerBlockModel extends GeoModel<ChestmegaSpawnerTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(ChestmegaSpawnerTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "animations/chestmega_block.animation.json");
		return new ResourceLocation("caerula_arbor_reunite", "animations/chestmega_block.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ChestmegaSpawnerTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "geo/chestmega_block.geo.json");
		return new ResourceLocation("caerula_arbor_reunite", "geo/chestmega_block.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ChestmegaSpawnerTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "textures/block/megachest.png");
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/megachest.png");
	}
}
