package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class CanShulkerStayProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Direction dire) {
		if (dire == null)
			return false;
		Direction opposite = Direction.NORTH;
		opposite = dire.getOpposite();
		return world.getBlockState(BlockPos.containing(x + opposite.getStepX(), y + opposite.getStepY(), z + opposite.getStepZ())).isFaceSturdy(world, BlockPos.containing(x + opposite.getStepX(), y + opposite.getStepY(), z + opposite.getStepZ()),
				dire);
	}
}
