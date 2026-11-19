package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;

public class EndspeakerEvoCountProcedure {
	public static double execute(LevelAccessor world) {
		double r = 0;
		for (int index0 = 0; index0 < 6; index0++) {
			r = r + CastBooleanToIntProcedure.execute(InquirybilityProcedure.execute(world, index0));
		}
		return r;
	}
}
