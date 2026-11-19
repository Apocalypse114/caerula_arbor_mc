package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.HighmoreSpawningBlockTileEntity;

public class HighmoreSpawningBlockBlockModel extends GeoModel<HighmoreSpawningBlockTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(HighmoreSpawningBlockTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "animations/highmore_spawnblock.animation.json");
		return new ResourceLocation("caerula_arbor_reunite", "animations/highmore_spawnblock.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HighmoreSpawningBlockTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "geo/highmore_spawnblock.geo.json");
		return new ResourceLocation("caerula_arbor_reunite", "geo/highmore_spawnblock.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HighmoreSpawningBlockTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "textures/block/highmore_spawn_withoutlight.png");
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/highmore_spawnblock.png");
	}
}
