package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.SwarmcallerDollBlockModel;
import net.apocalypse.careunite.block.entity.SwarmcallerDollTileEntity;

public class SwarmcallerDollTileRenderer extends GeoBlockRenderer<SwarmcallerDollTileEntity> {
	public SwarmcallerDollTileRenderer() {
		super(new SwarmcallerDollBlockModel());
	}

	@Override
	public RenderType getRenderType(SwarmcallerDollTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
