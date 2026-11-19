package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;
import net.apocalypse.careunite.CaerulaArborMod;

public class ChangePlayerLPOrShieldProcedure {
	public static boolean execute(LevelAccessor world, Entity entity, boolean is_lp, double amount, String reason) {
		if (entity == null || reason == null)
			return false;
		double ffAmount = 0;
		double foreAmount = 0;
		double afterAmount = 0;
		ffAmount = (int) amount;
		if (!(reason).equals("death.not_enough_shield")) {
			if (ffAmount < 0 && GetEchoSpindlingProcedure.execute(entity) != 0) {
				if (CaerulaArborModVariables.MapVariables.get(world).strategy_grow >= 3) {
					ffAmount = ffAmount * 3;
				} else {
					ffAmount = ffAmount * 2;
				}
			}
		}
		if (is_lp) {
			foreAmount = (int) ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_lives);
			afterAmount = foreAmount + ffAmount;
			foreAmount = Math.min(
					Math.min((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_maxlive, Math.max(1, (double) CaerulaConfigsConfiguration.LP_LIMIT.get())),
					Math.max(afterAmount, 1));
			{
				double _setval = foreAmount;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.player_lives = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (CaerulaConfigsConfiguration.DEBUG.get()) {
				CaerulaArborMod.LOGGER.info((entity.getDisplayName().getString() + " gain LP with amount: " + (int) ffAmount + " for reason: " + reason));
			}
			if (afterAmount < 1) {
				return false;
			}
		} else {
			foreAmount = (int) ((entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_shield);
			afterAmount = foreAmount + ffAmount;
			foreAmount = Math.min((double) CaerulaConfigsConfiguration.SHIELD_LIMIT.get(), Math.max(afterAmount, 0));
			{
				double _setval = foreAmount;
				entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.player_shield = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (CaerulaConfigsConfiguration.DEBUG.get()) {
				CaerulaArborMod.LOGGER.info((entity.getDisplayName().getString() + " gain Shield with amount: " + (int) ffAmount + " for reason: " + reason));
			}
			if (afterAmount < 0) {
				return ChangePlayerLPOrShieldProcedure.execute(world, entity, true, afterAmount, reason + ".not_enough_shield");
			}
		}
		return true;
	}
}
