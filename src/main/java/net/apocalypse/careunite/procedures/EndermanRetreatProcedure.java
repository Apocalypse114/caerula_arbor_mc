package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

public class EndermanRetreatProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double ratio = 0;
		double angl = 0;
		double dist = 0;
		double validY = 0;
		double tX = 0;
		double tZ = 0;
		if (world.isClientSide()) {
			return;
		}
		if (entity.isAlive()) {
			ratio = 0.2;
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
				ratio = 0.45;
			}
			if (Math.random() < ratio) {
				for (int index0 = 0; index0 < 64; index0++) {
					angl = Mth.nextDouble(RandomSource.create(), 0, 6.283);
					dist = Mth.nextDouble(RandomSource.create(), 3, 6);
					tX = x + dist * Math.sin(angl);
					tZ = z + dist * Math.cos(angl);
					validY = FindValidYProcedure.execute(world, tX, y, tZ);
					if (validY <= 114513) {
						TeleportToProcedure.execute(world, entity, x, y, z, tX, validY, tZ);
						if (entity instanceof LivingEntity _entity)
							_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.075));
						break;
					}
				}
			}
		}
	}
}
