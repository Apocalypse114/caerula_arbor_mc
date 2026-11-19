
package net.apocalypse.careunite.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;

import net.apocalypse.careunite.entity.GunmuEntity;

public class GunmuRenderer extends HumanoidMobRenderer<GunmuEntity, HumanoidModel<GunmuEntity>> {
	public GunmuRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel<GunmuEntity>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(GunmuEntity entity) {
		return new ResourceLocation("caerula_arbor_reunite:textures/entities/white.png");
	}

	@Override
	protected boolean isBodyVisible(GunmuEntity entity) {
		return false;
	}

	@Override
	protected boolean isShaking(GunmuEntity entity) {
		return true;
	}
}
