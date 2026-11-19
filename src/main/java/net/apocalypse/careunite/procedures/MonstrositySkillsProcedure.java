package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.IberiaMonstrosityEntity;

public class MonstrositySkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double sklp2 = 0;
		double dura = 0;
		double meleeT = 0;
		double rangedT = 0;
		double shootP = 0;
		if (!entity.isAlive()) {
			return;
		}
		sklp1 = entity instanceof IberiaMonstrosityEntity _datEntI ? _datEntI.getEntityData().get(IberiaMonstrosityEntity.DATA_MELEE_SKILL_P) : 0;
		sklp2 = entity instanceof IberiaMonstrosityEntity _datEntI ? _datEntI.getEntityData().get(IberiaMonstrosityEntity.DATA_RANGED_SKILL_P) : 0;
		dura = entity instanceof IberiaMonstrosityEntity _datEntI ? _datEntI.getEntityData().get(IberiaMonstrosityEntity.DATA_DURATION) : 0;
		meleeT = entity instanceof IberiaMonstrosityEntity _datEntI ? _datEntI.getEntityData().get(IberiaMonstrosityEntity.DATA_MELEE_SKILL_TICK) : 0;
		rangedT = entity instanceof IberiaMonstrosityEntity _datEntI ? _datEntI.getEntityData().get(IberiaMonstrosityEntity.DATA_RANGED_SKILL_TICK) : 0;
		shootP = entity instanceof IberiaMonstrosityEntity _datEntI ? _datEntI.getEntityData().get(IberiaMonstrosityEntity.DATA_SHOOT_P) : 0;
		entity.clearFire();
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_DURATION, (int) (dura - 1));
		}
		if (shootP > 0) {
			if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_SHOOT_P, (int) (shootP - 1));
		} else if (meleeT <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 32) {
					if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_SHOOT_P, Mth.nextInt(RandomSource.create(), 60, 80));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_shoot")), SoundSource.NEUTRAL, 2,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_shoot")), SoundSource.NEUTRAL, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
					ShootRocketToProcedure.execute(entity, enemy);
				}
			}
		}
		if (sklp1 > 0) {
			if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_MELEE_SKILL_P, (int) (sklp1 - 1));
		} else if (rangedT <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 5) {
					if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_MELEE_SKILL_P, 375);
					if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_MELEE_SKILL_TICK, 75);
					if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_DURATION, 75);
					dura = 75;
					meleeT = 75;
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_PERCLY_TINY.get(), 75, 9, false, false));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_skill_pre")), SoundSource.NEUTRAL, 2,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_skill_pre")), SoundSource.NEUTRAL, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_skill_charge")), SoundSource.NEUTRAL, 2,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_skill_charge")), SoundSource.NEUTRAL, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
				}
			}
		}
		if (sklp2 > 0) {
			if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_RANGED_SKILL_P, (int) (sklp2 - 1));
		} else if (meleeT <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 32) {
					if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_RANGED_SKILL_TICK, 70);
					if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_RANGED_SKILL_P, 405);
					if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
						_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_DURATION, 70);
					dura = 70;
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_ATTACK_PERCLY.get(), 70, 1, false, false));
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_skill_pre")), SoundSource.NEUTRAL, 2,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_skill_pre")), SoundSource.NEUTRAL, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
				}
			}
		}
		if (meleeT > 0) {
			if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_MELEE_SKILL_TICK, (int) (meleeT - 1));
			if (!(enemy == null) && enemy.isAlive()) {
				entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY()), (enemy.getZ())));
			}
			if (meleeT == 25) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_skill_hit")), SoundSource.NEUTRAL, 2,
								(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_skill_hit")), SoundSource.NEUTRAL, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
				MonstrosityAimlessRangedProcedure.execute(world, x, y, z, entity, 2.25);
			}
			if (meleeT == 23) {
				MonstrosityAimlessRangedProcedure.execute(world, x, y, z, entity, 3);
			}
			if (meleeT == 21) {
				MonstrosityAimlessRangedProcedure.execute(world, x, y, z, entity, 2.25);
			}
		}
		if (rangedT > 0) {
			if (entity instanceof IberiaMonstrosityEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IberiaMonstrosityEntity.DATA_RANGED_SKILL_TICK, (int) (rangedT - 1));
			if (rangedT >= 20 && rangedT <= 50) {
				if ((70 - rangedT) % 4 == 2) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_shoot")), SoundSource.NEUTRAL, 2,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:mons_shoot")), SoundSource.NEUTRAL, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
						}
					}
					ShootRocketToProcedure.execute(entity, GetRandomEnemyForIshmaelProcedure.execute(world, x, y, z, entity));
					ShootRocketToProcedure.execute(entity, enemy);
				}
			}
		}
		if (entity.tickCount % 20 == 10) {
			if (!(enemy == null) && enemy.isAlive()) {
				MonstrosityDestoryBlockProcedure.execute(world, x, y, z);
			}
		}
	}
}
