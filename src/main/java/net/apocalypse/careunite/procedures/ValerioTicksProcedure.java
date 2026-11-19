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
import net.apocalypse.careunite.entity.SaintValerioEntity;

public class ValerioTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean mayRelease = false;
		Entity enemy = null;
		double skillPierceT = 0;
		double skillPierceCld = 0;
		double skillShootT = 0;
		double skillShootCld = 0;
		double igniteT = 0;
		double igniteCld = 0;
		double PHASE = 0;
		double atkAnim = 0;
		double reviveT = 0;
		double bshd = 0;
		double burnRange = 0;
		if (entity.isAlive()) {
			if (world.isClientSide()) {
				return;
			}
			bshd = GetBaseHeldProcedure.execute(entity, false, 0);
			if (entity.tickCount % 5 == 0) {
				if (bshd > 0) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.STRENGTH_OF_CROWD.get(), 5, (int) (bshd * 3 - 1), false, false));
				}
			}
			skillPierceT = entity instanceof SaintValerioEntity _datEntI ? _datEntI.getEntityData().get(SaintValerioEntity.DATA_SKILL_PIERCE_T) : 0;
			skillPierceCld = entity instanceof SaintValerioEntity _datEntI ? _datEntI.getEntityData().get(SaintValerioEntity.DATA_SKILL_PIERCE_COOLDOWN) : 0;
			skillShootT = entity instanceof SaintValerioEntity _datEntI ? _datEntI.getEntityData().get(SaintValerioEntity.DATA_SKILL_SHOOT_T) : 0;
			skillShootCld = entity instanceof SaintValerioEntity _datEntI ? _datEntI.getEntityData().get(SaintValerioEntity.DATA_SKILL_SHOOT_COOLDOWN) : 0;
			igniteT = entity instanceof SaintValerioEntity _datEntI ? _datEntI.getEntityData().get(SaintValerioEntity.DATA_IGNITE_T) : 0;
			igniteCld = entity instanceof SaintValerioEntity _datEntI ? _datEntI.getEntityData().get(SaintValerioEntity.DATA_IGNITE_P) : 0;
			PHASE = entity instanceof SaintValerioEntity _datEntI ? _datEntI.getEntityData().get(SaintValerioEntity.DATA_PHASE) : 0;
			atkAnim = entity instanceof SaintValerioEntity _datEntI ? _datEntI.getEntityData().get(SaintValerioEntity.DATA_ATTACK_ANIM) : 0;
			reviveT = entity instanceof SaintValerioEntity _datEntI ? _datEntI.getEntityData().get(SaintValerioEntity.DATA_REVIVE_T) : 0;
			if (reviveT > 0) {
				if (entity instanceof SaintValerioEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_REVIVE_T, (int) (reviveT - 1));
				entity.setDeltaMovement(new Vec3(0, 0, 0));
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) Math.max((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * (200 - reviveT) * 0.005, 1));
				if (reviveT <= 40) {
					if (entity instanceof SaintValerioEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_PHASE, 2);
				}
				if (reviveT <= 1) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 200, 1, false, false));
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
				}
				if (reviveT <= 100) {
					burnRange = 3 * (100 - reviveT) * 0.01;
				}
			} else if (PHASE > 0) {
				burnRange = 3;
			}
			if (burnRange > 0) {
				ValerioElementalBurnProcedure.execute(world, x, y, z, entity, burnRange);
			}
			if (atkAnim > 0) {
				if (entity instanceof SaintValerioEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_ATTACK_ANIM, (int) (atkAnim - 1));
			}
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			mayRelease = !(enemy == null) && enemy.isAlive();
			if (skillPierceT > 0) {
				if (entity instanceof SaintValerioEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_SKILL_PIERCE_T, (int) (skillPierceT - 1));
				entity.setDeltaMovement(new Vec3(0, 0, 0));
				if (mayRelease) {
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), y, (enemy.getZ())));
				}
				if (skillPierceT == 24 || skillPierceT == 18 || skillPierceT == 11 || bshd >= 2 && skillPierceT == 20 || bshd >= 4 && skillPierceT == 22 || bshd >= 6 && skillPierceT == 15) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:valerio_pierce_hit")), SoundSource.NEUTRAL, (float) 0.87,
									Mth.nextInt(RandomSource.create(), (int) 0.7, (int) 1.25));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:valerio_pierce_hit")), SoundSource.NEUTRAL, (float) 0.87, Mth.nextInt(RandomSource.create(), (int) 0.7, (int) 1.25),
									false);
						}
					}
					if (PHASE < 0.5) {
						ValerioPierceAttackProcedure.execute(world, x, y, z, entity, 5);
					} else {
						ValerioPierceAttackProcedure.execute(world, x, y, z, entity, 14);
					}
				}
				mayRelease = false;
			} else if (atkAnim <= 0) {
				if (skillPierceCld <= 0 && mayRelease) {
					if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 3.5) {
						if (entity instanceof SaintValerioEntity _datEntSetI)
							_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_SKILL_PIERCE_COOLDOWN, 5);
						if (entity instanceof SaintValerioEntity _datEntSetI)
							_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_SKILL_PIERCE_T, 75);
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:valerio_pierce_pre")), SoundSource.NEUTRAL, (float) 1.5,
										Mth.nextInt(RandomSource.create(), (int) 0.9, (int) 1.2));
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:valerio_pierce_pre")), SoundSource.NEUTRAL, (float) 1.5,
										Mth.nextInt(RandomSource.create(), (int) 0.9, (int) 1.2), false);
							}
						}
					}
				}
			}
			if (skillShootT > 0) {
				if (entity instanceof SaintValerioEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_SKILL_SHOOT_T, (int) (skillShootT - 1));
				if (skillShootT == 21) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:valerio_shoot_burst")), SoundSource.NEUTRAL, (float) 1.5,
									Mth.nextInt(RandomSource.create(), (int) 0.9, (int) 1.2));
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:valerio_shoot_burst")), SoundSource.NEUTRAL, (float) 1.5, Mth.nextInt(RandomSource.create(), (int) 0.9, (int) 1.2),
									false);
						}
					}
				}
				if (skillShootT == 72) {
					ValerioLockTargetsProcedure.execute(world, x, y, z, entity, 3 + 3 * PHASE);
				}
				mayRelease = false;
			} else if (atkAnim <= 0) {
				if (skillShootCld > 0) {
					if (entity instanceof SaintValerioEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_SKILL_SHOOT_COOLDOWN, (int) (skillShootCld - 1));
				} else if (mayRelease) {
					if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 16) {
						if (entity instanceof SaintValerioEntity _datEntSetI)
							_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_SKILL_SHOOT_COOLDOWN, 480);
						if (entity instanceof SaintValerioEntity _datEntSetI)
							_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_SKILL_SHOOT_T, 82);
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:valerio_shoot_pre")), SoundSource.NEUTRAL, 2,
										Mth.nextInt(RandomSource.create(), (int) 0.9, (int) 1.2));
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:valerio_shoot_pre")), SoundSource.NEUTRAL, 2, Mth.nextInt(RandomSource.create(), (int) 0.9, (int) 1.2), false);
							}
						}
					}
				}
			}
			if (igniteT > 0) {
				if (entity instanceof SaintValerioEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_IGNITE_T, (int) (igniteT - 1));
				mayRelease = false;
			} else if (!(enemy == null) && enemy.isAlive()) {
				if (igniteCld > 0) {
					if (entity instanceof SaintValerioEntity _datEntSetI)
						_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_IGNITE_P, (int) (igniteCld - 1));
				} else if (reviveT <= 0 && entity.tickCount % 5 == 0) {
					if (ValerioPunishProcedure.execute(world, x, y, z, entity, 12 + 4 * PHASE)) {
						if (entity instanceof SaintValerioEntity _datEntSetI)
							_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_IGNITE_T, 15);
						if (entity instanceof SaintValerioEntity _datEntSetI)
							_datEntSetI.getEntityData().set(SaintValerioEntity.DATA_IGNITE_P, 100);
					}
				}
			}
		}
	}
}
