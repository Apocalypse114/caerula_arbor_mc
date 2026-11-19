package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.CrisisTableDisplayModel;
import net.apocalypse.careunite.block.display.CrisisTableDisplayItem;

public class CrisisTableDisplayItemRenderer extends GeoItemRenderer<CrisisTableDisplayItem> {
	public CrisisTableDisplayItemRenderer() {
		super(new CrisisTableDisplayModel());
	}

	@Override
	public RenderType getRenderType(CrisisTableDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
