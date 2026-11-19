package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.PocketSeaDollDisplayModel;
import net.apocalypse.careunite.block.display.PocketSeaDollDisplayItem;

public class PocketSeaDollDisplayItemRenderer extends GeoItemRenderer<PocketSeaDollDisplayItem> {
	public PocketSeaDollDisplayItemRenderer() {
		super(new PocketSeaDollDisplayModel());
	}

	@Override
	public RenderType getRenderType(PocketSeaDollDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
