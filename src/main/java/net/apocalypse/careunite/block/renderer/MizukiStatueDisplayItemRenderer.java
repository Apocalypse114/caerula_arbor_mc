package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.MizukiStatueDisplayModel;
import net.apocalypse.careunite.block.display.MizukiStatueDisplayItem;

public class MizukiStatueDisplayItemRenderer extends GeoItemRenderer<MizukiStatueDisplayItem> {
	public MizukiStatueDisplayItemRenderer() {
		super(new MizukiStatueDisplayModel());
	}

	@Override
	public RenderType getRenderType(MizukiStatueDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
