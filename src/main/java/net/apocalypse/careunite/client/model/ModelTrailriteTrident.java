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
public class ModelTrailriteTrident<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("caerula_arbor_reunite", "model_trailrite_trident"), "main");
	public final ModelPart trident;
	public final ModelPart tie;

	public ModelTrailriteTrident(ModelPart root) {
		this.trident = root.getChild("trident");
		this.tie = this.trident.getChild("tie");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition trident = partdefinition.addOrReplaceChild("trident",
				CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -23.0F, -0.5F, 1.0F, 31.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 0).addBox(-1.5F, -19.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 3)
						.addBox(-2.5F, -22.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 3).addBox(1.5F, -22.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 29)
						.addBox(-0.5F, 6.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.125F)),
				PartPose.offset(0.0F, 16.0F, 0.0F));
		PartDefinition cube_r1 = trident.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 13).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(0.0F, -17.25F, 0.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition cube_r2 = trident.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(4, 11).mirror().addBox(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0015F)).mirror(false),
				PartPose.offsetAndRotation(-1.0F, -16.75F, -0.5F, 0.0F, 0.0F, 0.3927F));
		PartDefinition cube_r3 = trident.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(4, 11).addBox(0.0F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0015F)),
				PartPose.offsetAndRotation(1.0F, -16.75F, -0.5F, 0.0F, 0.0F, -0.3927F));
		PartDefinition cube_r4 = trident.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(4, 8).mirror().addBox(-0.5F, 0.125F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false),
				PartPose.offsetAndRotation(2.0F, -20.0F, 0.0F, 0.0F, 0.0F, -0.3927F));
		PartDefinition cube_r5 = trident.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(4, 8).addBox(-0.5F, 0.125F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)),
				PartPose.offsetAndRotation(-2.0F, -20.0F, 0.0F, 0.0F, 0.0F, 0.3927F));
		PartDefinition tie = trident.addOrReplaceChild("tie", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition cube_r6 = tie.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(4, 25).addBox(-1.0F, -3.0F, -0.01F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 6.0F, -0.5F, 0.0F, 0.0F, 0.3927F));
		PartDefinition cube_r7 = tie.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(10, 9).addBox(-1.0F, -6.0F, -0.001F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.5F, 3.0F, -0.5F, 0.0F, 0.0F, -0.3927F));
		PartDefinition cube_r8 = tie.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(4, 16).addBox(-1.0F, -8.0F, -0.099F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0F, -2.0F, -0.4F, 0.0F, 0.0F, 0.3927F));
		PartDefinition cube_r9 = tie.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(4, 16).addBox(-1.0F, -8.0F, 0.099F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.0F, -9.75F, -0.6F, 0.0F, 0.0F, -0.3927F));
		return LayerDefinition.create(meshdefinition, 24, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		trident.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
