package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class MerelyDeductOneSanityProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double amplifier) {
		if (entity == null)
			return;
		DeductPlayerSanityProcedure.execute(entity, amplifier + 1);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get()), x, (y + 1), z, (int) (amplifier + 1), 0.5, 0.8, 0.5, 0.1);
	}
}
