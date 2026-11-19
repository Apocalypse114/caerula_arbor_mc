package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class RUnvolutionIconShowProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double phase = 0;
		double ang = 0;
		entity.invulnerableTime = 10;
		ang = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		if (IsRunvoltuionInPhaseProcedure.execute(entity)) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.SEORDS_ICON.get()), (x + 2 * Math.sin(ang)), (y + 2), (z + 2 * Math.cos(ang)), 1, 0.1, 2, 0.1, 0.2);
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.BOW_ICON.get()), (x + 2 * Math.sin(ang)), (y + 2), (z + 2 * Math.cos(ang)), 1, 0.1, 2, 0.1, 0.2);
		} else {
			if (GetHealthPercProcedure.execute(entity) <= 0.5) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.SEORDS_ICON.get()), (x + 2 * Math.sin(ang)), (y + 2), (z + 2 * Math.cos(ang)), 1, 0.1, 2, 0.1, 0.2);
			} else {
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.BOW_ICON.get()), (x + 2 * Math.sin(ang)), (y + 2), (z + 2 * Math.cos(ang)), 1, 0.1, 2, 0.1, 0.2);
			}
		}
	}
}
