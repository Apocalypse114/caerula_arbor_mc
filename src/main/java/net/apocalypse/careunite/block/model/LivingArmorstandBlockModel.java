package net.apocalypse.careunite.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.block.entity.LivingArmorstandTileEntity;

public class LivingArmorstandBlockModel extends GeoModel<LivingArmorstandTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(LivingArmorstandTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/living_armorstand.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(LivingArmorstandTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/living_armorstand.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LivingArmorstandTileEntity animatable) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/block/flamarine_armorstand.png");
	}
}
