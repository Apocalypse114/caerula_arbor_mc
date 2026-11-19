package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.TidewayCradleBlockModel;
import net.apocalypse.careunite.block.entity.TidewayCradleTileEntity;

public class TidewayCradleTileRenderer extends GeoBlockRenderer<TidewayCradleTileEntity> {
	public TidewayCradleTileRenderer() {
		super(new TidewayCradleBlockModel());
	}

	@Override
	public RenderType getRenderType(TidewayCradleTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
