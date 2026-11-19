package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.LivingArmorstandDisplayModel;
import net.apocalypse.careunite.block.display.LivingArmorstandDisplayItem;

public class LivingArmorstandDisplayItemRenderer extends GeoItemRenderer<LivingArmorstandDisplayItem> {
	public LivingArmorstandDisplayItemRenderer() {
		super(new LivingArmorstandDisplayModel());
	}

	@Override
	public RenderType getRenderType(LivingArmorstandDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
