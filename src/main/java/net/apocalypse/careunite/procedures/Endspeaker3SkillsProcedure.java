package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerPlayer;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.Endspeaker3Entity;
import net.apocalypse.careunite.CaerulaArborMod;

public class Endspeaker3SkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double gap = 0;
		double sklp1 = 0;
		double dura = 0;
		if (!entity.isAlive()) {
			return;
		}
		if (entity.tickCount < 35) {
			return;
		}
		gap = 400;
		if (entity.tickCount % gap == 33 && GetSeabornNumProcedure.execute(world, x, y, z) < 32) {
			EndspeakerSpawnMobsProcedure.execute(world, x, y, z, entity, 0.15, 5);
		}
		EndspeakerTickFuncProcedure.execute(world, entity);
		sklp1 = entity instanceof Endspeaker3Entity _datEntI ? _datEntI.getEntityData().get(Endspeaker3Entity.DATA_skillp) : 0;
		dura = entity instanceof Endspeaker3Entity _datEntI ? _datEntI.getEntityData().get(Endspeaker3Entity.DATA_duration) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof Endspeaker3Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker3Entity.DATA_duration, (int) (dura - 1));
		}
		if (sklp1 > 0) {
			if (entity instanceof Endspeaker3Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker3Entity.DATA_skillp, (int) (sklp1 - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 24) {
					if (entity instanceof Endspeaker3Entity) {
						((Endspeaker3Entity) entity).setAnimation("animation.endspeaker_3.skill");
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 50, 9, false, false));
					if (entity instanceof Endspeaker3Entity _datEntSetI)
						_datEntSetI.getEntityData().set(Endspeaker3Entity.DATA_skillp, 280);
					if (entity instanceof Endspeaker3Entity _datEntSetI)
						_datEntSetI.getEntityData().set(Endspeaker3Entity.DATA_duration, 60);
					CaerulaArborMod.queueServerWork(16, () -> {
						EndspeakerTeleportProcedure.execute(entity);
					});
					CaerulaArborMod.queueServerWork(19, () -> {
						EndspeakerChopProcedure.execute(world, x, y, z, entity, false);
					});
					CaerulaArborMod.queueServerWork(21, () -> {
						EndspeakerTeleportProcedure.execute(entity);
					});
					CaerulaArborMod.queueServerWork(24, () -> {
						EndspeakerChopProcedure.execute(world, x, y, z, entity, false);
					});
					CaerulaArborMod.queueServerWork(26, () -> {
						EndspeakerTeleportProcedure.execute(entity);
					});
					CaerulaArborMod.queueServerWork(30, () -> {
						EndspeakerChopProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, true);
					});
					CaerulaArborMod.queueServerWork(34, () -> {
						EndspeakerTeleportProcedure.execute(entity);
					});
					CaerulaArborMod.queueServerWork(37, () -> {
						EndspeakerChopProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, true);
					});
					CaerulaArborMod.queueServerWork(41, () -> {
						{
							Entity _ent = entity;
							_ent.teleportTo(x, y, z);
							if (_ent instanceof ServerPlayer _serverPlayer)
								_serverPlayer.connection.teleport(x, y, z, _ent.getYRot(), _ent.getXRot());
						}
					});
					CaerulaArborMod.queueServerWork(44, () -> {
						EndspeakerChopProcedure.execute(world, x, y, z, entity, true);
					});
				}
			}
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.4) {
			if (entity instanceof Endspeaker3Entity animatable)
				animatable.setTexture("endspeaker_3_broken");
		} else {
			if (entity instanceof Endspeaker3Entity animatable)
				animatable.setTexture("endspeaker_3");
		}
	}
}
