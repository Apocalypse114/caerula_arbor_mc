package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.PregnantFishEntity;

public class RandomAnimaProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!(entity instanceof Mob _mobEnt0 && _mobEnt0.isAggressive()) && Math.random() < 0.001) {
			if (entity instanceof PregnantFishEntity) {
				((PregnantFishEntity) entity).setAnimation("animation.pregnant.random");
			}
		}
		SeabornRidePolarProcedure.execute(world, x, y, z, entity);
	}
}
