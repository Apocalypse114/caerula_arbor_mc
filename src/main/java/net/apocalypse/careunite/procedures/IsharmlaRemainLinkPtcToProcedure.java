package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class IsharmlaRemainLinkPtcToProcedure {
	public static void execute(LevelAccessor world, double fromX, double fromY, double fromZ, double toX, double toY, double toZ) {
		double vx = 0;
		double vy = 0;
		double vz = 0;
		double size = 0;
		vx = toX - fromX;
		vy = toY - fromY;
		vz = toZ - fromZ;
		size = Math.max(Math.min(Math.round(Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2) + Math.pow(vz, 2))), 32), 1);
		for (int index0 = 0; index0 < (int) size; index0++) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ISHARMLA_CURSE_PARTICLE.get()), (fromX + (vx / size) * index0), (fromY + (vy / size) * index0 + 0.5), (fromZ + (vz / size) * index0), 5, 0.32, 0.5, 0.32, 0.05);
		}
	}
}
