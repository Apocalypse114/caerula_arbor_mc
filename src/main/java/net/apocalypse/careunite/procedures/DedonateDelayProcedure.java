package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;

public class DedonateDelayProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, boolean burn, double iter) {
		for (int index0 = 0; index0 < 3; index0++) {
			for (int index1 = 0; index1 < 3; index1++) {
				for (int index2 = 0; index2 < 3; index2++) {
					if (!(index0 == 1 && index1 == 1 && index2 == 1)) {
						DedonateIterProcedure.execute(world, x + index0 - 1, y + index1 - 1, z + index2 - 1, burn, iter - 1);
					}
				}
			}
		}
	}
}
