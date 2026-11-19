package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.Endspeaker2Entity;

public class Endspeaker2Model extends GeoModel<Endspeaker2Entity> {
	@Override
	public ResourceLocation getAnimationResource(Endspeaker2Entity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/endspeaker_2.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(Endspeaker2Entity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/endspeaker_2.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Endspeaker2Entity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
