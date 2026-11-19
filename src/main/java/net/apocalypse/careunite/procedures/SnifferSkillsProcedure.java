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

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedSnifferEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class SnifferSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double sklp2 = 0;
		double dura = 0;
		double dTime = 0;
		double P = 0;
		double perc = 0;
		if (!entity.isAlive()) {
			return;
		}
		sklp1 = entity instanceof OceanizedSnifferEntity _datEntI ? _datEntI.getEntityData().get(OceanizedSnifferEntity.DATA_skillp1) : 0;
		sklp2 = entity instanceof OceanizedSnifferEntity _datEntI ? _datEntI.getEntityData().get(OceanizedSnifferEntity.DATA_skillp2) : 0;
		dura = entity instanceof OceanizedSnifferEntity _datEntI ? _datEntI.getEntityData().get(OceanizedSnifferEntity.DATA_duration) : 0;
		dTime = entity instanceof OceanizedSnifferEntity _datEntI ? _datEntI.getEntityData().get(OceanizedSnifferEntity.DATA_dig_time) : 0;
		P = entity instanceof OceanizedSnifferEntity _datEntI ? _datEntI.getEntityData().get(OceanizedSnifferEntity.DATA_phase) : 0;
		perc = GetHealthPercProcedure.execute(entity);
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (perc <= 0.671 && P == 0) {
			if (entity instanceof OceanizedSnifferEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_phase, 1);
			if (entity instanceof OceanizedSnifferEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_skillp1, 9);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 36, 0, false, false));
			P = 1;
			SnifferCallAnimalsProcedure.execute(world, x, y, z, entity);
		} else if (perc <= 0.331 && P == 1) {
			if (entity instanceof OceanizedSnifferEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_phase, 2);
			if (entity instanceof OceanizedSnifferEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_skillp2, 0);
			if (entity instanceof OceanizedSnifferEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_skillp1, 9);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 60, 0, false, false));
			P = 2;
			SnifferCallAnimalsProcedure.execute(world, x, y, z, entity);
		}
		if (dura > 0) {
			if (entity instanceof OceanizedSnifferEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_duration, (int) (dura - 1));
		}
		if (dTime > 0) {
			if (dTime == 45 || dTime == 42 || dTime == 35 || dTime == 32) {
				SnifferDigParticleProcedure.execute(world, x, y, z);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.digging")), SoundSource.HOSTILE, (float) 1.5,
								(float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.digging")), SoundSource.HOSTILE, (float) 1.5, (float) Mth.nextDouble(RandomSource.create(), 0.95, 1.05), false);
					}
				}
			} else if (dTime == 25 || dTime == 22) {
				SnifferDigParticleProcedure.execute(world, x, y, z);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.digging_stop")), SoundSource.HOSTILE, (float) 1.75, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.digging_stop")), SoundSource.HOSTILE, (float) 1.75, 1, false);
					}
				}
			} else if (dTime == 20) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.drop_seed")), SoundSource.HOSTILE, (float) 1.25, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.drop_seed")), SoundSource.HOSTILE, (float) 1.25, 1, false);
					}
				}
				SnifferDigResultProcedure.execute(world, x, y, z, entity);
			} else if (dTime == 55 || dTime == 48) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.sniffing")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.sniffing")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			}
			entity.setDeltaMovement(new Vec3(0, 0, 0));
			if (entity instanceof OceanizedSnifferEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_dig_time, (int) (dTime - 1));
		}
		if (sklp1 >= 3 && dura <= 0) {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 18) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.happy")), SoundSource.HOSTILE, (float) 1.5, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.happy")), SoundSource.HOSTILE, (float) 1.5, 1, false);
						}
					}
					if (entity instanceof OceanizedSnifferEntity) {
						((OceanizedSnifferEntity) entity).setAnimation("animation.oceanized_sniffer.hatch");
					}
					if (entity instanceof OceanizedSnifferEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_skillp1, 0);
					if (entity instanceof OceanizedSnifferEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_duration, 36);
					dura = 36;
					CaerulaArborMod.queueServerWork(25, () -> {
						if (entity.isAlive()) {
							SanifferRangedProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
						}
					});
				}
			}
		}
		if (sklp2 > 0) {
			if (sklp2 == 100) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.sniffing")), SoundSource.HOSTILE, (float) 1.5, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.sniffing")), SoundSource.HOSTILE, (float) 1.5, 1, false);
					}
				}
			}
			if (sklp2 == 40) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.scenting")), SoundSource.HOSTILE, (float) 1.5, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.scenting")), SoundSource.HOSTILE, (float) 1.5, 1, false);
					}
				}
			}
			if (entity instanceof OceanizedSnifferEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_skillp2, (int) (sklp2 - 1));
		} else if (dura <= 0) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.searching")), SoundSource.HOSTILE, (float) 1.5, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sniffer.searching")), SoundSource.HOSTILE, (float) 1.5, 1, false);
				}
			}
			if (entity instanceof OceanizedSnifferEntity) {
				((OceanizedSnifferEntity) entity).setAnimation("animation.oceanized_sniffer.dig");
			}
			if (entity instanceof OceanizedSnifferEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_dig_time, 60);
			if (entity instanceof OceanizedSnifferEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_duration, 60);
			dura = 90;
			if (entity instanceof OceanizedSnifferEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_skillp2, 400);
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 24) {
					if (entity instanceof OceanizedSnifferEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizedSnifferEntity.DATA_skillp2, 300);
				}
			}
		}
	}
}
