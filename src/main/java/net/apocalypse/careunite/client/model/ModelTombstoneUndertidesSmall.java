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
public class ModelTombstoneUndertidesSmall<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("caerula_arbor_reunite", "model_tombstone_undertides_small"), "main");
	public final ModelPart body;
	public final ModelPart upper;
	public final ModelPart flower;
	public final ModelPart piece_1;
	public final ModelPart sub_piece_1;
	public final ModelPart piece_2;
	public final ModelPart sub_piece_2;
	public final ModelPart piece_3;
	public final ModelPart sub_piece_3;
	public final ModelPart piece_4;
	public final ModelPart sub_piece_4;

	public ModelTombstoneUndertidesSmall(ModelPart root) {
		this.body = root.getChild("body");
		this.upper = this.body.getChild("upper");
		this.flower = this.body.getChild("flower");
		this.piece_1 = this.flower.getChild("piece_1");
		this.sub_piece_1 = this.piece_1.getChild("sub_piece_1");
		this.piece_2 = this.flower.getChild("piece_2");
		this.sub_piece_2 = this.piece_2.getChild("sub_piece_2");
		this.piece_3 = this.flower.getChild("piece_3");
		this.sub_piece_3 = this.piece_3.getChild("sub_piece_3");
		this.piece_4 = this.flower.getChild("piece_4");
		this.sub_piece_4 = this.piece_4.getChild("sub_piece_4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 25).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.25F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition upper = body.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(16, 17).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(1, 18)
				.addBox(-3.0F, -12.0F, -0.5F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 43).addBox(-6.0F, -14.0F, 0.0F, 12.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, -0.0436F, 0.7854F, -0.0436F));
		PartDefinition flower = body.addOrReplaceChild("flower", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 0.0F));
		PartDefinition piece_1 = flower.addOrReplaceChild("piece_1", CubeListBuilder.create().texOffs(24, 18).addBox(-1.5F, -6.0F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -5.25F, 0.0F, 1.309F, -0.7854F, 0.0F));
		PartDefinition sub_piece_1 = piece_1.addOrReplaceChild("sub_piece_1", CubeListBuilder.create().texOffs(32, 18).addBox(-1.5F, -4.875F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(-0.125F)),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 1.4399F, 0.0F, 0.0F));
		PartDefinition piece_2 = flower.addOrReplaceChild("piece_2", CubeListBuilder.create().texOffs(24, 18).addBox(-1.5F, -6.0F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -5.25F, 0.0F, -1.8326F, -0.7854F, -3.1416F));
		PartDefinition sub_piece_2 = piece_2.addOrReplaceChild("sub_piece_2", CubeListBuilder.create().texOffs(32, 18).addBox(-1.5F, -4.875F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(-0.125F)),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 1.4399F, 0.0F, 0.0F));
		PartDefinition piece_3 = flower.addOrReplaceChild("piece_3", CubeListBuilder.create().texOffs(24, 18).addBox(-1.5F, -6.0F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -5.25F, 0.0F, 1.309F, 0.7854F, 0.0F));
		PartDefinition sub_piece_3 = piece_3.addOrReplaceChild("sub_piece_3", CubeListBuilder.create().texOffs(32, 18).addBox(-1.5F, -4.875F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(-0.125F)),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 1.4399F, 0.0F, 0.0F));
		PartDefinition piece_4 = flower.addOrReplaceChild("piece_4", CubeListBuilder.create().texOffs(24, 18).addBox(-1.5F, -6.0F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -5.25F, 0.0F, -1.8326F, 0.7854F, -3.1416F));
		PartDefinition sub_piece_4 = piece_4.addOrReplaceChild("sub_piece_4", CubeListBuilder.create().texOffs(32, 18).addBox(-1.5F, -4.875F, -0.5F, 3.0F, 5.0F, 1.0F, new CubeDeformation(-0.125F)),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 1.4399F, 0.0F, 0.0F));
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
