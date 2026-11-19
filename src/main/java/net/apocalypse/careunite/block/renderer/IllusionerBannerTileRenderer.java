package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.IllusionerBannerBlockModel;
import net.apocalypse.careunite.block.entity.IllusionerBannerTileEntity;

public class IllusionerBannerTileRenderer extends GeoBlockRenderer<IllusionerBannerTileEntity> {
	public IllusionerBannerTileRenderer() {
		super(new IllusionerBannerBlockModel());
	}

	@Override
	public RenderType getRenderType(IllusionerBannerTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
