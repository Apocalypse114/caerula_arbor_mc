package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.ChestmegaSpawnerDisplayModel;
import net.apocalypse.careunite.block.display.ChestmegaSpawnerDisplayItem;

public class ChestmegaSpawnerDisplayItemRenderer extends GeoItemRenderer<ChestmegaSpawnerDisplayItem> {
	public ChestmegaSpawnerDisplayItemRenderer() {
		super(new ChestmegaSpawnerDisplayModel());
	}

	@Override
	public RenderType getRenderType(ChestmegaSpawnerDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
