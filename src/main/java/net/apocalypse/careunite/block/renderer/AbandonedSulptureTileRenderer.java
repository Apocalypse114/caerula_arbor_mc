package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.AbandonedSulptureBlockModel;
import net.apocalypse.careunite.block.entity.AbandonedSulptureTileEntity;

public class AbandonedSulptureTileRenderer extends GeoBlockRenderer<AbandonedSulptureTileEntity> {
	public AbandonedSulptureTileRenderer() {
		super(new AbandonedSulptureBlockModel());
	}

	@Override
	public RenderType getRenderType(AbandonedSulptureTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
