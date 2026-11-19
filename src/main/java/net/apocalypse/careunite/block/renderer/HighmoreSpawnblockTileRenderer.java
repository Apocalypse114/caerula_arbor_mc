package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.HighmoreSpawnblockBlockModel;
import net.apocalypse.careunite.block.entity.HighmoreSpawnblockTileEntity;

public class HighmoreSpawnblockTileRenderer extends GeoBlockRenderer<HighmoreSpawnblockTileEntity> {
	public HighmoreSpawnblockTileRenderer() {
		super(new HighmoreSpawnblockBlockModel());
	}

	@Override
	public RenderType getRenderType(HighmoreSpawnblockTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
