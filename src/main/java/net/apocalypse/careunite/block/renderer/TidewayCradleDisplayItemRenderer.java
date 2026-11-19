package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.TidewayCradleDisplayModel;
import net.apocalypse.careunite.block.display.TidewayCradleDisplayItem;

public class TidewayCradleDisplayItemRenderer extends GeoItemRenderer<TidewayCradleDisplayItem> {
	public TidewayCradleDisplayItemRenderer() {
		super(new TidewayCradleDisplayModel());
	}

	@Override
	public RenderType getRenderType(TidewayCradleDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
