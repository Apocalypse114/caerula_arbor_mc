package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class FindYzforTearProcedure {
	public static double execute(LevelAccessor world, double xx, double yy, double zz) {
		double y_found = 0;
		y_found = yy;
		for (int index0 = 0; index0 < 12; index0++) {
			y_found = yy + index0;
			if (world.isEmptyBlock(BlockPos.containing(xx, y_found, zz))) {
				return y_found;
			}
		}
		return 114514;
	}
}
