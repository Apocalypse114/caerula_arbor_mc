package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class GetNodeAddDamageProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		return (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).PEVO_NODE_add_damage;
	}
}
