package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.HugeLilyBlockModel;
import net.apocalypse.careunite.block.entity.HugeLilyTileEntity;

public class HugeLilyTileRenderer extends GeoBlockRenderer<HugeLilyTileEntity> {
	public HugeLilyTileRenderer() {
		super(new HugeLilyBlockModel());
	}

	@Override
	public RenderType getRenderType(HugeLilyTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
