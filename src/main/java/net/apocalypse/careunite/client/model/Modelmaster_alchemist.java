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
public class Modelmaster_alchemist<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("caerula_arbor_reunite", "modelmaster_alchemist"), "main");
	public final ModelPart all;
	public final ModelPart head;
	public final ModelPart armLeft;
	public final ModelPart armRight;
	public final ModelPart body;
	public final ModelPart robeLeft;
	public final ModelPart robeRight;
	public final ModelPart legLeft;
	public final ModelPart legRight;
	public final ModelPart bootLeft;
	public final ModelPart bootRight;
	public final ModelPart nope;

	public Modelmaster_alchemist(ModelPart root) {
		this.all = root.getChild("all");
		this.head = this.all.getChild("head");
		this.armLeft = this.all.getChild("armLeft");
		this.armRight = this.all.getChild("armRight");
		this.body = this.all.getChild("body");
		this.robeLeft = this.body.getChild("robeLeft");
		this.robeRight = this.body.getChild("robeRight");
		this.legLeft = this.all.getChild("legLeft");
		this.legRight = this.all.getChild("legRight");
		this.bootLeft = this.all.getChild("bootLeft");
		this.bootRight = this.all.getChild("bootRight");
		this.nope = this.all.getChild("nope");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(3.0F, 18.0F, -1.0F));
		PartDefinition head = all.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.75F)).texOffs(0, 0).addBox(-4.0F, -8.0F, 5.4F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.75F)),
				PartPose.offset(-3.0F, -18.0F, 1.0F));
		PartDefinition surface_r1 = head.addOrReplaceChild("surface_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -0.5F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.75F)),
				PartPose.offsetAndRotation(5.9F, -4.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
		PartDefinition surface_r2 = head.addOrReplaceChild("surface_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -0.5F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.75F)),
				PartPose.offsetAndRotation(-5.9F, -4.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
		PartDefinition surface_r3 = head.addOrReplaceChild("surface_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -0.5F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.75F)),
				PartPose.offsetAndRotation(0.0F, -9.9F, 0.0F, 1.5708F, 0.0F, 0.0F));
		PartDefinition armLeft = all.addOrReplaceChild("armLeft",
				CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).texOffs(16, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.525F)),
				PartPose.offset(2.0F, -16.0F, 1.0F));
		PartDefinition armRight = all.addOrReplaceChild("armRight", CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false).texOffs(16, 32).mirror()
				.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.525F)).mirror(false), PartPose.offset(-8.0F, -16.0F, 1.0F));
		PartDefinition body = all.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.523F)).texOffs(16, 58).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.524F)),
				PartPose.offset(-3.0F, -18.0F, 1.0F));
		PartDefinition robeLeft = body.addOrReplaceChild("robeLeft",
				CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, -2.0F, -2.0F, 8.0F, 16.0F, 4.0F, new CubeDeformation(0.575F)).texOffs(42, 36).mirror().addBox(-3.0F, -2.0F, 3.05F, 8.0F, 16.0F, 1.0F, new CubeDeformation(0.575F)).mirror(false),
				PartPose.offset(3.5F, 1.5F, 0.0F));
		PartDefinition cube_r1 = robeLeft.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 35).addBox(-2.0F, -8.0F, -0.5F, 4.0F, 16.0F, 1.0F, new CubeDeformation(0.575F)),
				PartPose.offsetAndRotation(6.55F, 6.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
		PartDefinition robeRight = body.addOrReplaceChild("robeRight",
				CubeListBuilder.create().texOffs(24, 16).mirror().addBox(-5.0F, -2.0F, -2.0F, 8.0F, 16.0F, 4.0F, new CubeDeformation(0.575F)).mirror(false).texOffs(42, 36).addBox(-5.0F, -2.0F, 3.05F, 8.0F, 16.0F, 1.0F, new CubeDeformation(0.575F)),
				PartPose.offset(-3.5F, 1.5F, 0.0F));
		PartDefinition cube_r2 = robeRight.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(32, 35).mirror().addBox(-2.0F, -8.0F, -0.5F, 4.0F, 16.0F, 1.0F, new CubeDeformation(0.575F)).mirror(false),
				PartPose.offsetAndRotation(-6.55F, 6.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
		PartDefinition legLeft = all.addOrReplaceChild("legLeft", CubeListBuilder.create().texOffs(48, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.525F)), PartPose.offset(-1.0F, -6.0F, 1.0F));
		PartDefinition legRight = all.addOrReplaceChild("legRight", CubeListBuilder.create().texOffs(48, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.525F)).mirror(false).texOffs(16, 49).mirror()
				.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.575F)).mirror(false), PartPose.offset(-5.0F, -6.0F, 1.0F));
		PartDefinition bootLeft = all.addOrReplaceChild("bootLeft", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.575F)), PartPose.offset(-1.0F, -6.0F, 1.0F));
		PartDefinition bootRight = all.addOrReplaceChild("bootRight", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.575F)).mirror(false), PartPose.offset(-5.0F, -6.0F, 1.0F));
		PartDefinition nope = all.addOrReplaceChild("nope", CubeListBuilder.create().texOffs(4, 2).addBox(-3.0F, 7.0F, -1.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
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
