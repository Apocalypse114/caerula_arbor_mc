package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.Endspeaker0Entity;

public class EndspeakerStartProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Endspeaker0Entity) {
			((Endspeaker0Entity) entity).setAnimation("animation.endspeaker_0.start");
		}
	}
}
