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

// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelinquisition_armor<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("caerula_arbor_reunite", "modelinquisition_armor"), "main");
	public final ModelPart Helmet;
	public final ModelPart hat;
	public final ModelPart LeftArm;
	public final ModelPart RightArm;
	public final ModelPart Chestplate;
	public final ModelPart Leggings;
	public final ModelPart LeftBoots;
	public final ModelPart RightBoots;
	public final ModelPart RightLeg;
	public final ModelPart LeftLeg;
	public final ModelPart nope;

	public Modelinquisition_armor(ModelPart root) {
		this.Helmet = root.getChild("Helmet");
		this.hat = this.Helmet.getChild("hat");
		this.LeftArm = root.getChild("LeftArm");
		this.RightArm = root.getChild("RightArm");
		this.Chestplate = root.getChild("Chestplate");
		this.Leggings = root.getChild("Leggings");
		this.LeftBoots = this.Leggings.getChild("LeftBoots");
		this.RightBoots = this.Leggings.getChild("RightBoots");
		this.RightLeg = this.Leggings.getChild("RightLeg");
		this.LeftLeg = this.Leggings.getChild("LeftLeg");
		this.nope = root.getChild("nope");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition Helmet = partdefinition.addOrReplaceChild("Helmet",
				CubeListBuilder.create().texOffs(48, 65).addBox(-4.0F, -8.0F, -4.2F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.51F)).texOffs(84, 62).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.5F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Head2_r1 = Helmet.addOrReplaceChild("Head2_r1", CubeListBuilder.create().texOffs(0, 30).addBox(-4.0F, -1.5F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.52F)),
				PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, -0.1309F, 0.0F, 0.0F));
		PartDefinition hat = Helmet.addOrReplaceChild("hat",
				CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.5F)).texOffs(0, 0).addBox(-8.0F, 1.0F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -9.5F, 1.0F, -0.1309F, 0.0F, 0.0F));
		PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(56, 33).addBox(-0.5F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.455F)).texOffs(72, 16)
				.addBox(-0.5F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.45F)).texOffs(0, 57).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(5.0F, 2.0F, 0.0F));
		PartDefinition LeftArm2_r1 = LeftArm.addOrReplaceChild("LeftArm2_r1", CubeListBuilder.create().texOffs(72, 25).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.6F)),
				PartPose.offsetAndRotation(2.0F, -0.5F, 0.0F, 0.0F, 0.0F, -0.3491F));
		PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm",
				CubeListBuilder.create().texOffs(48, 49).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.255F)).texOffs(56, 17).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)).texOffs(66, 65)
						.addBox(-3.5F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.4F)).texOffs(101, 62).addBox(-2.5F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)),
				PartPose.offset(-5.0F, 2.0F, 0.0F));
		PartDefinition Chestplate = partdefinition.addOrReplaceChild("Chestplate",
				CubeListBuilder.create().texOffs(32, 17).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.35F)).texOffs(32, 33).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Body2_r1 = Chestplate.addOrReplaceChild("Body2_r1", CubeListBuilder.create().texOffs(0, 41).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.35F)),
				PartPose.offsetAndRotation(0.0F, 16.2735F, 0.8743F, 0.1745F, 0.0F, 0.0F));
		PartDefinition Leggings = partdefinition.addOrReplaceChild("Leggings", CubeListBuilder.create(), PartPose.offset(0.1F, 12.0F, 0.0F));
		PartDefinition LeftBoots = Leggings.addOrReplaceChild("LeftBoots", CubeListBuilder.create().texOffs(64, 49).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(2.0F, 0.0F, 0.0F));
		PartDefinition RightBoots = Leggings.addOrReplaceChild("RightBoots", CubeListBuilder.create().texOffs(32, 59).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(-2.0F, 0.0F, 0.0F));
		PartDefinition RightLeg = Leggings.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(16, 59).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.255F)), PartPose.offset(-2.0F, 0.0F, 0.0F));
		PartDefinition LeftLeg = Leggings.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(64, 0).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.255F)), PartPose.offset(1.8F, 0.0F, 0.0F));
		PartDefinition nope = partdefinition.addOrReplaceChild("nope", CubeListBuilder.create().texOffs(4, 2).addBox(1.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Helmet.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Chestplate.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Leggings.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		nope.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
