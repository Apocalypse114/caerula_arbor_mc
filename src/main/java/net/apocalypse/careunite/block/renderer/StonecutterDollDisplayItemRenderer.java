package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.StonecutterDollDisplayModel;
import net.apocalypse.careunite.block.display.StonecutterDollDisplayItem;

public class StonecutterDollDisplayItemRenderer extends GeoItemRenderer<StonecutterDollDisplayItem> {
	public StonecutterDollDisplayItemRenderer() {
		super(new StonecutterDollDisplayModel());
	}

	@Override
	public RenderType getRenderType(StonecutterDollDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
