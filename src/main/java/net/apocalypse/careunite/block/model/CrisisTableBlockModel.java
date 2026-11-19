package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.CrisisTableTileEntity;

public class CrisisTableBlockModel extends GeoModel<CrisisTableTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(CrisisTableTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "animations/crisis_table.animation.json");
		return new ResourceLocation("caerula_arbor_reunite", "animations/crisis_table.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CrisisTableTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "geo/crisis_table.geo.json");
		return new ResourceLocation("caerula_arbor_reunite", "geo/crisis_table.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CrisisTableTileEntity animatable) {
		final int blockstate = animatable.blockstateNew;
		if (blockstate == 1)
			return new ResourceLocation("caerula_arbor_reunite", "textures/block/crisis_table_dim.png");
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/crisis_table.png");
	}
}
