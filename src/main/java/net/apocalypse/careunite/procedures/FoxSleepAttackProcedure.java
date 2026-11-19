package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedFoxEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class FoxSleepAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean sneak = false;
		double time_stamp = 0;
		double skillp = 0;
		double dura = 0;
		Entity enemy = null;
		if (!entity.isAlive()) {
			return;
		}
		if (entity.tickCount % 10 == 0) {
			time_stamp = entity instanceof OceanizedFoxEntity _datEntI ? _datEntI.getEntityData().get(OceanizedFoxEntity.DATA_action_time) : 0;
			sneak = entity instanceof OceanizedFoxEntity _datEntL3 && _datEntL3.getEntityData().get(OceanizedFoxEntity.DATA_sleeping);
			if (time_stamp > 0) {
				if (entity instanceof OceanizedFoxEntity _datEntSetI)
					_datEntSetI.getEntityData().set(OceanizedFoxEntity.DATA_action_time, (int) (time_stamp - 1));
			} else if (Math.random() < 0.02) {
				if (sneak) {
					if (entity instanceof OceanizedFoxEntity _datEntSetL)
						_datEntSetL.getEntityData().set(OceanizedFoxEntity.DATA_sleeping, false);
					if (entity instanceof OceanizedFoxEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedFoxEntity.DATA_action_time, 200);
				} else if (!(entity instanceof Mob _mobEnt7 && _mobEnt7.isAggressive())) {
					if (entity instanceof OceanizedFoxEntity _datEntSetL)
						_datEntSetL.getEntityData().set(OceanizedFoxEntity.DATA_sleeping, true);
					if (entity instanceof OceanizedFoxEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedFoxEntity.DATA_action_time, 200);
				}
			}
			entity.setShiftKeyDown(sneak);
		}
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (!(enemy == null) && enemy.isAlive()) {
			entity.setShiftKeyDown(false);
			if (entity instanceof OceanizedFoxEntity _datEntSetL)
				_datEntSetL.getEntityData().set(OceanizedFoxEntity.DATA_sleeping, false);
		}
		skillp = entity instanceof OceanizedFoxEntity _datEntI ? _datEntI.getEntityData().get(OceanizedFoxEntity.DATA_skillp) : 0;
		dura = entity instanceof OceanizedFoxEntity _datEntI ? _datEntI.getEntityData().get(OceanizedFoxEntity.DATA_duration) : 0;
		if (dura > 0) {
			if (entity instanceof OceanizedFoxEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedFoxEntity.DATA_duration, (int) (dura - 1));
		}
		if (skillp > 0) {
			if (entity instanceof OceanizedFoxEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedFoxEntity.DATA_skillp, (int) (skillp - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 4) {
					if (entity instanceof OceanizedFoxEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedFoxEntity.DATA_duration, 45);
					if (entity instanceof OceanizedFoxEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedFoxEntity.DATA_skillp, 200);
					if (entity instanceof OceanizedFoxEntity) {
						((OceanizedFoxEntity) entity).setAnimation("animation.oceanized_fox.jump");
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 25, 9, false, false));
					entity.push((entity.getLookAngle().x * 0.25), 0.25, (entity.getLookAngle().z * 0.25));
					CaerulaArborMod.queueServerWork(20, () -> {
						if (entity.isAlive() && !((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.fox.teleport")), SoundSource.HOSTILE, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.fox.teleport")), SoundSource.HOSTILE, 1, 1, false);
								}
							}
							{
								Entity _ent = entity;
								_ent.teleportTo(((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getX()), ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY() + 0.25),
										((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getZ()));
								if (_ent instanceof ServerPlayer _serverPlayer)
									_serverPlayer.connection.teleport(((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getX()), ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY() + 0.25),
											((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getZ()), _ent.getYRot(), _ent.getXRot());
							}
						}
					});
					CaerulaArborMod.queueServerWork(30, () -> {
						if (entity.isAlive()) {
							FoxLandProcedure.execute(world, x, y, z, entity);
						}
					});
				}
			}
		}
	}
}
