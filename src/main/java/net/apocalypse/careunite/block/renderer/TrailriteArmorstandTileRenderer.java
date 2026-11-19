package net.apocalypse.careunite.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.block.model.TrailriteArmorstandBlockModel;
import net.apocalypse.careunite.block.entity.TrailriteArmorstandTileEntity;

public class TrailriteArmorstandTileRenderer extends GeoBlockRenderer<TrailriteArmorstandTileEntity> {
	public TrailriteArmorstandTileRenderer() {
		super(new TrailriteArmorstandBlockModel());
	}

	@Override
	public RenderType getRenderType(TrailriteArmorstandTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
