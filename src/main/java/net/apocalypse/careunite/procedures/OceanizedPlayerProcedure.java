package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class OceanizedPlayerProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		boolean valid = false;
		return (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElseGet(CaerulaArborModVariables.PlayerVariables::new)).player_oceanization < 2.5;
	}
}
