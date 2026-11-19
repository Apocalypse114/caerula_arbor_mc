package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.OceannizedWitheriaEntity;

public class AdditionWithriaShootProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity obj) {
		if (obj == null)
			return;
		Entity enemy = null;
		Entity otherOne = null;
		Entity otherTwo = null;
		if ((obj instanceof OceannizedWitheriaEntity _datEntI ? _datEntI.getEntityData().get(OceannizedWitheriaEntity.DATA_duration) : 0) > 0) {
			return;
		}
		enemy = obj instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (enemy == null || !enemy.isAlive()) {
			return;
		}
		ShootWitheriaToProcedure.execute(world, obj, enemy);
		otherOne = GetNearestEnemyProcedure.execute(world, x, y, z, enemy, enemy, obj);
		if (otherOne == null || !otherOne.isAlive()) {
			otherOne = enemy;
		}
		ShootWitheriaToProcedure.execute(world, obj, otherOne);
		otherTwo = GetNearestEnemyProcedure.execute(world, x, y, z, enemy, otherOne, obj);
		if (otherTwo == null || !otherTwo.isAlive()) {
			otherTwo = enemy;
		}
		ShootWitheriaToProcedure.execute(world, obj, otherTwo);
	}
}
