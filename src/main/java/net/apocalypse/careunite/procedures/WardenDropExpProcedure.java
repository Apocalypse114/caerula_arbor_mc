package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;

public class WardenDropExpProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			for (int index0 = 0; index0 < 64; index0++) {
				if (world instanceof ServerLevel _level)
					_level.addFreshEntity(new ExperienceOrb(_level, (x + Mth.nextDouble(RandomSource.create(), -1, 1)), y, (z + Mth.nextDouble(RandomSource.create(), -1, 1)), Mth.nextInt(RandomSource.create(), 32, 64)));
			}
		}
	}
}
