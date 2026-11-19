package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.TrailriteArmorstandDisplayModel;
import net.apocalypse.careunite.block.display.TrailriteArmorstandDisplayItem;

public class TrailriteArmorstandDisplayItemRenderer extends GeoItemRenderer<TrailriteArmorstandDisplayItem> {
	public TrailriteArmorstandDisplayItemRenderer() {
		super(new TrailriteArmorstandDisplayModel());
	}

	@Override
	public RenderType getRenderType(TrailriteArmorstandDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
