package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class BestowAbilityProcedure {
	public static void execute(LevelAccessor world, double index) {
		CaerulaArborModVariables.MapVariables.get(world).endspeaker_abolities =(double) ((int)CaerulaArborModVariables.MapVariables.get(world).endspeaker_abolities | (int)Math.pow(2, index));
		CaerulaArborModVariables.MapVariables.get(world).syncData(world);
	}
}
