package com.lzyzl6.model;

import com.lzyzl6.animation.WanderingSpiritAnimation;
import com.lzyzl6.entity.WanderingSpirit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class WanderingSpiritModel extends HierarchicalModel<WanderingSpirit> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart core;
	private final ModelPart arm;
	private final ModelPart left;
	private final ModelPart right;

	public WanderingSpiritModel(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.body = root.getChild("body");
		this.core = body.getChild("core");
		this.head = body.getChild("head");
		this.arm = body.getChild("arm");
		this.left = arm.getChild("left");
		this.right = arm.getChild("right");
	}


	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));
		PartDefinition core = body.addOrReplaceChild("core", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));
		PartDefinition arm = body.addOrReplaceChild("arm", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));
		PartDefinition left = arm.addOrReplaceChild("left", CubeListBuilder.create().texOffs(24, 17).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.0F, 0.0F));
		PartDefinition right = arm.addOrReplaceChild("right", CubeListBuilder.create().texOffs(17, 24).addBox(0.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 32, 32);
	}
	@Override
	public void setupAnim(WanderingSpirit ghost, float f, float g, float h, float i, float j) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		float n = h * 9.0F * (float) (Math.PI / 180.0);
		float o = Math.min(g / 0.3F, 1.0F);
		float p = 1.0F - o;
		this.head.xRot = j * (float) (Math.PI / 180.0);
		this.head.yRot = i * (float) (Math.PI / 180.0);
		this.core.xRot = o * (float) (Math.PI / 4);
		this.body.y = this.body.y + (float)Math.cos(n) * 0.25F * p;
		this.animateWalk(WanderingSpiritAnimation.fly,
				f,
				g,
				0.8f,
				0.9f);
		if (g < 0.05f && !ghost.walkAnimation.isMoving()) { // 当移动量接近0时播放完整空闲动画
			this.animate(WanderingSpirit.idleAnimation,
					WanderingSpiritAnimation.idle,
					h,
					0.8f); // 动画混合强度
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		body.render(poseStack, vertexConsumer, light, overlay, color);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.body;
	}

}