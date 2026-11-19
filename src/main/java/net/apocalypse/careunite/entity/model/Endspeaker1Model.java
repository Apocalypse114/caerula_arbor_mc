package net.apocalypse.careunite.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.Endspeaker1Entity;

public class Endspeaker1Model extends GeoModel<Endspeaker1Entity> {
	@Override
	public ResourceLocation getAnimationResource(Endspeaker1Entity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "animations/endspeaker_1.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(Endspeaker1Entity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "geo/endspeaker_1.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Endspeaker1Entity entity) {
		return new ResourceLocation("caerula_arbor_reunite", "textures/entities/" + entity.getTexture() + ".png");
	}

}
