package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class CArmenShowBulletsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double b) {
		if (entity == null)
			return;
		if (entity.tickCount % 2 == 0) {
			for (int index0 = 0; index0 < (int) b; index0++) {
				world.addParticle((SimpleParticleType) (CaerulaArborModParticleTypes.BULLETS.get()), (x + 1), (y + 1.5 + index0 * 0.25), (z + 1), 0, 0, 0);
			}
		}
	}
}
