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
public class Modelraid_sign<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("caerula_arbor_reunite", "modelraid_sign"), "main");
	public final ModelPart all;
	public final ModelPart circle;
	public final ModelPart sign;
	public final ModelPart arrows;
	public final ModelPart arrow1;
	public final ModelPart arrow2;
	public final ModelPart arrow3;
	public final ModelPart arrow4;
	public final ModelPart beacon;

	public Modelraid_sign(ModelPart root) {
		this.all = root.getChild("all");
		this.circle = this.all.getChild("circle");
		this.sign = this.all.getChild("sign");
		this.arrows = this.all.getChild("arrows");
		this.arrow1 = this.arrows.getChild("arrow1");
		this.arrow2 = this.arrows.getChild("arrow2");
		this.arrow3 = this.arrows.getChild("arrow3");
		this.arrow4 = this.arrows.getChild("arrow4");
		this.beacon = this.all.getChild("beacon");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.5F, 0.0F));
		PartDefinition circle = all.addOrReplaceChild("circle", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -1.0F, 0.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition cube_r1 = circle.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -0.5F, 0.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));
		PartDefinition cube_r2 = circle.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -0.5F, 0.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));
		PartDefinition cube_r3 = circle.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -0.5F, 0.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));
		PartDefinition sign = all.addOrReplaceChild("sign", CubeListBuilder.create().texOffs(2, 17).addBox(-8.0F, 9.5F, -7.0F, 16.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.5F, 1.0F));
		PartDefinition arrows = all.addOrReplaceChild("arrows", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition arrow1 = arrows.addOrReplaceChild("arrow1", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -0.5F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -0.5F, -14.0F, 0.0F, 0.7854F, 0.0F));
		PartDefinition arrow2 = arrows.addOrReplaceChild("arrow2", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -0.5F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(14.0F, -0.5F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition arrow3 = arrows.addOrReplaceChild("arrow3", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -0.5F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -0.5F, 14.0F, 0.0F, -2.3562F, 0.0F));
		PartDefinition arrow4 = arrows.addOrReplaceChild("arrow4", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -0.5F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-14.0F, -0.5F, 0.0F, 0.0F, 2.3562F, 0.0F));
		PartDefinition beacon = all.addOrReplaceChild("beacon", CubeListBuilder.create().texOffs(1, 40).addBox(-4.0F, -36.0F, 0.0F, 8.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition cube_r4 = beacon.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(1, 40).addBox(-4.0F, -12.0F, 0.0F, 8.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		all.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
