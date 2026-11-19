package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceanizedWitherEntity;

public class PostWitherShootProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity) {
		if (entity == null || immediatesourceentity == null)
			return;
		Entity enemy = null;
		Entity otherOne = null;
		Entity otherTwo = null;
		if ((entity instanceof OceanizedWitherEntity _datEntI ? _datEntI.getEntityData().get(OceanizedWitherEntity.DATA_duration) : 0) > 0) {
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
			return;
		}
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (enemy == null || !enemy.isAlive()) {
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
			return;
		}
		ShootWitherToProcedure.execute(world, entity, enemy);
		otherOne = GetNearestEnemyProcedure.execute(world, x, y, z, enemy, enemy, entity);
		if (otherOne == null || !otherOne.isAlive()) {
			otherOne = enemy;
		}
		ShootWitherToProcedure.execute(world, entity, otherOne);
		otherTwo = GetNearestEnemyProcedure.execute(world, x, y, z, enemy, otherOne, entity);
		if (otherTwo == null || !otherTwo.isAlive()) {
			otherTwo = enemy;
		}
		ShootWitherToProcedure.execute(world, entity, otherTwo);
		if (!immediatesourceentity.level().isClientSide())
			immediatesourceentity.discard();
	}
}
