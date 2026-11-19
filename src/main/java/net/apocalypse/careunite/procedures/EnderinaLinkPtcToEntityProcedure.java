package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class EnderinaLinkPtcToEntityProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity tgt) {
		if (tgt == null)
			return;
		EnderinaLinkPtcToProcedure.execute(world, x, y + 0.5, z, tgt.getX(), tgt.getY(), tgt.getZ());
	}
}
