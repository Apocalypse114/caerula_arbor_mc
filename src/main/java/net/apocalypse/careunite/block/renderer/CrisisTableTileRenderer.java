package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.CrisisTableBlockModel;
import net.apocalypse.careunite.block.entity.CrisisTableTileEntity;

public class CrisisTableTileRenderer extends GeoBlockRenderer<CrisisTableTileEntity> {
	public CrisisTableTileRenderer() {
		super(new CrisisTableBlockModel());
	}

	@Override
	public RenderType getRenderType(CrisisTableTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
