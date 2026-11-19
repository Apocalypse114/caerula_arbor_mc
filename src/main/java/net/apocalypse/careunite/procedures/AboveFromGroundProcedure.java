package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class AboveFromGroundProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		double block = 0;
		if (y < -64) {
			return false;
		}
		return world.isEmptyBlock(BlockPos.containing(x, y - 1, z));
	}
}
