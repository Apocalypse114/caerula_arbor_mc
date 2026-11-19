package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.CaerulaArborMod;

public class IsharmlaDroppingPtcProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, double r, double t) {
		CaerulaArborMod.queueServerWork((int) t, () -> {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.MOIST_BOOM.get()), x, (y + 10 - t * 0.5), z, 1, 0, 0, 0, 0);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.END_ROD, (x + r * Math.cos(Math.toRadians(t * 9))), y, (z + r * Math.sin(Math.toRadians(t * 9))), 1, 0, 0, 0, 0);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.END_ROD, (x + r * Math.cos(Math.toRadians(t * 9 + 180))), (y + 0.125), (z + r * Math.sin(Math.toRadians(t * 9 + 180))), 1, 0, 0, 0, 0);
		});
	}
}
