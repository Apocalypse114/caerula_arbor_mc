package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TideChimeraEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class ChimeraSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double dura = 0;
		double tap = 0;
		double perc = 0;
		boolean maySummon = false;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.deathTime : 0) == 10) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dragon_fireball.explode")), SoundSource.HOSTILE, 2, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dragon_fireball.explode")), SoundSource.HOSTILE, 2, 1, false);
				}
			}
			ChimeraBlastProcedure.execute(world, x, y, z);
		}
		if (!entity.isAlive()) {
			return;
		}
		sklp1 = entity instanceof TideChimeraEntity _datEntI ? _datEntI.getEntityData().get(TideChimeraEntity.DATA_skillP) : 0;
		dura = entity instanceof TideChimeraEntity _datEntI ? _datEntI.getEntityData().get(TideChimeraEntity.DATA_duration) : 0;
		tap = entity instanceof TideChimeraEntity _datEntI ? _datEntI.getEntityData().get(TideChimeraEntity.DATA_summonP) : 0;
		perc = GetHealthPercProcedure.execute(entity);
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof TideChimeraEntity _datEntSetI)
				_datEntSetI.getEntityData().set(TideChimeraEntity.DATA_duration, (int) (dura - 1));
		}
		if (dura <= 0) {
			if (perc <= 0.25 && tap >= 1) {
				maySummon = true;
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.IMMORTAL.get(), 600, 0, false, false));
				CaerulaArborMod.queueServerWork(10, () -> {
					SummonRandomChimeraProcedure.execute(world, x, y, z, entity);
				});
			} else if (perc <= 0.5 && tap >= 2) {
				maySummon = true;
				CaerulaArborMod.queueServerWork(10, () -> {
					SummonRandomChimeraProcedure.execute(world, x, y, z, entity);
				});
			} else if (perc <= 0.75 && tap >= 3) {
				maySummon = true;
				CaerulaArborMod.queueServerWork(10, () -> {
					SummonRandomChimeraProcedure.execute(world, x, y, z, entity);
				});
			}
			if (maySummon) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 20, 0, false, false));
				if (entity instanceof TideChimeraEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TideChimeraEntity.DATA_summonP, (int) (tap - 1));
				if (entity instanceof TideChimeraEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TideChimeraEntity.DATA_duration, 18);
				dura = 18;
				if (entity instanceof TideChimeraEntity) {
					((TideChimeraEntity) entity).setAnimation("animation.super_apocata.throw");
				}
			}
		}
		if (sklp1 > 0) {
			if (entity instanceof TideChimeraEntity _datEntSetI)
				_datEntSetI.getEntityData().set(TideChimeraEntity.DATA_skillP, (int) (sklp1 - 1));
		} else if (dura <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 8) {
					if (entity instanceof TideChimeraEntity) {
						((TideChimeraEntity) entity).setAnimation("animation.super_apocata.ranged");
					}
					if (entity instanceof TideChimeraEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TideChimeraEntity.DATA_skillP, 300);
					if (entity instanceof TideChimeraEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TideChimeraEntity.DATA_duration, 28);
					CaerulaArborMod.queueServerWork(12, () -> {
						if (entity.isAlive()) {
							if (world instanceof ServerLevel _level)
								_level.sendParticles(ParticleTypes.EXPLOSION, x, (y + 4), z, 3, 0, 0, 0, 0.1);
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:firstteller_attack")), SoundSource.HOSTILE, (float) 1.5, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:firstteller_attack")), SoundSource.HOSTILE, (float) 1.5, 1, false);
								}
							}
							ChimeraDistributeBulletProcedure.execute(world, x, y, z, entity);
						}
					});
					CaerulaArborMod.queueServerWork(13, () -> {
						if (entity.isAlive()) {
							if (world instanceof ServerLevel _level)
								_level.sendParticles(ParticleTypes.EXPLOSION, x, (y + 4), z, 3, 0, 0, 0, 0.1);
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:firstteller_attack")), SoundSource.HOSTILE, (float) 1.5, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:firstteller_attack")), SoundSource.HOSTILE, (float) 1.5, 1, false);
								}
							}
							ChimeraDistributeBulletProcedure.execute(world, x, y, z, entity);
						}
					});
					CaerulaArborMod.queueServerWork(14, () -> {
						if (entity.isAlive()) {
							if (world instanceof ServerLevel _level)
								_level.sendParticles(ParticleTypes.EXPLOSION, x, (y + 4), z, 3, 0, 0, 0, 0.1);
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:firstteller_attack")), SoundSource.HOSTILE, (float) 1.5, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:firstteller_attack")), SoundSource.HOSTILE, (float) 1.5, 1, false);
								}
							}
							ChimeraDistributeBulletProcedure.execute(world, x, y, z, entity);
						}
					});
				}
			}
		}
		GiveGuideLayProcedure.execute(entity);
		UmbrellaBigRimProcedure.execute(world, x, y, z, entity);
	}
}
