package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.CentrifugerBlockModel;
import net.apocalypse.careunite.block.entity.CentrifugerTileEntity;

public class CentrifugerTileRenderer extends GeoBlockRenderer<CentrifugerTileEntity> {
	public CentrifugerTileRenderer() {
		super(new CentrifugerBlockModel());
	}

	@Override
	public RenderType getRenderType(CentrifugerTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
