package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class GetShulkerDirectionProcedure {
	public static Direction execute(LevelAccessor world, double x, double y, double z) {
		Direction opposite = Direction.NORTH;
		if (world.getBlockFloorHeight(BlockPos.containing(x, y, z)) > 0) {
			return null;
		}
		for (Direction directioniterator : Direction.values()) {
			if (CanShulkerStayProcedure.execute(world, x, y, z, directioniterator)) {
				return directioniterator;
			}
		}
		return null;
	}
}
