package com.lzyzl6.model;

import com.lzyzl6.animation.WanderingSpiritAnimation;
import com.lzyzl6.entity.WanderingSpirit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;

public class WanderingSpiritModel extends HierarchicalModel<WanderingSpirit> {
	private final ModelPart head;
	private final ModelPart body;

	public WanderingSpiritModel(ModelPart root) {
		this.body = root.getChild("body");
		this.head = body.getChild("head");
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
	public void setupAnim(WanderingSpirit entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.setHeadAngles(netHeadYaw, headPitch);
		this.animateWalk(WanderingSpiritAnimation.fly,
				limbSwing,
				limbSwingAmount * 0.8f,  // 降低幅度避免过度摆动
				1.6f,  // 降低速度系数匹配动画关键帧
				0.0f); // 调整腿间距参数
		// 在空闲动画调用前添加移动状态判断
		if (limbSwingAmount < 0.05f) { // 当移动量接近0时播放完整空闲动画
			this.animate(WanderingSpirit.idleAnimation,
					WanderingSpiritAnimation.idle,
					ageInTicks,
					0.6f); // 动画混合强度
		}
	}

	private void setHeadAngles(float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = headPitch * ((float)Math.PI / 180F);
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