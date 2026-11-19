package net.apocalypse.careunite.client.model;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class ModelTombstoneStultifera<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("caerula_arbor_reunite", "model_tombstone_stultifera"), "main");
	public final ModelPart body;
	public final ModelPart upper;
	public final ModelPart deco;
	public final ModelPart deco1;

	public ModelTombstoneStultifera(ModelPart root) {
		this.body = root.getChild("body");
		this.upper = this.body.getChild("upper");
		this.deco = this.body.getChild("deco");
		this.deco1 = this.body.getChild("deco1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(0, 25).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 5.0F, 12.0F, new CubeDeformation(0.25F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition upper = body.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(16, 17).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(1, 18)
				.addBox(-3.0F, -12.0F, -0.5F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 43).addBox(-6.0F, -15.0F, 0.0F, 12.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -0.0436F, 0.7854F, -0.0436F));
		PartDefinition deco = body.addOrReplaceChild("deco", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition cube_r1 = deco.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 43).addBox(-8.0F, -9.0F, 0.0F, 16.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 1.9635F, 0.0F, -3.1416F));
		PartDefinition cube_r2 = deco.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 43).addBox(-8.0F, -9.0F, 0.0F, 16.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -1.1781F, 0.0F, 0.0F));
		PartDefinition deco1 = body.addOrReplaceChild("deco1", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));
		PartDefinition cube_r3 = deco1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(24, 43).addBox(-8.0F, -9.0F, 0.0F, 16.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -1.1781F));
		PartDefinition cube_r4 = deco1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(24, 43).addBox(-8.0F, -9.0F, 0.0F, 16.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 1.1781F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
