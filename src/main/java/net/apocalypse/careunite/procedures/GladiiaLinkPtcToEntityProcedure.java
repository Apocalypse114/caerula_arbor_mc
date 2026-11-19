package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class GladiiaLinkPtcToEntityProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity tgt) {
		if (entity == null || tgt == null)
			return;
		GladiiaLinkPtcToProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), tgt.getX(), tgt.getY(), tgt.getZ());
	}
}
