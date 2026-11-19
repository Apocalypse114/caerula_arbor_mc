package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.CaerulaArborMod;

public class IsharmlaDroppedAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity obj, double R, double rate) {
		if (obj == null)
			return;
		for (int index0 = 0; index0 < 20; index0++) {
			IsharmlaDroppingPtcProcedure.execute(world, x, y, z, R, index0);
		}
		CaerulaArborMod.queueServerWork(20, () -> {
			RangedIsharmlaAttackProcedure.execute(world, x, y, z, obj, R, rate);
		});
	}
}
