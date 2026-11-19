package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

public class SetLPLimitProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double lp = 0;
		double lpm = 0;
		double lpmConfig = 0;
		lp = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_lives;
		lpm = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_maxlive;
		lpmConfig = (int) ((double) CaerulaConfigsConfiguration.LP_LIMIT.get());
		if (lpmConfig >= 1) {
			if (lpm > lpmConfig) {
				lpm = lpmConfig;
				{
					double _setval = lpmConfig;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.player_maxlive = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
			if (lp > lpm) {
				{
					double _setval = lpm;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.player_lives = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
		lp = (entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_shield;
		lpmConfig = (int) ((double) CaerulaConfigsConfiguration.SHIELD_LIMIT.get());
		if (lpmConfig >= 1) {
			if (lp > lpmConfig) {
				{
					double _setval = lpmConfig;
					entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.player_shield = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
	}
}
