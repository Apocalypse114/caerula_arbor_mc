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
public class Modelinquisition_suit<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("caerula_arbor_reunite", "modelinquisition_suit"), "main");
	public final ModelPart Lower;
	public final ModelPart LeftBoots;
	public final ModelPart RightBoots;
	public final ModelPart RightLeg;
	public final ModelPart LeftLeg;
	public final ModelPart Body;
	public final ModelPart Head2;
	public final ModelPart RightArm2;
	public final ModelPart LeftArm2;
	public final ModelPart nope;

	public Modelinquisition_suit(ModelPart root) {
		this.Lower = root.getChild("Lower");
		this.LeftBoots = this.Lower.getChild("LeftBoots");
		this.RightBoots = this.Lower.getChild("RightBoots");
		this.RightLeg = this.Lower.getChild("RightLeg");
		this.LeftLeg = this.Lower.getChild("LeftLeg");
		this.Body = root.getChild("Body");
		this.Head2 = this.Body.getChild("Head2");
		this.RightArm2 = root.getChild("RightArm2");
		this.LeftArm2 = root.getChild("LeftArm2");
		this.nope = root.getChild("nope");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition Lower = partdefinition.addOrReplaceChild("Lower", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
		PartDefinition LeftBoots = Lower.addOrReplaceChild("LeftBoots", CubeListBuilder.create().texOffs(48, 0).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(4.0F, 0.0F, 0.0F));
		PartDefinition RightBoots = Lower.addOrReplaceChild("RightBoots", CubeListBuilder.create().texOffs(16, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition RightLeg = Lower.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.255F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition LeftLeg = Lower.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(32, 43).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.255F)), PartPose.offset(3.8F, 0.0F, 0.0F));
		PartDefinition Body = partdefinition.addOrReplaceChild("Body",
				CubeListBuilder.create().texOffs(24, 11).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.35F)).texOffs(0, 11).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Head2 = Body.addOrReplaceChild("Head2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Head2_r1 = Head2.addOrReplaceChild("Head2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.5F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.35F)),
				PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, -0.1309F, 0.0F, 0.0F));
		PartDefinition RightArm2 = partdefinition.addOrReplaceChild("RightArm2", CubeListBuilder.create().texOffs(0, 27).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.255F)).texOffs(32, 27)
				.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)).texOffs(48, 26).addBox(-3.5F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(-5.0F, 2.0F, 0.0F));
		PartDefinition LeftArm2 = partdefinition.addOrReplaceChild("LeftArm2",
				CubeListBuilder.create().texOffs(16, 27).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).texOffs(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.5F)),
				PartPose.offset(5.0F, 2.0F, 0.0F));
		PartDefinition LeftArm2_r1 = LeftArm2.addOrReplaceChild("LeftArm2_r1", CubeListBuilder.create().texOffs(32, 27).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)),
				PartPose.offsetAndRotation(1.0F, 4.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
		PartDefinition LeftArm2_r2 = LeftArm2.addOrReplaceChild("LeftArm2_r2", CubeListBuilder.create().texOffs(48, 16).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.6F)),
				PartPose.offsetAndRotation(2.0F, -0.5F, 0.0F, 0.0F, 0.0F, -0.3491F));
		PartDefinition nope = partdefinition.addOrReplaceChild("nope", CubeListBuilder.create().texOffs(4, 2).addBox(1.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Lower.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		nope.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
