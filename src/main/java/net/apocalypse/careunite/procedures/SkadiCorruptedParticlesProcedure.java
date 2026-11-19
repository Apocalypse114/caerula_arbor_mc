package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.entity.SkadiCorruptedEntity;

public class SkadiCorruptedParticlesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double phase = 0;
		double ang = 0;
		double r = 0;
		ang = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		phase = entity instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_phase) : 0;
		for (int index0 = 0; index0 < (int) (phase + 1); index0++) {
			r = Mth.nextDouble(RandomSource.create(), 2, 3.5);
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.CORRUPTED_FISH.get()), (x + r * Math.sin(ang)), (y + 0.25), (z + r * Math.cos(ang)), 1, 0, 0, 0, 0.2);
		}
	}
}
