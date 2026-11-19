package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

public class WardenLightBoomProcedure {
	public static void execute(LevelAccessor world, Entity obj, Entity target) {
		if (obj == null || target == null)
			return;
		double vx = 0;
		double vy = 0;
		double vz = 0;
		double len = 0;
		double tx = 0;
		double ty = 0;
		double tz = 0;
		vx = target.getX() - obj.getX();
		vy = target.getY() - obj.getY();
		vz = target.getZ() - obj.getZ();
		len = Math.sqrt(vx * vx + vy * vy + vz * vz);
		if (len > 0) {
			vx = vx / len;
			vy = vy / len;
			vz = vz / len;
		} else {
			vx = obj.getLookAngle().y;
			vy = obj.getLookAngle().x;
			vz = obj.getLookAngle().z;
		}
		for (int index0 = 0; index0 < 32; index0++) {
			tx = obj.getX() + 0 + vx * (index0 + 1);
			ty = obj.getY() + 1.5 + vy * (index0 + 1);
			tz = obj.getZ() + 0 + vz * (index0 + 1);
			WardenRangedProcedure.execute(world, obj, true, 0.15, tx, ty, tz);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SONIC_BOOM, tx, ty, tz, 1, 0.1, 0.1, 0.1, 0.1);
		}
	}
}
