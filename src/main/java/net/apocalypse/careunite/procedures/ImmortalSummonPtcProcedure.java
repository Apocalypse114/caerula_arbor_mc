package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class ImmortalSummonPtcProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		double ang = 0;
		double phase = 0;
		ang = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.IMMORTAL_PTC.get()), (x + 1.5 * Math.sin(ang)), (y + 1.25), (z + 1.5 * Math.cos(ang)), 1, 0.1, 2, 0.1, 0.2);
	}
}
