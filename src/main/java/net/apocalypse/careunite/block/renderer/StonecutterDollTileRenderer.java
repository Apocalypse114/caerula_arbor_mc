package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.StonecutterDollBlockModel;
import net.apocalypse.careunite.block.entity.StonecutterDollTileEntity;

public class StonecutterDollTileRenderer extends GeoBlockRenderer<StonecutterDollTileEntity> {
	public StonecutterDollTileRenderer() {
		super(new StonecutterDollBlockModel());
	}

	@Override
	public RenderType getRenderType(StonecutterDollTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
