package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.Endspeaker1Entity;

public class IsEndspeaker1StartingProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity.tickCount >= 50 && !(entity instanceof Endspeaker1Entity _datEntL1 && _datEntL1.getEntityData().get(Endspeaker1Entity.DATA_isEvolving));
	}
}
