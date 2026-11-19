package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.AbandonedSulptureDisplayModel;
import net.apocalypse.careunite.block.display.AbandonedSulptureDisplayItem;

public class AbandonedSulptureDisplayItemRenderer extends GeoItemRenderer<AbandonedSulptureDisplayItem> {
	public AbandonedSulptureDisplayItemRenderer() {
		super(new AbandonedSulptureDisplayModel());
	}

	@Override
	public RenderType getRenderType(AbandonedSulptureDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
