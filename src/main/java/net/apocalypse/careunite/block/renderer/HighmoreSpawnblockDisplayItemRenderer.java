package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.HighmoreSpawnblockDisplayModel;
import net.apocalypse.careunite.block.display.HighmoreSpawnblockDisplayItem;

public class HighmoreSpawnblockDisplayItemRenderer extends GeoItemRenderer<HighmoreSpawnblockDisplayItem> {
	public HighmoreSpawnblockDisplayItemRenderer() {
		super(new HighmoreSpawnblockDisplayModel());
	}

	@Override
	public RenderType getRenderType(HighmoreSpawnblockDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
