package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.AegirMortarEntity;

public class AegirMortarTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean working = false;
		boolean auto_mode = false;
		double opt = 0;
		double clst = 0;
		double cooldown = 0;
		double gunpowder = 0;
		double core = 0;
		double firemode = 0;
		double gunpowder_exhaustion = 0;
		double core_exhaustion = 0;
		double stdt = 0;
		Entity target = null;
		Entity target1 = null;
		{
			Entity _ent = entity;
			_ent.setYRot(0);
			_ent.setXRot(0);
			_ent.setYBodyRot(_ent.getYRot());
			_ent.setYHeadRot(_ent.getYRot());
			_ent.yRotO = _ent.getYRot();
			_ent.xRotO = _ent.getXRot();
			if (_ent instanceof LivingEntity _entity) {
				_entity.yBodyRotO = _entity.getYRot();
				_entity.yHeadRotO = _entity.getYRot();
			}
		}
		entity.setDeltaMovement(new Vec3(0, 0, 0));
		opt = entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_OPEN_TICK) : 0;
		clst = entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_CLOSE_TICK) : 0;
		stdt = entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_START_TICK) : 0;
		cooldown = entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_FIRE_COOLDOWN) : 0;
		auto_mode = entity instanceof AegirMortarEntity _datEntL6 && _datEntL6.getEntityData().get(AegirMortarEntity.DATA_AUTO);
		working = entity instanceof AegirMortarEntity _datEntL7 && _datEntL7.getEntityData().get(AegirMortarEntity.DATA_WORKING);
		if (opt > 0) {
			if (entity instanceof AegirMortarEntity _datEntSetI)
				_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_OPEN_TICK, (int) (opt - 1));
		}
		if (clst > 0) {
			if (entity instanceof AegirMortarEntity _datEntSetI)
				_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_CLOSE_TICK, (int) (clst - 1));
		}
		if (stdt > 0) {
			if (entity instanceof AegirMortarEntity _datEntSetI)
				_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_START_TICK, (int) (stdt - 1));
		}
		if (cooldown > 0) {
			if (entity instanceof AegirMortarEntity _datEntSetI)
				_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_FIRE_COOLDOWN, (int) (cooldown - 1));
		}
		if (working && auto_mode) {
			if (cooldown <= 0) {
				firemode = entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_FIRE_MODE) : 0;
				gunpowder = entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_COMMON_AMMO) : 0;
				core = entity instanceof AegirMortarEntity _datEntI ? _datEntI.getEntityData().get(AegirMortarEntity.DATA_SUPER_AMMO) : 0;
				if (firemode == 0) {
					gunpowder_exhaustion = 3;
				} else if (firemode == 1) {
					gunpowder_exhaustion = 9;
				} else if (firemode == 2) {
					gunpowder_exhaustion = 12;
					core_exhaustion = 1;
				}
				if (gunpowder >= gunpowder_exhaustion && core >= core_exhaustion) {
					target = FindNearestBombTargetProcedure.execute(world, x, y, z, entity);
					target1 = GetGoalAfterChangeProcedure.execute(world, x, y, z, target, entity);
					if (!(target1 == null)) {
						target = target1;
					}
					if (target == null) {
						if (entity instanceof AegirMortarEntity _datEntSetI)
							_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_FIRE_COOLDOWN, 20);
					} else if (target.getY() >= y - 4 || world.canSeeSkyFromBelowWater(BlockPos.containing(target.getX(), target.getY(), target.getZ()))) {
						if (entity instanceof AegirMortarEntity _datEntSetI)
							_datEntSetI.getEntityData().set(AegirMortarEntity.DATA_TARGET_ANGLE, (int) GetYRotByXZProcedure.execute(entity, target));
						MortarFireProcedure.execute(world, entity, firemode, target.getX(), target.getY(), target.getZ());
					}
				}
			}
		}
	}
}
