package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class ResetRewardLimitProcedure {
	public static void execute(Entity entity, double id, double t) {
		if (entity == null)
			return;
		if (t > 1000 && (id == 2 || id == 3 || id == 7)) {
			{
				double _setval = Math.min(32, Math.max((int) (32 * t * 0.0005), 1));
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.reward_limit = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
