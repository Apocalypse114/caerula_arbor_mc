package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class SwitchShwostatsProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			boolean _setval = !(entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).show_stats;
			entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.show_stats = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
