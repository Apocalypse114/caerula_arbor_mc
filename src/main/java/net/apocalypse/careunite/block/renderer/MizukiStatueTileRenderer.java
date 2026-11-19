package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.MizukiStatueBlockModel;
import net.apocalypse.careunite.block.entity.MizukiStatueTileEntity;

public class MizukiStatueTileRenderer extends GeoBlockRenderer<MizukiStatueTileEntity> {
	public MizukiStatueTileRenderer() {
		super(new MizukiStatueBlockModel());
	}

	@Override
	public RenderType getRenderType(MizukiStatueTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
