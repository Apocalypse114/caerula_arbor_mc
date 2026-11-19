package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Team;

public class NotSameTeamProcedure {
	public static boolean execute(Entity a, Entity b) {
		if (a == null || b == null)
			return false;
		Team at = a.getTeam();
		Team bt = b.getTeam();
		if (at == null || bt == null) return false;
        return !at.isAlliedTo(bt);
	}
}
