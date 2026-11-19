package net.apocalypse.careunite.client.model.animations;

import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.AnimationChannel;

// Save this class in your mod and generate all required imports
/**
 * Made with Blockbench 4.12.6 Exported for Minecraft version 1.19 or later with
 * Mojang mappings
 * 
 * @author Author
 */
public class OceanizedSnifferEggAnimation {
	public static final AnimationDefinition EggIdle = AnimationDefinition.Builder.withLength(1.0F).looping()
			.addAnimation("egg", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.scaleVec(0.975F, 1.0625F, 0.975F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
}
