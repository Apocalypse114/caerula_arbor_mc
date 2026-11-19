package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.TrailriteArmorstandTileEntity;

public class TrailriteArmorstandBlockModel extends GeoModel<TrailriteArmorstandTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(TrailriteArmorstandTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/trairite_armorstand.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TrailriteArmorstandTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/trairite_armorstand.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TrailriteArmorstandTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/trailrte_armorstand.png");
	}
}
