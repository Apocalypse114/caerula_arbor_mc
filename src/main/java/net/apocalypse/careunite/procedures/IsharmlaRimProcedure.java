package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class IsharmlaRimProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double p = 0;
		double r = 0;
		double t = 0;
		double ang = 0;
		t = entity.tickCount % 90;
		for (int index0 = 0; index0 < 20; index0++) {
			ang = Math.toRadians(index0 * 6 + t * 4);
			r = 12 + Math.sin(index0 * 12);
			if (Math.random() < 0.33) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.END_ROD, (x + r * Math.sin(ang)), (y + 0.125), (z + r * Math.cos(ang)), 1, 0, 0.25, 0, 0.2);
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.EDERMAN_PTC.get()), (x + r * Math.sin(ang)), (y + 0.15), (z + r * Math.cos(ang)), 1, 0, 0.25, 0, 0.2);
			r = 23 + Math.sin(index0 * 12);
			if (Math.random() < 0.33) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.END_ROD, (x + r * Math.sin(ang)), (y + 0.125), (z + r * Math.cos(ang)), 1, 0, 0.25, 0, 0.2);
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.EDERMAN_PTC.get()), (x + r * Math.sin(ang)), (y + 0.15), (z + r * Math.cos(ang)), 1, 0, 0.25, 0, 0.2);
		}
	}
}
