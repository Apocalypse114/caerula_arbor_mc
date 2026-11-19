package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class ReducePlayerLightsProcedure {
	public static void execute(LevelAccessor world, Entity player, double num) {
		if (player == null)
			return;
		double snt = 0;
		double ffNum = 0;
		if (num > 0) {
			ffNum = num;
			if (GetEchoExploreProcedure.execute(player) != 0) {
				if (CaerulaArborModVariables.MapVariables.get(world).strategy_migration >= 3) {
					ffNum = 3 * ffNum;
				} else {
					ffNum = 2 * ffNum;
				}
			}
			snt = (player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_light - ffNum;
			if (snt < 0) {
				snt = 0;
			}
			{
				double _setval = snt;
				player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.player_light = _setval;
					capability.syncPlayerVariables(player);
				});
			}
		}
	}
}
