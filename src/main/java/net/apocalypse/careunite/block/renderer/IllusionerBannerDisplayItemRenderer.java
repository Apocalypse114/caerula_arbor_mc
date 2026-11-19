package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.IllusionerBannerDisplayModel;
import net.apocalypse.careunite.block.display.IllusionerBannerDisplayItem;

public class IllusionerBannerDisplayItemRenderer extends GeoItemRenderer<IllusionerBannerDisplayItem> {
	public IllusionerBannerDisplayItemRenderer() {
		super(new IllusionerBannerDisplayModel());
	}

	@Override
	public RenderType getRenderType(IllusionerBannerDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
