package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.LivingArmorstandBlockModel;
import net.apocalypse.careunite.block.entity.LivingArmorstandTileEntity;

public class LivingArmorstandTileRenderer extends GeoBlockRenderer<LivingArmorstandTileEntity> {
	public LivingArmorstandTileRenderer() {
		super(new LivingArmorstandBlockModel());
	}

	@Override
	public RenderType getRenderType(LivingArmorstandTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
