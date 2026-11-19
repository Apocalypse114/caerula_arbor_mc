package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.CentrifugerDisplayModel;
import net.apocalypse.careunite.block.display.CentrifugerDisplayItem;

public class CentrifugerDisplayItemRenderer extends GeoItemRenderer<CentrifugerDisplayItem> {
	public CentrifugerDisplayItemRenderer() {
		super(new CentrifugerDisplayModel());
	}

	@Override
	public RenderType getRenderType(CentrifugerDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
