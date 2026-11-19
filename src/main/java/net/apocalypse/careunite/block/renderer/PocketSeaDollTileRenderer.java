package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.PocketSeaDollBlockModel;
import net.apocalypse.careunite.block.entity.PocketSeaDollTileEntity;

public class PocketSeaDollTileRenderer extends GeoBlockRenderer<PocketSeaDollTileEntity> {
	public PocketSeaDollTileRenderer() {
		super(new PocketSeaDollBlockModel());
	}

	@Override
	public RenderType getRenderType(PocketSeaDollTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
