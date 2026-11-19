package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.HighmoreSpawningBlockBlockModel;
import net.apocalypse.careunite.block.entity.HighmoreSpawningBlockTileEntity;

public class HighmoreSpawningBlockTileRenderer extends GeoBlockRenderer<HighmoreSpawningBlockTileEntity> {
	public HighmoreSpawningBlockTileRenderer() {
		super(new HighmoreSpawningBlockBlockModel());
	}

	@Override
	public RenderType getRenderType(HighmoreSpawningBlockTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
