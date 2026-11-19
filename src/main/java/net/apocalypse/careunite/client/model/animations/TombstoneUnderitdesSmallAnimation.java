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
public class TombstoneUnderitdesSmallAnimation {
	public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(2.0F).looping()
			.addAnimation("upper", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("piece_1",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("sub_piece_1",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.86F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.15F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.15F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.0F, KeyframeAnimations.degreeVec(1.86F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("piece_2",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.94F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.125F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.125F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.94F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("sub_piece_2",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(8.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.4F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.0F, KeyframeAnimations.degreeVec(8.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("piece_3",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.88F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.25F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.0F, KeyframeAnimations.degreeVec(1.88F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("sub_piece_3",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(13.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.65F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.0F, KeyframeAnimations.degreeVec(13.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("piece_4",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.81F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.375F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.0F, KeyframeAnimations.degreeVec(2.81F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("sub_piece_4",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(18.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.9F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.9F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.0F, KeyframeAnimations.degreeVec(18.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.build();
}
