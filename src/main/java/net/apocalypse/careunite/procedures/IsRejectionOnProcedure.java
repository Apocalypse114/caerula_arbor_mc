package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class IsRejectionOnProcedure {
	public static boolean execute(Entity entity, double index) {
		if (entity == null)
			return false;
		double rej = 0;
		rej = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).disoclusion;
		if (rej < 0) {
			return false;
		}
		return ((int) rej & (int) Math.pow(2, index)) != 0;
	}
}
