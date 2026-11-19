package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.ViviparousLilyBlockModel;
import net.apocalypse.careunite.block.entity.ViviparousLilyTileEntity;

public class ViviparousLilyTileRenderer extends GeoBlockRenderer<ViviparousLilyTileEntity> {
	public ViviparousLilyTileRenderer() {
		super(new ViviparousLilyBlockModel());
	}

	@Override
	public RenderType getRenderType(ViviparousLilyTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
