package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class IsharmlaLinkPtcToEntityProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity tgt) {
		if (tgt == null)
			return;
		IsharmlaRemainLinkPtcToProcedure.execute(world, x + 0.5, y + 0.5, z + 0.5, tgt.getX(), tgt.getY(), tgt.getZ());
	}
}
