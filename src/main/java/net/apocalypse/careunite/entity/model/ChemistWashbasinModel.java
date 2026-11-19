package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.ChemistWashbasinEntity;

public class ChemistWashbasinModel extends GeoModel<ChemistWashbasinEntity> {
	private static final ResourceLocation anim = new ResourceLocation("caerula_arbor_reunite", "animations/chemist_washbasin.animation.json");;
	private static final ResourceLocation model = new ResourceLocation("caerula_arbor_reunite", "geo/chemist_washbasin.geo.json");

	@Override
	public ResourceLocation getAnimationResource(ChemistWashbasinEntity entity) {
		return anim;
	}

	@Override
	public ResourceLocation getModelResource(ChemistWashbasinEntity entity) {
		return model;
	}

	@Override
	public ResourceLocation getTextureResource(ChemistWashbasinEntity entity) {
		return entity.getTextureRes();
	}

}
