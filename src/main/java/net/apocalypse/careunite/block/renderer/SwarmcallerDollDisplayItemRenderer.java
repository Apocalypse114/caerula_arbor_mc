package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.SwarmcallerDollDisplayModel;
import net.apocalypse.careunite.block.display.SwarmcallerDollDisplayItem;

public class SwarmcallerDollDisplayItemRenderer extends GeoItemRenderer<SwarmcallerDollDisplayItem> {
	public SwarmcallerDollDisplayItemRenderer() {
		super(new SwarmcallerDollDisplayModel());
	}

	@Override
	public RenderType getRenderType(SwarmcallerDollDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
