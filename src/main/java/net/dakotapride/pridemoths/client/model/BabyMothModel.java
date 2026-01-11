package net.dakotapride.pridemoths.client.model;

// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import net.dakotapride.pridemoths.client.renderer.MothAnimations;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;

public class BabyMothModel extends EntityModel<MothRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("pridemoths", "baby_moth"), "main");
	private final ModelPart body;
	private final ModelPart antennae;
	private final ModelPart wings;
	private final ModelPart fur;

	private final KeyframeAnimation idlingAnimation;

	public BabyMothModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.antennae = this.body.getChild("antennae");
		this.wings = this.body.getChild("wings");
		this.fur = this.body.getChild("fur");

		this.idlingAnimation = MothAnimations.ANIM_IDLE.bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.25F));

		PartDefinition antennae = body.addOrReplaceChild("antennae", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -2.5F));

		PartDefinition right2 = antennae.addOrReplaceChild("right2", CubeListBuilder.create().texOffs(11, 2).addBox(0.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, 0.75F, 0.0F, 0.0873F, 0.0F, 0.1309F));

		PartDefinition left2 = antennae.addOrReplaceChild("left2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, 0.75F, 0.0F, 0.0873F, 0.0F, -0.1309F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -0.5F));

		PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -0.5F));

		PartDefinition left = wings.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, -0.75F));

		PartDefinition left_r1 = left.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, -0.1309F, 0.0F));

		PartDefinition right = wings.addOrReplaceChild("right", CubeListBuilder.create(), PartPose.offset(0.5F, 0.0F, -0.75F));

		PartDefinition right_r1 = right.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(6, 8).addBox(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.1309F, 0.0F));

		PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create().texOffs(11, 1).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(7, 10).addBox(-1.5F, 0.0F, 1.5F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition fur = body.addOrReplaceChild("fur", CubeListBuilder.create(), PartPose.offset(0.5F, 4.0F, 2.5F));

		PartDefinition top = fur.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(-0.5F, -5.0F, -4.0F));

		PartDefinition top_r1 = top.addOrReplaceChild("top_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition top_r2 = top.addOrReplaceChild("top_r2", CubeListBuilder.create().texOffs(10, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition right3 = fur.addOrReplaceChild("right3", CubeListBuilder.create(), PartPose.offset(1.0F, -3.5F, -2.5F));

		PartDefinition right3_r1 = right3.addOrReplaceChild("right3_r1", CubeListBuilder.create().texOffs(10, 11).addBox(0.0F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.5F, 0.0F, -1.2217F, 0.0F));

		PartDefinition right3_r2 = right3.addOrReplaceChild("right3_r2", CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, -1.2217F, 0.0F));

		PartDefinition left3 = fur.addOrReplaceChild("left3", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, -3.5F, -2.4167F, 0.0F, 0.0F, -3.1416F));

		PartDefinition left3_r1 = left3.addOrReplaceChild("left3_r1", CubeListBuilder.create().texOffs(6, 11).addBox(0.0F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.4167F, 0.0F, -1.2217F, 0.0F));

		PartDefinition left3_r2 = left3.addOrReplaceChild("left3_r2", CubeListBuilder.create().texOffs(8, 11).addBox(0.0F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5833F, 0.0F, -1.2217F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(MothRenderState state) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.idlingAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
	}
}