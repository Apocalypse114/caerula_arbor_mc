package net.apocalypse.careunite.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.apocalypse.careunite.entity.ShotOceanArrowEntity;
import net.apocalypse.careunite.client.model.Modeloceanarrow_model;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.ArrowRenderer;

public class ShotOceanArrowRenderer extends ArrowRenderer<ShotOceanArrowEntity> {
	private static final ResourceLocation texture = new ResourceLocation("caerula_arbor_reunite:textures/entities/oceanarrow_model.png");

	public ShotOceanArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(ShotOceanArrowEntity entity) {
		return texture;
	}
}
