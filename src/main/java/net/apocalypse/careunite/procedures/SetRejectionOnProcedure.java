package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class SetRejectionOnProcedure {
	public static void execute(Entity entity, double index) {
		if (entity == null)
			return;
		double rej = 0;
		rej = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).disoclusion;
		if (rej >= 0) {
			{
				double _setval = (int) rej | (int) Math.pow(2, index);
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.disoclusion = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
