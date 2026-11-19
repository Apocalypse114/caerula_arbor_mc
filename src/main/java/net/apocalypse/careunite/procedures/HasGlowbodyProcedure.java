package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class HasGlowbodyProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_cursed_GLOWBODY;
	}
}
