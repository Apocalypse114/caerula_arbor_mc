package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.HugeLilyDisplayModel;
import net.apocalypse.careunite.block.display.HugeLilyDisplayItem;

public class HugeLilyDisplayItemRenderer extends GeoItemRenderer<HugeLilyDisplayItem> {
	public HugeLilyDisplayItemRenderer() {
		super(new HugeLilyDisplayModel());
	}

	@Override
	public RenderType getRenderType(HugeLilyDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
