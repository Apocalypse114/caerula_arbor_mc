package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.ChestmegaSpawnerBlockModel;
import net.apocalypse.careunite.block.entity.ChestmegaSpawnerTileEntity;

public class ChestmegaSpawnerTileRenderer extends GeoBlockRenderer<ChestmegaSpawnerTileEntity> {
	public ChestmegaSpawnerTileRenderer() {
		super(new ChestmegaSpawnerBlockModel());
	}

	@Override
	public RenderType getRenderType(ChestmegaSpawnerTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
