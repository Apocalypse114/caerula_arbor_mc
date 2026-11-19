package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.entity.IshmaelEntity;

public class IshmaelRimPtcProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double range = 0;
		double t = 0;
		range = 12;
		t = (-0.6) * (entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_SKILL_P) : 0) * 0.25;
		for (int index0 = 0; index0 < (int) t; index0++) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.SPECTER_GLITTER.get()), (x + 12 * Math.sin(Math.toRadians(index0 * 4))), (y + 0.25), (z + range * Math.cos(Math.toRadians(index0 * 4))), 1, 0, 0.1, 0, 0.1);
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.SPECTER_GLITTER.get()), (x + 12 * Math.sin(Math.toRadians(index0 * 4 + 180))), (y + 0.25), (z + 12 * Math.cos(Math.toRadians(index0 * 4 + 180))), 1, 0, 0.1, 0,
						0.1);
		}
	}
}
