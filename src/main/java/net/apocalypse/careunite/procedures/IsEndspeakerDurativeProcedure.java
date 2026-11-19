package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.Endspeaker2Entity;

public class IsEndspeakerDurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (entity instanceof Endspeaker2Entity _datEntL0 && _datEntL0.getEntityData().get(Endspeaker2Entity.DATA_isEvolving)) {
			return false;
		}
		return (entity instanceof Endspeaker2Entity _datEntI ? _datEntI.getEntityData().get(Endspeaker2Entity.DATA_duration) : 0) <= 0 && entity.tickCount >= 68;
	}
}
