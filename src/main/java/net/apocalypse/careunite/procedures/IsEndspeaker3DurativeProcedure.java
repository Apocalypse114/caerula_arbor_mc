package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.Endspeaker3Entity;

public class IsEndspeaker3DurativeProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (!entity.isAlive()) {
			return false;
		}
		return (entity instanceof Endspeaker3Entity _datEntI ? _datEntI.getEntityData().get(Endspeaker3Entity.DATA_duration) : 0) <= 0 && entity.tickCount >= 50;
	}
}
