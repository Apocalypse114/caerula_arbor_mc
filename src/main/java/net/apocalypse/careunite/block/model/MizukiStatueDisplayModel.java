package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.display.MizukiStatueDisplayItem;

public class MizukiStatueDisplayModel extends GeoModel<MizukiStatueDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(MizukiStatueDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/mizuki.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MizukiStatueDisplayItem animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/mizuki.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MizukiStatueDisplayItem entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/mizuki.png");
	}
}
