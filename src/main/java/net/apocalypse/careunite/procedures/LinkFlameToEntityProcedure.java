package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class LinkFlameToEntityProcedure {
	public static void execute(LevelAccessor world, Entity from, Entity tgt) {
		if (from == null || tgt == null)
			return;
		LinkFlameToProcedure.execute(world, from.getX(), from.getY() + from.getBbHeight() * 0.5, from.getZ(), tgt.getX(), tgt.getY() + tgt.getBbHeight() * 0.5, tgt.getZ());
	}
}
