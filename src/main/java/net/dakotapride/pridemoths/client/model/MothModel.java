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

public class MothModel extends EntityModel<MothRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath("pridemoths", "moth"), "main");
	private final ModelPart body;
	private final ModelPart antennae;
	private final ModelPart wings;
	private final ModelPart fur;

	private final KeyframeAnimation idlingAnimation;

	public MothModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
		this.antennae = this.body.getChild("antennae");
		this.wings = this.body.getChild("wings");
		this.fur = this.body.getChild("fur");

		//this.walkingAnimation = MothAnimations.ANIM_FLIGHT.bake(root);
		this.idlingAnimation = MothAnimations.ANIM_IDLE.bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, -1.0F));

		PartDefinition antennae = body.addOrReplaceChild("antennae", CubeListBuilder.create(), PartPose.offset(0.5F, 1.0F, 2.0F));

		PartDefinition right2 = antennae.addOrReplaceChild("right2", CubeListBuilder.create().texOffs(0, 5).addBox(0.0F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -3.0F, -6.0F, 0.0873F, 0.0F, 0.1309F));

		PartDefinition left2 = antennae.addOrReplaceChild("left2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -5.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -3.0F, -6.0F, 0.0873F, 0.0F, -0.1309F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 14).addBox(-2.5F, -4.0F, -6.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 1.0F, 2.0F));

		PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.5F, -3.0F, -2.0F));

		PartDefinition left = wings.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, 0.0F));

		PartDefinition left_r1 = left.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(3, 4).addBox(-8.0F, 0.0F, 0.0F, 8.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, -0.1309F, 0.0F));

		PartDefinition right = wings.addOrReplaceChild("right", CubeListBuilder.create(), PartPose.offset(0.5F, 0.0F, 0.0F));

		PartDefinition right_r1 = right.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(3, 9).addBox(0.0F, 0.0F, 0.0F, 8.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.1309F, 0.0F));

		PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create().texOffs(18, 18).addBox(-2.5F, 0.0F, -1.5F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(18, 16).addBox(-2.5F, 0.0F, -0.5F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(18, 14).addBox(-2.5F, 0.0F, 1.5F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 2.0F, -1.5F));

		PartDefinition fur = body.addOrReplaceChild("fur", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 1.0F));

		PartDefinition top = fur.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition top_r1 = top.addOrReplaceChild("top_r1", CubeListBuilder.create().texOffs(2, 27).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -7.0F, 1.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition top_r2 = top.addOrReplaceChild("top_r2", CubeListBuilder.create().texOffs(7, 2).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -7.0F, -4.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition top_r3 = top.addOrReplaceChild("top_r3", CubeListBuilder.create().texOffs(7, 0).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -7.0F, -2.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition right3 = fur.addOrReplaceChild("right3", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition right3_r1 = right3.addOrReplaceChild("right3_r1", CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.5F, -4.0F, 0.0F, -1.2217F, 0.0F));

		PartDefinition right3_r2 = right3.addOrReplaceChild("right3_r2", CubeListBuilder.create().texOffs(0, 27).addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.5F, 1.0F, 0.0F, -1.2217F, 0.0F));

		PartDefinition right3_r3 = right3.addOrReplaceChild("right3_r3", CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.5F, -2.0F, 0.0F, -1.2217F, 0.0F));

		PartDefinition left3 = fur.addOrReplaceChild("left3", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, -4.5F, -1.6667F, 0.0F, 0.0F, -3.1416F));

		PartDefinition left3_r1 = left3.addOrReplaceChild("left3_r1", CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -2.3333F, 0.0F, -1.2217F, 0.0F));

		PartDefinition left3_r2 = left3.addOrReplaceChild("left3_r2", CubeListBuilder.create().texOffs(0, 27).addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 2.6667F, 0.0F, -1.2217F, 0.0F));

		PartDefinition left3_r3 = left3.addOrReplaceChild("left3_r3", CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, -2.5F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -0.3333F, 0.0F, -1.2217F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(MothRenderState state) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.idlingAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
	}
}