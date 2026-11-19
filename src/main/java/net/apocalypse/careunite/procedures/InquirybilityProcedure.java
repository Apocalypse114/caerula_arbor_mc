package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class InquirybilityProcedure {
	public static boolean execute(LevelAccessor world, double index) {
		int comparator = (int) Math.pow(2, index);
		int inq = (int) CaerulaArborModVariables.MapVariables.get(world).endspeaker_abolities & comparator;
		return inq == comparator;
	}
}
