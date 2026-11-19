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

// Made with Blockbench 5.2.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelbatalion<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("caerula_arbor_reunite", "modelbatalion"), "main");
	public final ModelPart Head;
	public final ModelPart Head2;
	public final ModelPart Waist;
	public final ModelPart Body;
	public final ModelPart Right_Arm;
	public final ModelPart Left_Arm;
	public final ModelPart lower;
	public final ModelPart nope;
	public final ModelPart Left_Foot;
	public final ModelPart Righ_Foot;
	public final ModelPart Left_Leg;
	public final ModelPart Right_Leg;

	public Modelbatalion(ModelPart root) {
		this.Head = root.getChild("Head");
		this.Head2 = this.Head.getChild("Head2");
		this.Waist = root.getChild("Waist");
		this.Body = this.Waist.getChild("Body");
		this.Right_Arm = this.Waist.getChild("Right_Arm");
		this.Left_Arm = this.Waist.getChild("Left_Arm");
		this.lower = root.getChild("lower");
		this.nope = root.getChild("nope");
		this.Left_Foot = root.getChild("Left_Foot");
		this.Righ_Foot = root.getChild("Righ_Foot");
		this.Left_Leg = root.getChild("Left_Leg");
		this.Right_Leg = root.getChild("Right_Leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(20, 41).addBox(4.0F, -6.0F, -1.6184F, 0.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Head2 = Head.addOrReplaceChild("Head2", CubeListBuilder.create().texOffs(0, 13).addBox(-4.0F, -4.3862F, -5.2369F, 8.0F, 8.0F, 9.0F, new CubeDeformation(0.55F)).texOffs(0, 0)
				.addBox(-6.0F, 3.8638F, -7.2369F, 12.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(48, 30).addBox(0.0F, -7.8862F, -6.2369F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.6138F, 1.1185F));
		PartDefinition Head_r1 = Head2.addOrReplaceChild("Head_r1", CubeListBuilder.create().texOffs(34, 13).addBox(0.0F, -7.0F, -5.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.5F, 2.6138F, 2.2631F, 0.0F, 0.0F, 0.7854F));
		PartDefinition Head_r2 = Head2.addOrReplaceChild("Head_r2", CubeListBuilder.create().texOffs(20, 47).addBox(0.0F, -7.0F, -5.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.5F, 2.6138F, 2.2631F, 0.0F, 0.0F, -0.7854F));
		PartDefinition Head_r3 = Head2.addOrReplaceChild("Head_r3", CubeListBuilder.create().texOffs(0, 41).addBox(0.0F, -7.0F, -5.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.5F, 2.6138F, 2.2631F, -0.1744F, -0.0076F, -0.043F));
		PartDefinition Head_r4 = Head2.addOrReplaceChild("Head_r4", CubeListBuilder.create().texOffs(28, 30).addBox(0.0F, -7.0F, -5.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.5F, 2.6138F, 2.2631F, -0.1744F, 0.0076F, 0.043F));
		PartDefinition Head_r5 = Head2.addOrReplaceChild("Head_r5", CubeListBuilder.create().texOffs(48, 45).addBox(-6.0F, 0.0F, -2.0F, 12.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 3.8638F, -7.2369F, -0.6109F, 0.0F, 0.0F));
		PartDefinition Waist = partdefinition.addOrReplaceChild("Waist", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Body = Waist.addOrReplaceChild("Body",
				CubeListBuilder.create().texOffs(40, 47).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.55F)).texOffs(64, 47).addBox(-4.0F, -0.1F, -2.2F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.55F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Right_Arm = Waist.addOrReplaceChild("Right_Arm", CubeListBuilder.create().texOffs(50, 0).addBox(-3.0F, -3.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.56F)), PartPose.offset(-5.0F, 2.0F, 0.0F));
		PartDefinition Left_Arm = Waist.addOrReplaceChild("Left_Arm", CubeListBuilder.create().texOffs(54, 11).addBox(-1.0F, -3.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.56F)), PartPose.offset(5.0F, 2.0F, 0.0F));
		PartDefinition lower = partdefinition.addOrReplaceChild("lower",
				CubeListBuilder.create().texOffs(0, 30).addBox(-4.0F, 8.0F, -3.0F, 8.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(22, 41).addBox(-4.0F, 9.0F, -3.2F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(26, 41)
						.addBox(1.0F, 9.0F, -3.2F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(16, 58).addBox(3.0F, 9.0F, -3.2F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(36, 64)
						.addBox(5.0F, 13.0F, 2.0F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Body_r1 = lower.addOrReplaceChild("Body_r1", CubeListBuilder.create().texOffs(30, 64).addBox(2.0F, -3.0F, -1.5F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2F, 11.0F, 2.5F, 0.0F, 0.0F, 0.1309F));
		PartDefinition Body_r2 = lower.addOrReplaceChild("Body_r2", CubeListBuilder.create().texOffs(16, 64).addBox(-2.0F, -3.0F, -1.5F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.0F, 11.0F, 2.5F, 0.0F, 0.0F, 0.1309F));
		PartDefinition nope = partdefinition.addOrReplaceChild("nope", CubeListBuilder.create().texOffs(4, 2).addBox(1.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition Left_Foot = partdefinition.addOrReplaceChild("Left_Foot", CubeListBuilder.create().texOffs(56, 58).addBox(-1.9F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.55F)), PartPose.offset(2.0F, 12.0F, 0.0F));
		PartDefinition Righ_Foot = partdefinition.addOrReplaceChild("Righ_Foot", CubeListBuilder.create().texOffs(54, 22).addBox(-1.9F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.55F)), PartPose.offset(-2.0F, 12.0F, 0.0F));
		PartDefinition Left_Leg = partdefinition.addOrReplaceChild("Left_Leg", CubeListBuilder.create().texOffs(40, 58).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.55F)), PartPose.offset(1.9F, 12.0F, 0.0F));
		PartDefinition Right_Leg = partdefinition.addOrReplaceChild("Right_Leg", CubeListBuilder.create().texOffs(0, 58).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.55F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Waist.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		lower.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		nope.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Left_Foot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Righ_Foot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Left_Leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Right_Leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
