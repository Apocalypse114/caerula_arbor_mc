package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.ViviparousLilyDisplayModel;
import net.apocalypse.careunite.block.display.ViviparousLilyDisplayItem;

public class ViviparousLilyDisplayItemRenderer extends GeoItemRenderer<ViviparousLilyDisplayItem> {
	public ViviparousLilyDisplayItemRenderer() {
		super(new ViviparousLilyDisplayModel());
	}

	@Override
	public RenderType getRenderType(ViviparousLilyDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
