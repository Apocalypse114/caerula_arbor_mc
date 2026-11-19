package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.HighmoreSpawningBlockDisplayModel;
import net.apocalypse.careunite.block.display.HighmoreSpawningBlockDisplayItem;

public class HighmoreSpawningBlockDisplayItemRenderer extends GeoItemRenderer<HighmoreSpawningBlockDisplayItem> {
	public HighmoreSpawningBlockDisplayItemRenderer() {
		super(new HighmoreSpawningBlockDisplayModel());
	}

	@Override
	public RenderType getRenderType(HighmoreSpawningBlockDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
