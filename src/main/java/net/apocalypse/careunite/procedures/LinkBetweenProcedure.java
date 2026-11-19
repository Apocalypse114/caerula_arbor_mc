package net.apocalypse.careunite.procedures;

import org.checkerframework.checker.units.qual.A;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

public class LinkBetweenProcedure {
	public static void execute(LevelAccessor world, Entity A, Entity B) {
		if (A == null || B == null)
			return;
		double vx = 0;
		double vy = 0;
		double vz = 0;
		vx = A.getX() - B.getX();
		vy = A.getY() - B.getY();
		vz = A.getZ() - B.getZ();
		for (int index0 = 0; index0 < 40; index0++) {
			if (Math.random() < 0.1) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.DOLPHIN, (B.getX() + vx * 0.025 * index0), (B.getY() + vy * 0.025 * index0 + 1), (B.getZ() + vz * 0.025 * index0), 3, 0.1, 0.1, 0.1, 0.01);
			}
		}
	}
}
