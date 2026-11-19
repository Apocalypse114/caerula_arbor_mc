package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class RevivesPlayerLightsProcedure {
	public static void execute(Entity player, double num) {
		if (player == null)
			return;
		double snt = 0;
		if (num > 0) {
			snt = (player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).player_light + num;
			if (snt > 100) {
				snt = 100;
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
