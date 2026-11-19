package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class SpecterDollTiucksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double tickCount = 0;
		if (!entity.isAlive()) {
			return;
		}
		HealFromGladiiaProcedure.execute(world, x, y, z, entity);
		entity.setDeltaMovement(new Vec3(0, (entity.getDeltaMovement().y()), 0));
		tickCount = entity.tickCount;
		if (entity instanceof LivingEntity _entity)
			_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.0005));
		if (tickCount > 20 && tickCount < 200) {
			if (tickCount % 20 == 0) {
				SpecterDollRangedProcedure.execute(world, x, y, z, entity);
			}
			SpecterDiamondParticleProcedure.execute(world, x, y, z, tickCount % 20 + 1);
		}
		if (tickCount >= 220) {
			RecallSpecterProcedure.execute(world, x, y, z, entity);
		}
	}
}
