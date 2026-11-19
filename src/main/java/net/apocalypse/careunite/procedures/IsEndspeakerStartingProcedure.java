package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.Endspeaker0Entity;

public class IsEndspeakerStartingProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity.tickCount >= 28 && !(entity instanceof Endspeaker0Entity _datEntL1 && _datEntL1.getEntityData().get(Endspeaker0Entity.DATA_isEvolving));
	}
}
