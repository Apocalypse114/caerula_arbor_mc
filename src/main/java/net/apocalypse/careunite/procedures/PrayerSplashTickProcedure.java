package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.particles.ParticleTypes;

public class PrayerSplashTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
		if (immediatesourceentity == null)
			return;
		double count = 0;
		double atk = 0;
		world.addParticle(ParticleTypes.DOLPHIN, x, y, z, ((-0.05) * immediatesourceentity.getDeltaMovement().x()), ((-0.05) * immediatesourceentity.getDeltaMovement().y()), ((-0.05) * immediatesourceentity.getDeltaMovement().z()));
	}
}
