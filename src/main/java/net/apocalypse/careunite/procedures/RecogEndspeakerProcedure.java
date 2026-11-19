package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.Endspeaker3Entity;
import net.apocalypse.careunite.entity.Endspeaker2Entity;
import net.apocalypse.careunite.entity.Endspeaker1Entity;
import net.apocalypse.careunite.entity.Endspeaker0Entity;

public class RecogEndspeakerProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (entity instanceof Endspeaker0Entity) {
			return true;
		}
		if (entity instanceof Endspeaker1Entity) {
			return true;
		}
		if (entity instanceof Endspeaker2Entity) {
			return true;
		}
		if (entity instanceof Endspeaker3Entity) {
			return true;
		}
		return false;
	}
}
