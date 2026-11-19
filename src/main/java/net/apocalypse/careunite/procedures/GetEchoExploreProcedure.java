package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class GetEchoExploreProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		return (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).ECHO_EXPLORE / 2400;
	}
}
