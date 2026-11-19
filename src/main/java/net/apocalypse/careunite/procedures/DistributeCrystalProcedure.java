package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.MoistEnderCrystalEntity;
import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;

public class DistributeCrystalProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity instanceof OceanizedEnderinaEntity enderina)
{
			double r = 0;
			double d = 0;
			double tx = 0;
			double tz = 0;
			if (GetCrystalNumProcedure.execute(world, x, y, z, entity) < 8 && world instanceof ServerLevel _level) {
				r = Mth.nextDouble(world.getRandom(), 0, 6.283);
				d = Mth.nextDouble(world.getRandom(), 7, 12);
				tx = x + d * Math.cos(r);
				tz = z + d * Math.sin(r);
				Entity entityToSpawn = CaerulaArborModEntities.MOIST_ENDER_CRYSTAL.get().create(entity.level());
				entityToSpawn.setPos(tx, y, tz);
				enderina.putCrystal(entityToSpawn);
				_level.addFreshEntity(entityToSpawn);
				_level.sendParticles(ParticleTypes.EXPLOSION, tx, (y + 1), tz, 1, 0, 0, 0, 0.1);				
			}
		}
	}
}
